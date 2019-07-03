package com.ssaw.ssawmehelper.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.util.http.HttpConnectionUtils;
import com.ssaw.ssawmehelper.config.KqConfig;
import com.ssaw.ssawmehelper.constants.KaoqinConstants;
import com.ssaw.ssawmehelper.dao.mapper.employee.CommitLeaveMapper;
import com.ssaw.ssawmehelper.dao.po.employee.CommitLeavePO;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.CommitLeaveReqVO;
import com.ssaw.ssawmehelper.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;

/**
 * @author HuSen
 * @date 2019/4/16 9:33
 */
@Slf4j
@Component
public class CommitLeaveHandler extends BaseHandler {

    private final CommitLeaveMapper commitLeaveMapper;

    private final EmployeeService employeeService;

    @Resource
    private KqConfig kqConfig;

    @Autowired
    public CommitLeaveHandler(EmployeeService employeeService, CommitLeaveMapper commitLeaveMapper) {
        this.employeeService = employeeService;
        this.commitLeaveMapper = commitLeaveMapper;
    }

    public void work(CommitLeaveReqVO reqVO) {
        try {
            EmployeePO po = employeeService.getEmployeePO(reqVO.getBn());
            if (Objects.isNull(po)) {
                return;
            }
            realWorkDo(reqVO, po);
        } catch (Exception e) {
            log.error("提交调休申请失败:", e);
            // 记录数据库
            CommitLeavePO po = CopyUtil.copyProperties(reqVO, new CommitLeavePO());
            po.setSuccess(false);
            commitLeaveMapper.insert(po);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void realWorkDo(CommitLeaveReqVO reqVO, EmployeePO po) throws IOException {
        JSONObject calLeaveTimeObj = new JSONObject();
        calLeaveTimeObj.put("A0188", po.getEhrBn());
        calLeaveTimeObj.put("A0188s", po.getEhrBn());
        calLeaveTimeObj.put("LEAVE_BEGIN", reqVO.getBeginTime());
        calLeaveTimeObj.put("LEAVE_END", reqVO.getEndTime());
        calLeaveTimeObj.put("LEAVE_TYPE", "19");
        calLeaveTimeObj.put("ID", 0);
        calLeaveTimeObj.put("flag", 0);
        String leaveTime = HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/KQService/CalLeaveTime?ap=" + po.getEhrAp(),
                calLeaveTimeObj.toJSONString(), false);
        assert leaveTime != null;
        String errorMsg = ((JSONObject) JSON.parseArray(leaveTime).get(0)).getString("ErrorMsg");
        if (!Objects.equals(errorMsg, StringUtils.EMPTY)) {
            BigDecimal last = errorMsg.contains("期初") ? new BigDecimal(errorMsg.split("剩余")[2].split("小时")[0]) : BigDecimal.ZERO;
            BigDecimal over = new BigDecimal(errorMsg.split("加班单")[1].split("小时")[0]);
            BigDecimal leave = new BigDecimal(errorMsg.split("请假")[2].split("小时")[0]);
            // 可剩余调休时间
            BigDecimal availableTime = last.add(over).subtract(leave);
            BigDecimal needTime = reqVO.getLeaveTime().setScale(2, RoundingMode.HALF_DOWN);
            if (availableTime.subtract(needTime).compareTo(BigDecimal.ZERO) < 0) {
                // 加班时间不够了 调休失败 记录数据库
                throw new IllegalArgumentException("调休时间不够用了!");
            }
        }
        JSONObject jOriginalDataXml = JSON.parseObject(KaoqinConstants.TIAO_XIU_J_ORIGINAL_DATA_XML);
        JSONObject tables = JSON.parseObject(KaoqinConstants.TIAO_XIU_TABLES);
        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("updateTable", "K_LEAVE");
        reqJsonObject.put("primaryKey", "K_ID");
        reqJsonObject.put("userUpdateState", "0|保存");
        reqJsonObject.put("Tables", tables);
        JSONObject data = new JSONObject();
        data.put("K_LEAVE_K_ID", StringUtils.EMPTY);
        data.put("K_LEAVE_LEAVE_REASON", "调休");
        data.put("K_LEAVE_A0188", po.getEhrBn());
        data.put("K_LEAVE_LEAVE_TYPE", "19");
        data.put("K_LEAVE_LEAVE_BEGIN", reqVO.getBeginTime());
        data.put("K_LEAVE_LEAVE_END", reqVO.getEndTime());
        data.put("K_LEAVE_LEAVE_TIME", reqVO.getLeaveTime());
        data.put("K_LEAVE_LEAVE_DAYS", reqVO.getLeaveDays());
        data.put("K_LEAVE_LEAVEFILE", StringUtils.EMPTY);
        data.put("K_LEAVE_SIGNED", "0");
        data.put("SequenceID", "0");
        data.put("@RowState", "Added");
        data.put("DataRightType", "0");
        data.put("ValidateState", "1");
        data.put("FormulaState", "1");
        JSONObject dataSet = new JSONObject();
        dataSet.put("DATA", data);
        JSONObject jDataXml = new JSONObject();
        jDataXml.put("DataSet", dataSet);
        reqJsonObject.put("JOriginalDataXML", jOriginalDataXml);
        reqJsonObject.put("JDataXML", jDataXml);
        String json = JSON.toJSONString(reqJsonObject);
        String result = HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/ComService/UpdateEx?ap=" + po.getEhrAp(),
                json, false);
        log.info("提交调休申请结果:{}", result);
        final String id = "AddedID";
        if (StringUtils.contains(result, id)) {
            String signIds = result.split("<AddedID>")[1].split("</AddedID>")[0];
            String startWf = startWf("050102", signIds, po);
            log.info("提交审批结果:{}", startWf);
        } else {
            log.info("调休申请可能有异常:{}", URLDecoder.decode(result.split("<Message>")[1].split("</Message>")[0], "utf-8"));
        }
    }

    /**
     * 10分钟执行一次
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 30000)
    public void task() {
        QueryWrapper<CommitLeavePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("success", false);
        List<CommitLeavePO> commitLeavePoList = commitLeaveMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commitLeavePoList)) {
            log.info("没有失败的提交调休记录:{}", commitLeavePoList);
            return;
        }
        for (CommitLeavePO po : commitLeavePoList) {
            try {
                EmployeePO employeePo = employeeService.getEmployeePO(po.getBn());
                if (Objects.isNull(employeePo)) {
                    continue;
                }
                CommitLeaveReqVO reqVO = CopyUtil.copyProperties(po, new CommitLeaveReqVO());
                realWorkDo(reqVO, employeePo);
                // 没有出现异常任务执行成功
                po.setSuccess(true);
                commitLeaveMapper.updateById(po);
            } catch (Exception e) {
                // 什么都不做
                log.error("出现异常, 加班单:{}, 补偿失败:", po, e);
            }
        }
    }
}
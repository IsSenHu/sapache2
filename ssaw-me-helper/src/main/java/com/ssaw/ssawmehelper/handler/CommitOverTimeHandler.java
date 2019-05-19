package com.ssaw.ssawmehelper.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssaw.commons.util.bean.CopyUtil;
import com.ssaw.commons.util.http.HttpConnectionUtils;
import com.ssaw.ssawmehelper.constants.KaoqinConstants;
import com.ssaw.ssawmehelper.dao.mapper.employee.CommitOverTimeMapper;
import com.ssaw.ssawmehelper.dao.po.employee.CommitOverTimePO;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.model.vo.kaoqin.CommitOverTimeInfoReqVO;
import com.ssaw.ssawmehelper.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author HuSen
 * @date 2019/4/13 10:32
 */
@Slf4j
@Component
public class CommitOverTimeHandler extends BaseHandler {

    private final CommitOverTimeMapper commitOverTimeMapper;

    private final EmployeeService employeeService;

    @Autowired
    public CommitOverTimeHandler(CommitOverTimeMapper commitOverTimeMapper, EmployeeService employeeService) {
        this.commitOverTimeMapper = commitOverTimeMapper;
        this.employeeService = employeeService;
    }

    public void work(CommitOverTimeInfoReqVO reqVO) {
        try {
            EmployeePO employee = employeeService.getEmployeePO(reqVO.getBn());
            if (Objects.isNull(employee)) {
                return;
            }
            realWork(reqVO, employee);
        } catch (Exception e) {
            log.error("提交加班申请失败，结果:", e);
            CommitOverTimePO commitOverTimePO = CopyUtil.copyProperties(reqVO, new CommitOverTimePO());
            commitOverTimePO.setSuccess(false);
            commitOverTimeMapper.insert(commitOverTimePO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void realWork(CommitOverTimeInfoReqVO reqVO, EmployeePO employee) throws IOException {
        JSONObject jOriginalDataXml = JSON.parseObject(KaoqinConstants.J_ORIGINAL_DATA_XML);
        JSONObject tables = JSON.parseObject(KaoqinConstants.TABLES);
        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("updateTable", "K_OVER");
        reqJsonObject.put("primaryKey", "K_ID");
        reqJsonObject.put("userUpdateState", StringUtils.EMPTY);
        reqJsonObject.put("Tables", tables);
        JSONObject data = new JSONObject();
        data.put("K_OVER_K_ID", StringUtils.EMPTY);
        data.put("K_OVER_BYZJB", StringUtils.EMPTY);
        data.put("K_OVER_ZRJB", StringUtils.EMPTY);
        data.put("K_OVER_BSKF", StringUtils.EMPTY);
        data.put("K_OVER_K_OVERDATE", StringUtils.EMPTY);
        data.put("K_OVER_CONFIRM", StringUtils.EMPTY);
        data.put("K_OVER_K_OVERTIME", reqVO.getOverTime());
        Calendar calendar = Calendar.getInstance();
        //noinspection MagicConstant
        calendar.set(reqVO.getYear(), getMonth(reqVO.getMonth() - 1), reqVO.getDay(), 0, 0, 0);
        data.put("K_OVER_K_OVERRQ", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime()));
        data.put("K_OVER_SHIFTID", StringUtils.EMPTY);
        data.put("K_OVER_A0188", employee.getEhrBn());
        data.put("K_OVER_A0188S", StringUtils.EMPTY);
        data.put("K_OVER_ZLOVER_CHECK", "0");
        data.put("K_OVER_OVER_BEGIN", reqVO.getOverBegin());
        data.put("K_OVER_BEGIN_CHECK", "0");
        data.put("K_OVER_OVER_VALID1", StringUtils.EMPTY);
        data.put("K_OVER_OVER_END", reqVO.getOverEnd());
        data.put("K_OVER_END_CHECK", "0");
        data.put("K_OVER_OVER_VALID2", StringUtils.EMPTY);
        data.put("K_OVER_OVER_TIME", reqVO.getOverTime());
        data.put("K_OVER_REST_TIME", reqVO.getRestTime());
        data.put("K_OVER_OVER_TYPE", reqVO.getKqOverType());
        data.put("K_OVER_ACTIONEMPLOYEE", employee.getName());
        data.put("K_OVER_ACTIONTIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        data.put("K_OVER_BC", StringUtils.EMPTY);
        data.put("K_OVER_SIGNED", "0");
        data.put("K_OVER_CARD_BEGIN", StringUtils.EMPTY);
        data.put("K_OVER_CARD_END", StringUtils.EMPTY);
        data.put("K_OVER___CHK", StringUtils.EMPTY);
        data.put("K_OVER_JBYY", "01");
        data.put("K_OVER_OVER_TIME_FACT", StringUtils.EMPTY);
        data.put("K_OVER_OVER_MEMO", "加班");
        data.put("K_OVER_OVER_SOURCE", StringUtils.EMPTY);
        data.put("K_OVER_K_OVER01", StringUtils.EMPTY);
        data.put("DataRightType", "0");
        data.put("ValidateState", "1");
        data.put("FormulaState", "1");
        data.put("SequenceID", "0");
        data.put("@RowState", "Added");
        JSONObject dataSet = new JSONObject();
        dataSet.put("DATA", data);
        JSONObject jDataXml = new JSONObject();
        jDataXml.put("DataSet", dataSet);
        reqJsonObject.put("JDataXML", jDataXml);
        reqJsonObject.put("JOriginalDataXML", jOriginalDataXml);
        String json = JSON.toJSONString(reqJsonObject);
        String resp = HttpConnectionUtils.doPost("https://ehr.1919.cn/api/ComService/UpdateEx?ap=" + employee.getEhrAp(),
                json, false);
        log.info("提交加班申请结果:{}", resp);
        final String addedID = "AddedID";
        if (StringUtils.contains(resp, addedID)) {
            String signIds = resp.split("<AddedID>")[1].split("</AddedID>")[0];
            String startWf = startWf("050104", signIds, employee);
            log.info("提交审批结果:{}", startWf);
        } else {
            log.info("加班申请可能有异常:{}", URLDecoder.decode(resp.split("<Message>")[1].split("</Message>")[0], "utf-8"));
        }
    }

    /**
     * 10分钟执行一次
     */
    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 30000)
    public void task() {
        QueryWrapper<CommitOverTimePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("success", false);
        List<CommitOverTimePO> commitOverTimePOList = commitOverTimeMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commitOverTimePOList)) {
            log.info("没有失败的提交加班记录:{}", commitOverTimePOList);
            return;
        }
        for (CommitOverTimePO commitOverTimePO : commitOverTimePOList) {
            try {
                EmployeePO employeePO = employeeService.getEmployeePO(commitOverTimePO.getBn());
                if (Objects.isNull(employeePO)) {
                    continue;
                }
                CommitOverTimeInfoReqVO reqVO = CopyUtil.copyProperties(commitOverTimePO, new CommitOverTimeInfoReqVO());
                realWork(reqVO, employeePO);
                // 没有出现异常任务执行成功
                commitOverTimePO.setSuccess(true);
                commitOverTimeMapper.updateById(commitOverTimePO);
            } catch (Exception e) {
                // 什么都不做
                log.error("出现异常, 加班单:{}, 补偿失败:", commitOverTimePO, e);
            }
        }
    }
}
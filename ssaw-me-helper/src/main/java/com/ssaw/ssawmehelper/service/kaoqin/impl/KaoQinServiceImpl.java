package com.ssaw.ssawmehelper.service.kaoqin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ssaw.commons.util.http.HttpConnectionUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.store.UserContextHolder;
import com.ssaw.ssawmehelper.config.KqConfig;
import com.ssaw.ssawmehelper.dao.mapper.employee.EmployeeMapper;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.dao.redis.KaoQinDao;
import com.ssaw.ssawmehelper.handler.CommitLeaveHandler;
import com.ssaw.ssawmehelper.handler.CommitOverTimeHandler;
import com.ssaw.ssawmehelper.model.vo.kaoqin.*;
import com.ssaw.ssawmehelper.service.consumption.BaseService;
import com.ssaw.ssawmehelper.service.kaoqin.KaoQinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ssaw.commons.constant.Constants.ResultCodes.*;

/**
 * @author HuSen
 * @date 2019/3/20 15:22
 */
@Slf4j
@Service
public class KaoQinServiceImpl extends BaseService implements KaoQinService {

    private final EmployeeMapper employeeMapper;

    private final KaoQinDao kaoQinDao;

    @Resource(name = "commitOverTimeExecutor")
    private ThreadPoolTaskExecutor commitOverTimeExecutor;

    @Resource(name = "commitLeaveExecutor")
    private ThreadPoolTaskExecutor commitLeaveExecutor;

    private final CommitOverTimeHandler commitOverTimeHandler;

    private final CommitLeaveHandler commitLeaveHandler;

    @Resource
    private KqConfig kqConfig;

    @Autowired
    public KaoQinServiceImpl(EmployeeMapper employeeMapper, KaoQinDao kaoQinDao, CommitOverTimeHandler commitOverTimeHandler, CommitLeaveHandler commitLeaveHandler) {
        this.employeeMapper = employeeMapper;
        this.kaoQinDao = kaoQinDao;
        this.commitOverTimeHandler = commitOverTimeHandler;
        this.commitLeaveHandler = commitLeaveHandler;
    }

    /**
     * 分页查询考勤信息
     *
     * @param pageReqVO 查询数据模型
     * @return 分页结果
     */
    @Override
    public TableData<KaoQinInfoVO> page(PageReqVO<KaoQinInfoQueryVO> pageReqVO) {
        TableData<KaoQinInfoVO> tableData = new TableData<>();
        PageReqVO<KaoQinInfoQueryVO> page = getPage(pageReqVO);
        tableData.setPage(page.getPage());
        tableData.setSize(page.getSize());
        KaoQinInfoQueryVO data = pageReqVO.getData();
        QueryWrapper<EmployeePO> queryWrapper = new QueryWrapper<EmployeePO>()
                .eq("username", UserContextHolder.currentUser().getUsername());
        EmployeePO employeePo = employeeMapper.selectOne(queryWrapper);
        if (Objects.isNull(employeePo)) {
            tableData.setTotals(0L);
            tableData.setTotalPages(0);
            tableData.setContent(Lists.newArrayList());
        } else {
            List<KaoQinInfoVO> voList = getKaoQinInfoVoList(data.getYear(), data.getMonth(), employeePo);
            Set<String> allOnlineTime = kaoQinDao.allOnlineTime(employeePo.getBn());
            Set<String> allForgetTime = kaoQinDao.allForgetTime(employeePo.getBn());
            Set<String> allCommitOverTime = kaoQinDao.allCommitOverTime(employeePo.getBn());
            Set<String> allCommitLeave = kaoQinDao.allCommitLeave(employeePo.getBn());

            voList.forEach(k -> {
                String dutyDate = k.getDutyDate();
                k.setOnline(allOnlineTime.contains(dutyDate));
                k.setForget(allForgetTime.contains(dutyDate));
                k.setCommitOverTime(allCommitOverTime.contains(dutyDate));
                k.setCommitLeave(allCommitLeave.contains(dutyDate));
            });

            tableData.setContent(voList);
            tableData.setTotalPages(1);
            tableData.setTotals((long) voList.size());
        }
        return tableData;
    }

    /**
     * 提交加班申请单
     *
     * @param reqVO 提交加班申请单数据模型
     * @return 申请结果
     */
    @Override
    public CommonResult<CommitOverTimeInfoReqVO> commitOverTimeInfo(CommitOverTimeInfoReqVO reqVO) {
        // 记录redis 该加班申请已提交过了
        boolean commitOverTime = kaoQinDao.insertCommitOverTime(reqVO);
        log.info("记录redis 该加班申请已提交过了:{}", commitOverTime);
        if (!commitOverTime) {
            return CommonResult.createResult(ERROR, "记录redis失败!", null);
        }
        commitOverTimeExecutor.execute(() -> commitOverTimeHandler.work(reqVO));
        return CommonResult.createResult(SUCCESS, "提交成功，请等待执行结果", reqVO);
    }

    /**
     * 提交调休申请单
     *
     * @param reqVO 提交调休申请单数据模型
     * @return 申请结果
     */
    @Override
    public CommonResult<CommitLeaveReqVO> commitLeave(CommitLeaveReqVO reqVO) {
        // 记录redis 该调休申请已经提交过了
        boolean commitLeave = kaoQinDao.insertCommitLeave(reqVO);
        log.info("记录redis 该调休申请已经提交过了:{}", commitLeave);
        if (!commitLeave) {
            return CommonResult.createResult(ERROR, "记录redis失败!", null);
        }
        commitLeaveExecutor.execute(() -> commitLeaveHandler.work(reqVO));
        return CommonResult.createResult(SUCCESS, "提交成功，请等待执行结果", reqVO);
    }

    /**
     * 我上线了
     *
     * @param reqVO 确认我上线了
     * @return 确认结果
     */
    @Override
    public CommonResult<IOnlineReqVO> iOnline(IOnlineReqVO reqVO) {
        boolean success = kaoQinDao.insertOnlineTime(reqVO);
        if (success) {
            return CommonResult.createResult(SUCCESS, "确认我上线了", reqVO);
        } else {
            return CommonResult.createResult(ERROR, "确认我上线失败", reqVO);
        }
    }

    /**
     * 我忘记打卡了
     *
     * @param reqVO 确认我忘记打卡了
     * @return 确认结果
     */
    @Override
    public CommonResult<IForgetPlayCardReqVO> iForgetPlayCard(IForgetPlayCardReqVO reqVO) {
        boolean success = kaoQinDao.insertForgetTime(reqVO);
        if (success) {
            return CommonResult.createResult(SUCCESS, "确认我忘记打卡了", reqVO);
        } else {
            return CommonResult.createResult(ERROR, "确认我忘记打卡失败", reqVO);
        }
    }

    private List<KaoQinInfoVO> getKaoQinInfoVoList(Integer year, Integer month, EmployeePO employee) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String toDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("A0188", employee.getEhrBn());
        paramMap.put("fromDate", fromDate);
        paramMap.put("toDate", toDate);
        paramMap.put("resultType", "(1=1)");
        try {
            String result = HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/KQService/QueryKQResult?ap=" + employee.getEhrAp(),
                    JSON.toJSONString(paramMap), false);
            JSONObject jsonObject = JSON.parseObject(result);
            String jDataXml = jsonObject.getString("JDataXML");
            String newDataSet = JSON.parseObject(jDataXml).getString("NewDataSet");
            String data = JSON.parseObject(newDataSet).getString("DATA");
            JSONArray jsonArray = JSON.parseArray(data);
            Map<String, String> kqOverInfo = getKqOverInfo(fromDate, toDate, employee);
            Map<String, String> kqLeaveInfo = getKqLeaveInfo(fromDate, toDate, employee);
            try {
                for (Object o : jsonArray.toArray()) {
                    String kDayDutyDate = ((JSONObject) o).getString("K_DAY_DUTY_DATE");
                    String dateString = simpleDateFormat.format(simpleDateFormat.parse(kDayDutyDate));
                    ((JSONObject) o).put("KqOverStatus", kqOverInfo.getOrDefault(dateString, "未提交"));
                    if (kqLeaveInfo.containsKey(dateString)) {
                        ((JSONObject) o).put("KqLeaveStatus", kqLeaveInfo.get(dateString));
                    } else if (StringUtils.contains(((JSONObject) o).getString("K_DAY_K_DAYSTATE"), "缺勤")
                            || StringUtils.contains(((JSONObject) o).getString("K_DAY_K_DAYSTATE"), "迟到")) {
                        ((JSONObject) o).put("KqLeaveStatus", "未提交");
                    } else {
                        ((JSONObject) o).put("KqLeaveStatus", "");
                    }
                }
            } catch (ParseException e) {
                log.error("解析日期发生错误", e);
            }
            return JSONArray.parseArray(jsonArray.toJSONString(), KaoQinInfoVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    private Map<String, String> getKqOverInfo(String formDate, String toDate, EmployeePO po) {
        Map<String, String> result = Maps.newHashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(toDate));
            c.add(Calendar.DAY_OF_MONTH, 1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("A0188", po.getEhrBn());
            jsonObject.put("toDate", simpleDateFormat.format(c.getTime()));
            jsonObject.put("fromDate", formDate);
            jsonObject.put("spStatus", "4");
            String kqOver = HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/KQService/QueryKQOver?ap=" +
                    po.getEhrAp(), jsonObject.toJSONString(), false);
            String xml = JSON.parseObject(kqOver).getString("JDataXML");
            String kqOverNewDataSet = JSON.parseObject(xml).getString("NewDataSet");
            if (kqOverNewDataSet != null) {
                String kqOverData = JSON.parseObject(kqOverNewDataSet).getString("DATA");
                final String left = "[";
                if (!StringUtils.contains(kqOverData, left)) {
                    JSONObject parseObject = JSON.parseObject(kqOverData);
                    String overrq = parseObject.getString("K_OVER_K_OVERRQ");
                    if (StringUtils.isNotBlank(overrq)) {
                        Date date = simpleDateFormat.parse(overrq);
                        result.put(simpleDateFormat.format(date), parseObject.getString("K_OVER_SIGNEDMC"));
                    }
                } else {
                    JSONArray jsonArray = JSON.parseArray(kqOverData);
                    for (Object o : jsonArray.toArray()) {
                        String overrq = ((JSONObject) o).getString("K_OVER_K_OVERRQ");
                        if (overrq != null) {
                            Date date = simpleDateFormat.parse(overrq);
                            result.put(simpleDateFormat.format(date), ((JSONObject) o).getString("K_OVER_SIGNEDMC"));
                        }
                    }
                }
            }
        } catch (ParseException e) {
            log.error("解析日期发生错误", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String, String> getKqLeaveInfo(String formDate, String toDate, EmployeePO po) {
        Map<String, String> result = Maps.newHashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(simpleDateFormat.parse(toDate));
            c.add(Calendar.DAY_OF_MONTH, 1);
            JSONObject object = new JSONObject();
            object.put("A0188", po.getEhrBn());
            object.put("fromDate", formDate);
            object.put("toDate", simpleDateFormat.format(c.getTime()));
            object.put("spStatus", "4");
            String kqLeave;
            try {
                kqLeave = HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/KQService/QueryKQLeave?ap=" +
                        po.getEhrAp(), object.toJSONString(), false);
                String xml = JSON.parseObject(kqLeave).getString("JDataXML");
                String kqLeaveNewDataSet = JSON.parseObject(xml).getString("NewDataSet");
                if (kqLeaveNewDataSet != null) {
                    String kqLeaveData = JSON.parseObject(kqLeaveNewDataSet).getString("DATA");
                    final String left = "[";
                    if (StringUtils.contains(kqLeaveData, left)) {
                        JSONArray jsonArray = JSON.parseArray(kqLeaveData);
                        for (Object o : jsonArray.toArray()) {
                            String leaveBegin = ((JSONObject) o).getString("K_LEAVE_LEAVE_BEGIN");
                            Date date = simpleDateFormat.parse(leaveBegin);
                            result.put(simpleDateFormat.format(date), ((JSONObject) o).getString("K_LEAVE_SIGNEDMC"));
                        }
                    } else {
                        JSONObject jsonObject = (JSONObject) JSON.parse(kqLeaveData);
                        String leaveBegin = jsonObject.getString("K_LEAVE_LEAVE_BEGIN");
                        Date date = simpleDateFormat.parse(leaveBegin);
                        result.put(simpleDateFormat.format(date), jsonObject.getString("K_LEAVE_SIGNEDMC"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            log.error("解析日期发生错误", e);
        }
        return result;
    }
}
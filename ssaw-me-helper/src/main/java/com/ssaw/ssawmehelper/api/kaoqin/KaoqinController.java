package com.ssaw.ssawmehelper.api.kaoqin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssaw.commons.annotations.RequestLog;
import com.ssaw.commons.util.http.HttpConnectionUtils;
import com.ssaw.commons.vo.CommonResult;
import com.ssaw.commons.vo.PageReqVO;
import com.ssaw.commons.vo.TableData;
import com.ssaw.ssawauthenticatecenterfeign.annotations.Menu;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityApi;
import com.ssaw.ssawauthenticatecenterfeign.annotations.SecurityMethod;
import com.ssaw.ssawmehelper.api.BaseController;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.dao.redis.KaoQinDao;
import com.ssaw.ssawmehelper.model.vo.kaoqin.*;
import com.ssaw.ssawmehelper.service.employee.EmployeeService;
import com.ssaw.ssawmehelper.service.kaoqin.KaoQinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static com.ssaw.commons.constant.Constants.ResultCodes.ERROR;
import static com.ssaw.commons.constant.Constants.ResultCodes.PARAM_ERROR;

/**
 * @author HuSen
 * @date 2019/3/20 14:15
 */
@Slf4j
@RestController
@RequestMapping("/api/kaoqin")
@SecurityApi(index = "2", group = "工作", menu = @Menu(index = "2-1", title = "考勤信息", scope = "我的考勤", to = "/my/helper/kaoqin"))
public class KaoqinController extends BaseController {

    private final EmployeeService employeeService;

    private final KaoQinService kaoQinService;

    private final KaoQinDao kaoQinDao;

    @Autowired
    public KaoqinController(ApplicationContext context, EmployeeService employeeService, KaoQinService kaoQinService, KaoQinDao kaoQinDao) {
        super(context);
        this.employeeService = employeeService;
        this.kaoQinService = kaoQinService;
        this.kaoQinDao = kaoQinDao;
    }

    /**
     * 分页查询考勤信息
     *
     * @param pageReqVO 查询数据模型
     * @return 分页结果
     */
    @PostMapping("/page")
    @RequestLog(desc = "分页查询考勤信息")
    @SecurityMethod(antMatcher = "/api/kaoqin/page", scope = "读取考勤信息")
    public TableData<KaoQinInfoVO> page(@RequestBody PageReqVO<KaoQinInfoQueryVO> pageReqVO) {
        return kaoQinService.page(pageReqVO);
    }

    /**
     * 提交加班申请单
     *
     * @param reqVO 提交加班申请单数据模型
     * @return 申请结果
     */
    @PostMapping("/commitOverTimeInfo")
    @RequestLog(desc = "提交加班申请单")
    @SecurityMethod(antMatcher = "/api/kaoqin/commitOverTimeInfo", scope = "提交加班申请")
    public CommonResult<CommitOverTimeInfoReqVO> commitOverTimeInfo(@RequestBody CommitOverTimeInfoReqVO reqVO) {
        return kaoQinService.commitOverTimeInfo(reqVO);
    }

    /**
     * 提交迟到调休申请单
     *
     * @param reqVO 提交调休申请单数据模型
     * @return 申请结果
     * @throws ParseException ParseException
     */
    @PostMapping("/commitLateLeaveInfo")
    @RequestLog(desc = "提交迟到调休申请单")
    @SecurityMethod(antMatcher = "/api/kaoqin/commitLateLeaveInfo", scope = "提交调休申请")
    public CommonResult<CommitLeaveReqVO> commitLeaveInfo(@RequestBody CommitLeaveReqVO reqVO) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> allOnlineTime = kaoQinDao.allOnlineTime(reqVO.getBn());
        Date parse = simpleDateFormat.parse(reqVO.getBeginTime());
        String format = DateFormatUtils.format(parse, "yyyy-MM-dd 00:00:00+08:00").replace(" ", "T");
        // 首先判断打卡时间是否大于9点，大于9点要进行调休
        Calendar calendar = Calendar.getInstance();
        final int start = 9;
        final int half = 30;
        final int dayHalf = 12;
        calendar.setTime(simpleDateFormat.parse(reqVO.getBeginTime()));
        if (allOnlineTime.contains(format) || calendar.get(Calendar.HOUR_OF_DAY) >= start) {
            calendar.set(Calendar.HOUR_OF_DAY, start);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            // 调休开始时间
            Date beginTime = calendar.getTime();

            calendar.setTime(simpleDateFormat.parse(reqVO.getBeginTime()));
            reqVO.setBeginTime(simpleDateFormat.format(beginTime));
            if (calendar.get(Calendar.MINUTE) > half) {
                calendar.roll(Calendar.HOUR_OF_DAY, 1);
                calendar.set(Calendar.MINUTE, 0);
            } else if (calendar.get(Calendar.MINUTE) != 0) {
                calendar.set(Calendar.MINUTE, half);
            }
            Date endTime = calendar.getTime();
            reqVO.setEndTime(DateFormatUtils.format(endTime, allOnlineTime.contains(format) ? "yyyy-MM-dd 18:00:00" : "yyyy-MM-dd HH:mm:ss"));
            reqVO.setLeaveTime(allOnlineTime.contains(format) ? BigDecimal.valueOf(8) : new BigDecimal(endTime.getTime() - beginTime.getTime()).divide(new BigDecimal(3600000), 8, RoundingMode.HALF_DOWN));
            // 12点整至1点整 均为3小时调休时间
            if (!allOnlineTime.contains(format) && calendar.get(Calendar.HOUR_OF_DAY) == dayHalf) {
                reqVO.setLeaveTime(BigDecimal.valueOf(3L));
            } else if (!allOnlineTime.contains(format) && calendar.get(Calendar.HOUR_OF_DAY) >= dayHalf + 1) {
                reqVO.setLeaveTime(reqVO.getLeaveTime().subtract(BigDecimal.ONE));
            }
            reqVO.setLeaveDays(reqVO.getLeaveTime().divide(BigDecimal.valueOf(8L), 8, RoundingMode.HALF_UP));
            return kaoQinService.commitLeave(reqVO);
        } else {
            return CommonResult.createResult(ERROR, "打卡开始时间小于9点，无需迟到调休", reqVO);
        }
    }

    /**
     * 提交早退调休申请单
     *
     * @param reqVO 提交早退调休申请单
     * @return 申请结果
     * @throws ParseException ParseException
     */
    @PostMapping("/commitEarlyGoLeave")
    @RequestLog(desc = "提交早退调休申请单")
    @SecurityMethod(antMatcher = "/api/kaoqin/commitEarlyGoLeave", scope = "提交调休申请")
    public CommonResult<CommitLeaveReqVO> commitEarlyGoLeave(@RequestBody CommitLeaveReqVO reqVO) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 下班打卡时间小于18点 需要早退调休
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(reqVO.getEndTime()));
        final int end = 18;
        final int half = 30;
        final int dayHalf = 12;
        if (calendar.get(Calendar.HOUR_OF_DAY) < end) {
            if (calendar.get(Calendar.MINUTE) >= half) {
                calendar.set(Calendar.MINUTE, half);
            } else {
                calendar.set(Calendar.MINUTE, 0);
            }
            Date beginTime = calendar.getTime();
            reqVO.setBeginTime(simpleDateFormat.format(beginTime));
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 0);
            Date endTime = calendar.getTime();
            reqVO.setEndTime(DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"));
            reqVO.setLeaveTime(new BigDecimal(endTime.getTime() - beginTime.getTime()).divide(new BigDecimal(3600000), 8, RoundingMode.HALF_DOWN));
            // 12点整到1点整，均为5小时调休时间
            if (calendar.get(Calendar.HOUR_OF_DAY) == dayHalf) {
                reqVO.setLeaveTime(BigDecimal.valueOf(5L));
            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= dayHalf - 1) {
                reqVO.setLeaveTime(reqVO.getLeaveTime().subtract(BigDecimal.ONE));
            }
            reqVO.setLeaveDays(reqVO.getLeaveTime().divide(new BigDecimal(8), 8, RoundingMode.HALF_UP));
            return kaoQinService.commitLeave(reqVO);
        } else {
            return CommonResult.createResult(ERROR, "打卡开始时间大于18点，无需早退调休", reqVO);
        }
    }

    /**
     * 考勤系统注册员工
     *
     * @param registerReqVO 注册数据模型
     * @return 注册结果
     */
    @PostMapping("/register")
    @RequestLog(desc = "考勤系统注册员工")
    @SecurityMethod(antMatcher = "/api/kaoqin/register", scope = "绑定工号")
    public CommonResult<EmployeeRegisterReqVO> register(@RequestBody EmployeeRegisterReqVO registerReqVO) {
        if (StringUtils.isBlank(registerReqVO.getBn()) || StringUtils.isBlank(registerReqVO.getPassword())) {
            return CommonResult.createResult(PARAM_ERROR, "工号或密码不能为空", registerReqVO);
        }
        JSONObject loginParam = new JSONObject();
        loginParam.put("uname", registerReqVO.getBn());
        loginParam.put("pwd", registerReqVO.getPassword());
        try {
            String resp = HttpConnectionUtils.doPost("https://ehr.1919.cn/api/LoginService/Login", loginParam.toJSONString(), true);
            JSONObject employeeInfo = JSON.parseObject(resp);
            assert employeeInfo != null;
            assert employeeInfo.getJSONObject("_UserInfo") != null;
            EmployeePO employeePO = new EmployeePO();
            employeePO.setBn(registerReqVO.getBn());
            employeePO.setName(employeeInfo.getJSONObject("_UserInfo").getString("UserName"));
            employeePO.setEhrBn(employeeInfo.getJSONObject("_UserInfo").getString("UserId"));
            employeePO.setEhrAp(employeeInfo.getJSONObject("_UserInfo").getString("UrlAuthParam"));
            return employeeService.insert(employeePO);
        } catch (IOException e) {
            log.error("考勤系统注册员工失败:", e);
            return CommonResult.createResult(ERROR, "注册失败", registerReqVO);
        }
    }

    /**
     * 我上线了
     *
     * @param reqVO 确认我上线了
     * @return 确认结果
     */
    @PostMapping("/iOnline")
    @RequestLog(desc = "确认我上线了")
    @SecurityMethod(antMatcher = "/api/kaoqin/iOnline", scope = "上线确认")
    public CommonResult<IOnlineReqVO> iOnline(@RequestBody IOnlineReqVO reqVO) {
        return kaoQinService.iOnline(reqVO);
    }

    /**
     * 我忘记打卡了
     *
     * @param reqVO 确认我忘记打卡了
     * @return 确认结果
     */
    @PostMapping("/iForgetPlayCard")
    @RequestLog(desc = "确认我忘记打卡了")
    @SecurityMethod(antMatcher = "/api/kaoqin/iForgetPlayCard", scope = "忘打卡确认")
    public CommonResult<IForgetPlayCardReqVO> iForgetPlayCard(@RequestBody IForgetPlayCardReqVO reqVO) {
        return kaoQinService.iForgetPlayCard(reqVO);
    }
}
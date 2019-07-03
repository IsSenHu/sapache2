package com.ssaw.ssawmehelper.handler;

import com.alibaba.fastjson.JSONObject;
import com.ssaw.commons.util.http.HttpConnectionUtils;
import com.ssaw.ssawmehelper.config.KqConfig;
import com.ssaw.ssawmehelper.dao.mapper.employee.WfMapper;
import com.ssaw.ssawmehelper.dao.po.employee.EmployeePO;
import com.ssaw.ssawmehelper.dao.po.employee.WfPO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author HuSen
 * @date 2019/4/16 9:31
 */
@Slf4j
public class BaseHandler {

    @Resource
    private KqConfig kqConfig;

    @Resource
    private WfMapper wfMapper;

    int getMonth(int i) {
        switch (i) {
            case 1: {
                return Calendar.FEBRUARY;
            }
            case 2: {
                return Calendar.MARCH;
            }
            case 3: {
                return Calendar.APRIL;
            }
            case 4: {
                return Calendar.MAY;
            }
            case 5: {
                return Calendar.JUNE;
            }
            case 6: {
                return Calendar.JULY;
            }
            case 7: {
                return Calendar.AUGUST;
            }
            case 8: {
                return Calendar.SEPTEMBER;
            }
            case 9: {
                return Calendar.OCTOBER;
            }
            case 10: {
                return Calendar.NOVEMBER;
            }
            case 11: {
                return Calendar.DECEMBER;
            }
            default: {
                return Calendar.JANUARY;
            }
        }
    }

    String startWf(String spbm, String signIds, EmployeePO employee) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("spbm", spbm);
            jsonObject.put("signIDs", signIds);
            jsonObject.put("A0188", employee.getEhrBn());
            return HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/WFService/StartWF?ap=" + employee.getEhrAp(),
                    jsonObject.toJSONString(), false);
        } catch (Exception e) {
            log.error("提交审批失败:", e);
            WfPO po = new WfPO();
            po.setSpbm(spbm);
            po.setSignIds(signIds);
            po.setEhrBn(employee.getEhrBn());
            po.setEhrAp(employee.getEhrAp());
            po.setSuccess(false);
            wfMapper.insert(po);
            return null;
        }
    }

    protected void startWf(WfPO po) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("spbm", po.getSpbm());
            jsonObject.put("signIDs", po.getSignIds());
            jsonObject.put("A0188", po.getEhrBn());
            HttpConnectionUtils.doPost(kqConfig.getUrl() + "api/WFService/StartWF?ap=" + po.getEhrAp(),
                    jsonObject.toJSONString(), false);
            po.setSuccess(true);
            wfMapper.updateById(po);
        } catch (Exception e) {
            log.error("提交审批失败:", e);
        }
    }
}
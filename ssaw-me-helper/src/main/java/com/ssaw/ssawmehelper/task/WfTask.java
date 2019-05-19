package com.ssaw.ssawmehelper.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssaw.ssawmehelper.dao.mapper.employee.WfMapper;
import com.ssaw.ssawmehelper.dao.po.employee.WfPO;
import com.ssaw.ssawmehelper.handler.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author HuSen
 * @date 2019/4/30 11:02
 */
@Component
public class WfTask extends BaseHandler {

    private final WfMapper wfMapper;

    @Autowired
    public WfTask(WfMapper wfMapper) {
        this.wfMapper = wfMapper;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10, initialDelay = 10000)
    public void run() {
        QueryWrapper<WfPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("success", false);
        List<WfPO> fails = wfMapper.selectList(queryWrapper);
        for (WfPO fail : fails) {
            startWf(fail);
        }
    }
}
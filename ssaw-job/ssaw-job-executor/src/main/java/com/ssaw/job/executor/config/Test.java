package com.ssaw.job.executor.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 * @date 2019/3/11 15:59
 */
@Configuration
public class Test {

    @Bean(name = "scheduler")
    public Scheduler scheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    public static class TestJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            String params = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("param");
            System.out.println(params);
        }
    }
}
package com.ssaw.job.executor;

import com.ssaw.commons.util.app.ApplicationContextUtil;
import com.ssaw.job.executor.config.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author HuSen
 * @date 2019/3/11 16:13
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SchedulerException {
        ApplicationContextUtil.setContext(SpringApplication.run(Application.class));
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        Trigger trigger = newTrigger().withIdentity("test", "test-group")
                .startNow()
                .withSchedule(cronSchedule("0/10 * * * * ?"))
                .build();

        JobDetail job = newJob(Test.TestJob.class)
                .withIdentity("test", "test-group")
                .usingJobData("param", "你好")
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
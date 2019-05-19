package com.ssaw.smscenter;

import com.ssaw.commons.annotations.EnableSpringStudy;
import com.ssaw.smscenter.selector.DemoImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hszyp
 */
@EnableScheduling
@SpringBootApplication
@EnableSpringStudy
@Import(DemoImportSelector.class)
public class SmsCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsCenterApplication.class, args);
    }
}


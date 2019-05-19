package com.ssaw.smscenter.selector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.commons.util.SpringFactoryImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * @author HuSen
 * @date 2019/4/8 13:40
 */
@Slf4j
public class DemoImportSelector extends SpringFactoryImportSelector<EnableAutoConfiguration> {

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        String[] selectImports = super.selectImports(metadata);
        log.info("HuSen:{}", Arrays.toString(selectImports));
        return selectImports;
    }

    @Override
    public Class<? extends Group> getImportGroup() {
        return null;
    }
}
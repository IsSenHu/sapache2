package com.ssaw.config.server.util;

import com.google.common.collect.Lists;
import com.ssaw.config.sdk.vo.PropertiesVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author HuSen
 * @date 2019/5/30 16:08
 */
@Slf4j
public class PropertiesUtil {

    public static List<PropertiesVO> build(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return Lists.newArrayList();
            }
            List<PropertiesVO> result = new ArrayList<>();
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
            Properties p = new Properties();
            p.load(inputStream);
            p.forEach((key, value) -> {
                PropertiesVO vo = new PropertiesVO(String.valueOf(key), String.valueOf(value));
                result.add(vo);
            });
            return result;
        } catch (Exception e) {
            log.error("解析PROPERTIES文件失败:", e);
            return Lists.newArrayList();
        }
    }
}
package com.ssaw.ssawconfig.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ssaw.ssawconfig.model.vo.PropertiesVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

/**
 * @author HuSen
 * @date 2019/5/28 15:49
 */
@Slf4j
public class YamlUtil {

    public static List<PropertiesVO> build(String yaml) {
        try {
            if (StringUtils.isBlank(yaml)) {
                return Lists.newArrayList();
            }
            JSONObject jsonObject = new Yaml().loadAs(yaml, JSONObject.class);
            JsonNode jsonNode = new ObjectMapper().readTree(jsonObject.toJSONString());
            List<PropertiesVO> properties = new ArrayList<>();
            buildProperties(jsonNode, "", properties);
            log.info("YAML解析结果:{}", JSON.toJSONString(properties));
            return properties;
        } catch (Exception e) {
            log.error("解析YAML文件失败:", e);
            return Lists.newArrayList();
        }
    }

    private static void buildProperties(JsonNode jsonNode, String path, List<PropertiesVO> properties) {
        if (!jsonNode.isContainerNode()) {
            if (jsonNode.isNull()) {
                return;
            }
            properties.add(new PropertiesVO(path, jsonNode.asText()));
            return;
        }
        if (jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                buildProperties(elements.next(), path, properties);
            }
        } else {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> next = fields.next();
                if (StringUtils.isNotBlank(path)) {
                    buildProperties(next.getValue(), path + "." + next.getKey(), properties);
                }
                // 根节点
                else {
                    buildProperties(next.getValue(), next.getKey(), properties);
                }
            }
        }
    }
}
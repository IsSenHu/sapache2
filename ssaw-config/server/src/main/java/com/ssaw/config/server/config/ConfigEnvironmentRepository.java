package com.ssaw.config.server.config;

import com.ssaw.config.sdk.vo.PropertiesVO;
import com.ssaw.config.server.constants.ConfigConstants;
import com.ssaw.config.server.dao.mo.ConfigPO;
import com.ssaw.config.server.dao.repository.ConfigRepository;
import com.ssaw.config.server.util.PropertiesUtil;
import com.ssaw.config.server.util.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * @date 2019/5/28 14:01
 */
@Configuration
@Profile("mongodb")
public class ConfigEnvironmentRepository implements EnvironmentRepository {

    private final ConfigRepository configRepository;

    private static final String DEFAULT_PROFILE = "default";

    private static final String APPLICATION = "application";

    @Autowired
    public ConfigEnvironmentRepository(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        String config = application;
        if (StringUtils.isEmpty(label)) {
            label = "master";
        }
        if (StringUtils.isEmpty(profile)) {
            profile = "default";
        }
        if (!profile.startsWith(DEFAULT_PROFILE)) {
            profile = "default," + profile;
        }
        String[] profiles = StringUtils.commaDelimitedListToStringArray(profile);
        Environment environment = new Environment(application, profiles, label, null,
                null);
        if (!config.startsWith(APPLICATION)) {
            config = "application," + config;
        }
        List<String> applications = new ArrayList<>(new LinkedHashSet<>(
                Arrays.asList(StringUtils.commaDelimitedListToStringArray(config))));
        List<String> envs = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(profiles)));
        Collections.reverse(applications);
        Collections.reverse(envs);
        for (String app : applications) {
            for (String env : envs) {
                ConfigPO configPO = configRepository.findAllByApplicationAndProfileAndLabel(app, env, label);
                if (Objects.isNull(configPO)) {
                    continue;
                }
                List<PropertiesVO> properties;
                if (ConfigConstants.YAML.equals(configPO.getType())) {
                    properties = YamlUtil.build(configPO.getConfig());
                } else if (ConfigConstants.PROPERTIES.equals(configPO.getType())){
                    properties = PropertiesUtil.build(configPO.getConfig());
                } else {
                    continue;
                }
                Map<String, String> next;
                if (CollectionUtils.isEmpty(properties)) {
                    next = new HashMap<>(0);
                } else {
                    next = properties.stream().collect(Collectors.toMap(PropertiesVO::getKey, PropertiesVO::getValue));
                }
                if (!next.isEmpty()) {
                    environment.add(new PropertySource(app + "-" + env, next));
                }
            }
        }
        return environment;
    }
}
package com.ssaw.ssawconfig.dao.repository;

import com.ssaw.ssawconfig.dao.mo.ConfigPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/5/28 18:26
 */
@Repository
public interface ConfigRepository extends MongoRepository<ConfigPO, String> {

    /**
     * 根据应用名称 环境 对应用计数
     *
     * @param application 应用名称
     * @param profile     环境
     * @param label       分支标签
     * @return 总数
     */
    long countAllByApplicationAndProfileAndLabel(String application, String profile, String label);

    /**
     * 根据应用名称 环境 查询应用
     *
     * @param application 应用名称
     * @param profile     环境
     * @param label       分支标签
     * @return 应用
     */
    ConfigPO findAllByApplicationAndProfileAndLabel(String application, String profile, String label);
}

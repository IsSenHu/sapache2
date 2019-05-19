package com.ssaw.job.admin.dao.mapper.test;

import com.ssaw.job.admin.dao.po.TestPO;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/3/15 15:22
 */
@Repository
public interface TestMapper {

    /**
     * 根据第一个名称查询测试用例
     * @param firstName 第一个名称
     * @return 测试用例
     */
    TestPO findByFirstName(@Param("firstName") String firstName);
}

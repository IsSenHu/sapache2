package com.ssaw.swaggerdemo.dao.repository;

import com.ssaw.swaggerdemo.dao.entity.ClassPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/2/28 13:00
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassPO, Long> {
}

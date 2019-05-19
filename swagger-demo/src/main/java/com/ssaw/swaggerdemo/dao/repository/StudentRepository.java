package com.ssaw.swaggerdemo.dao.repository;

import com.ssaw.swaggerdemo.dao.entity.StudentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/2/28 10:42
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentPO, Long> {

    /**
     * 根据学号删除
     * @param studentNo 学号
     */
    void deleteByStudentNo(Long studentNo);

}

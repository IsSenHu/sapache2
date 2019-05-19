package com.ssaw.swaggerdemo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSON;
import com.ssaw.swaggerdemo.dao.entity.ClassPO;
import com.ssaw.swaggerdemo.dao.entity.StudentPO;
import com.ssaw.swaggerdemo.dao.repository.ClassRepository;
import com.ssaw.swaggerdemo.dao.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SwaggerDemoApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void persist() {
        // 按照规范在关系维护方操作 一方不存在 可以直接级联保存
        StudentPO studentPO = new StudentPO();
        studentPO.setId(null);
        studentPO.setStudentNo(20140310100125L);
        studentPO.setName("胡森");
        studentPO.setBirthday(LocalDate.parse("1995-05-28", dateFormatter));
        studentPO.setInDay(LocalDate.parse("2014-09-01", dateFormatter));
        studentPO.setCreateTime(LocalDateTime.now());
        studentPO.setUpdateTime(LocalDateTime.now());

        ClassPO classPO = new ClassPO();
        classPO.setId(null);
        classPO.setName("工业工程2014-1");
        classPO.setCreateTime(LocalDateTime.now());
        classPO.setUpdateTime(LocalDateTime.now());

        studentPO.setClassPO(classPO);

        log.info("保存前 ---> {}", JSON.toJSONString(studentPO));
        studentRepository.save(studentPO);
        log.info("保存后 ---> {}", JSON.toJSONString(studentPO));
    }

    @Test
    public void persist2() {
        // 一方已存在，必须先持久化多的一方，再保存级联关系
        Optional<ClassPO> classPO = classRepository.findById(3L);
        if (classPO.isPresent()) {
            StudentPO studentPO = new StudentPO();
            studentPO.setId(null);
            studentPO.setStudentNo(20140310100126L);
            studentPO.setName("熊启");
            studentPO.setBirthday(LocalDate.parse("1995-05-28", dateFormatter));
            studentPO.setInDay(LocalDate.parse("2014-09-01", dateFormatter));
            studentPO.setCreateTime(LocalDateTime.now());
            studentPO.setUpdateTime(LocalDateTime.now());
            studentRepository.save(studentPO);
            studentPO.setClassPO(classPO.get());
            studentRepository.save(studentPO);
        }
    }

    @Test
    public void deleteAll() {
        studentRepository.deleteAll();
    }

    @Test
    public void deleteById() {
        studentRepository.deleteById(2L);
    }

    @Test
    public void find() {
        List<StudentPO> all = studentRepository.findAll();
        log.info("结果:{}", JSON.toJSONString(all));
    }
}

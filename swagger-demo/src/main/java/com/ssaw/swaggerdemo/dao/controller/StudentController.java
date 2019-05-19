package com.ssaw.swaggerdemo.dao.controller;

import com.ssaw.swaggerdemo.dao.entity.StudentPO;
import com.ssaw.swaggerdemo.dao.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author HuSen
 * @date 2019/2/28 14:40
 */
@ApiIgnore
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<StudentPO> test() {
        return studentRepository.findAll();
    }
}
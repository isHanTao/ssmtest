package com.hant.controller;


import com.hant.bean.Student;
import com.hant.filter.CorsFilter;
import com.hant.service.StudentService;
import com.hant.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　＞　　　＜　┃
 * ┃　　　　　　　┃
 * ┃...　⌒　...　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃  神兽保佑
 * ┃　　　┃  代码无bug
 * ┃　　　┃
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 *
 * @author ：Hant
 * @date ：Created in 2019/5/27 17:26
 * @description：
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    private Logger log = LoggerFactory.getLogger(CorsFilter.class);
    @Autowired
    StudentService studentService;
    @Cacheable(value = "student",key = "#id")
    @ResponseBody
    @RequestMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Integer id) throws Exception {
        log.info("执行查询操作");
        return studentService.getStudentById(id);
    }

    @ResponseBody
    @GetMapping("/json")
    public JsonResult testNotFindException(){
        throw new HttpMessageNotReadableException("json error");
    }
    @ResponseBody
    @PostMapping
    public JsonResult testValid(@Valid @RequestBody Student student) throws Exception{
        return new JsonResult(200,student);
    }
}

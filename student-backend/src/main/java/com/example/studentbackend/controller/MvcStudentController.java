package com.example.studentbackend.controller;

import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class MvcStudentController {
    private final StudentService studentService;
    @GetMapping
    public String getAllStudents(Model model){
        List<StudentDTO> studentDTOS = studentService.getAllStudents();
        model.addAttribute("students",studentDTOS);
        return "student/list";
    }
    @GetMapping("/hello")
    public String getHello(){
        return "student/hello";
    }
}

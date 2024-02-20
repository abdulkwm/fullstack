package com.example.studentbackend.controller;


import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.model.Student;
import com.example.studentbackend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {

    private final StudentService studentService;
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String welcome(){
        return "Welcome";
    }
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student stu = studentService.saveStudent(student);
        return new ResponseEntity<>(stu, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable int id){
        StudentDTO student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
   @GetMapping
   @PreAuthorize(value = "ROLE_USER")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
   }
   @PutMapping("/{id}")
   public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student){
        Student stu = studentService.updateStudent(id,student);
        return new ResponseEntity<>(stu,HttpStatus.OK);
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
   }

   @GetMapping(params = "fname")
    public ResponseEntity<List<StudentDTO>> getAllStudentByName(@RequestParam(value = "fname") String firstName){
        List<StudentDTO> studentDTOList = studentService.getByFirstName(firstName);
        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
   }
}

package com.example.studentbackend.service;

import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.model.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    StudentDTO getStudentById(int id);
    List<StudentDTO> getAllStudents();
    Student updateStudent(int id, Student studentDetail);
    void deleteStudent(int id);
    List<StudentDTO> getByFirstName(String firstName);
}

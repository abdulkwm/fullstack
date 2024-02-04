package com.example.studentbackend.service;

import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.exception.ResourceNotFoundException;
import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    @Override
    public Student saveStudent(Student student) {
        Student stu = studentRepository.save(student);
        return stu;
    }

    @Override
    public StudentDTO getStudentById(int id) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("the student is not found"));
        return modelMapper.map(student,StudentDTO.class);
//        return convertToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOStream = students.stream()
                .map(element -> modelMapper.map(element, StudentDTO.class))
                .collect(Collectors.toList());
        return studentDTOStream;
//        return convertToDTOList(students);
    }

    @Override
    public Student updateStudent(int id, Student studentDetail) {
        Student student = studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("the student is not found"));
        student.setFirstName(studentDetail.getFirstName());
        student.setLastName(studentDetail.getLastName());
        student.setEmail(studentDetail.getEmail());
        studentRepository.save(student);
        return student;
    }

    @Override
    public void deleteStudent(int id) {
        Student student = studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("the student is not found"));
        studentRepository.delete(student);
    }

    @Override
    public List<StudentDTO> getByFirstName(String firstName) {
        List<Student> students = studentRepository.findAllByFirstName(firstName);
        if (students.isEmpty()){
            throw new RuntimeException("the list is empty");
        }else {
            return convertToDTOList(students);
        }
    }

    //mapper
    public StudentDTO convertToDTO(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        return studentDTO;
    }
    public Student convertToEntity(StudentDTO studentDTO){
        Student student = new Student();
        student.setId(UUID.randomUUID().version());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        return student;
    }
    public List<StudentDTO> convertToDTOList(List<Student> students){
        List<StudentDTO> studentDTOList = students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return studentDTOList;
    }
}

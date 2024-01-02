package com.example.studentbackend.service;

import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;


    @Test
    @DisplayName("save")
    void saveStudent() {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        //act
        Student stu = studentService.saveStudent(student);
        //assert
        Assertions.assertThat(stu).isNotNull();
        assertEquals(1,stu.getId());
    }

    @Test
    @DisplayName("getById")
    void getStudentById() {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        //act
        StudentDTO stu = studentService.getStudentById(1);
        //assert
        Assertions.assertThat(stu).isNotNull();
        Assertions.assertThat(stu.getFirstName()).isEqualTo("abdu");
        assertNotNull(stu);
    }

    @Test
    @DisplayName("getAll")
    void getAllStudents() {
        //arrange
        Student student1 = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        Student student2 = Student.builder()
                .id(2)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        when(studentRepository.findAll()).thenReturn(studentList);
        //act
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        //assert
        Assertions.assertThat(studentDTOList.size()).isGreaterThanOrEqualTo(2);
        assertNotNull(studentDTOList);
    }

    @Test
    @DisplayName("update")
    void updateStudent() {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        student.setFirstName("zakir");
        student.setLastName("mohammed");
        student.setEmail("zak@gmail.com");
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        //act
        Student stu = studentService.updateStudent(1,student);
        //assert
        Assertions.assertThat(stu.getFirstName()).isEqualTo("zakir");
        assertEquals("zak@gmail.com",stu.getEmail());

    }

    @Test
    @DisplayName("delete")
    void deleteStudent() {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(any(Student.class));
        //act
        studentService.deleteStudent(1);
        boolean isPresent = studentRepository.existsById(1);
        //assert
        verify(studentRepository, times(1)).delete(student);
        Assertions.assertThat(isPresent).isFalse();
    }

    @Test
    @DisplayName("findAllByFirstName")
    void getByFirstName() {
        //arrange
        Student student1 = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        Student student2 = Student.builder()
                .id(2)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        Student student3 = Student.builder()
                .id(3)
                .firstName("zakir")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        Student student4 = Student.builder()
                .id(4)
                .firstName("sekina")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepository.findAllByFirstName(anyString())).thenReturn(studentList);
        //act
        List<StudentDTO> list = studentService.getByFirstName("abdu");
        //assert
        Assertions.assertThat(list.size()).isEqualTo(2);
        assertNotNull(list);
    }
}
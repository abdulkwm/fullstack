package com.example.studentbackend.repository;

import com.example.studentbackend.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }
    @Test
    void testSave(){
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("mohammed")
                .email("am@gmail.com")
                .build();
        //act
        Student stu = studentRepository.save(student);
        //assert
        Assertions.assertThat(stu).isNotNull();
        assertEquals("abdu",stu.getFirstName());
    }
    @Test
    @DisplayName("findById")
    void testFindById(){
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("mohammed")
                .email("am@gmail.com")
                .build();
        studentRepository.save(student);
        //act
        Student stu = studentRepository.findById(1).get();
        //assert
        Assertions.assertThat(stu).isNotNull();
        Assertions.assertThat(stu.getId()).isEqualTo(1);
    }
    @Test
    @DisplayName("findAll")
    void testFindAll(){
        //arrange
        Student student1 = Student.builder()
                .id(1)
                .firstName("a")
                .lastName("b")
                .email("ab@gmail.com")
                .build();
        Student student2 = Student.builder()
                .id(2)
                .firstName("b")
                .lastName("c")
                .email("bc@gmail.com")
                .build();
        studentRepository.save(student1);
        studentRepository.save(student2);
        //act
        List<Student> studentList = studentRepository.findAll();
        //assert
        Assertions.assertThat(studentList.size()).isGreaterThan(1);
    }
    @Test
    @DisplayName("delete")
    void testDelete(){
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        studentRepository.save(student);
        //act
        studentRepository.delete(student);
        boolean isPresent = studentRepository.existsById(1);
        //assert
        Assertions.assertThat(studentRepository.findById(1)).isEmpty();
        assertFalse(isPresent);
    }

    @Test
    @DisplayName("findAllByFirstName")
    void findAllByFirstNameOrLastName() {
        //arrange
        Student student1 = Student.builder()
                .id(1)
                .firstName("a")
                .lastName("b")
                .email("ab@gmail.com")
                .build();
        Student student2 = Student.builder()
                .id(2)
                .firstName("b")
                .lastName("c")
                .email("bc@gmail.com")
                .build();
        studentRepository.save(student1);
        studentRepository.save(student2);
        //act
        List<Student> studentList = studentRepository.findAllByFirstName("a");
        //assert
        Assertions.assertThat(studentList).isNotNull();
        assertEquals(studentList.size(),1);
    }
}
package com.example.studentbackend.controller;

import com.example.studentbackend.dto.StudentDTO;
import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;
import com.example.studentbackend.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void init(){
        studentRepository.deleteAll();
    }

    @Test
    void createStudent() throws Exception {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);
        //act and assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.firstName", Matchers.is("abdu")))
                .andExpect(jsonPath("$.lastName", Matchers.is("ahmed")))
                .andExpect(jsonPath("$.email", Matchers.is("ab@gmail.com")))
                .andExpect(status().isCreated());
        //verify
        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    void getStudent() throws Exception {
        //arrange
        StudentDTO student = StudentDTO.builder()
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        when(studentService.getStudentById(anyInt())).thenReturn(student);
        //act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("abdu")))
                .andExpect(jsonPath("$.email",Matchers.is("ab@gmail.com")));
        //verify
        verify(studentService, times(1)).getStudentById(1);
    }

    @Test
    void getAllStudents() throws Exception {
        //arrange
        StudentDTO student1 = StudentDTO.builder()
                .firstName("ahmed")
                .lastName("mohammed")
                .email("ah@gmail.com")
                .build();
        StudentDTO student2 = StudentDTO.builder()
                .firstName("zakir")
                .lastName("endris")
                .email("za@gmail.com")
                .build();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(student1);
        studentDTOList.add(student2);
        when(studentService.getAllStudents()).thenReturn(studentDTOList);
        //act and arrange
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",Matchers.is(2)));
        //verify
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void updateStudent() throws Exception {
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
        when(studentService.updateStudent(anyInt(),any(Student.class))).thenReturn(student);
        //act and assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/{id}",1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName",Matchers.is("zakir")))
                .andExpect(jsonPath("$.lastName", Matchers.is("mohammed")))
                .andExpect(jsonPath("$.email",Matchers.is("zak@gmail.com")));
        //verify
        verify(studentService, times(1)).updateStudent(1,student);
    }

    @Test
    void deleteStudent() throws Exception{
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        doNothing().when(studentService).deleteStudent(1);
        //act
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/{id}",1));
        //assert
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        //verify
        verify(studentService, times(1)).deleteStudent(1);
    }

    @Test
    void getAllStudentByName() throws Exception{
        //arrange
        StudentDTO student1 = StudentDTO.builder()
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        StudentDTO student2 = StudentDTO.builder()
                .firstName("maida")
                .lastName("mohammed")
                .email("ma@gmail.com")
                .build();
        List<StudentDTO> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        when(studentService.getByFirstName(anyString())).thenReturn(studentList);
        //act
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students").param("fName","abdu"));
        //assert
        response.andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(jsonPath("$.size()",Matchers.is(1)));
        //verify
        verify(studentService, times(1)).getByFirstName("abdu");
    }
}
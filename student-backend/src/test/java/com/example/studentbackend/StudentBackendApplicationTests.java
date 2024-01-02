package com.example.studentbackend;

import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class StudentBackendApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";
    private static RestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetUp() {
        baseUrl = baseUrl + ":" + port + "/api/v1/students";
    }

    @AfterEach
    public void afterSetUp() {
        studentRepository.deleteAll();
    }

    @Test
    void createStudent() {
        //arrange
        Student student = Student.builder()
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
        //act
        ResponseEntity<Student> response = restTemplate.postForEntity(baseUrl, student, Student.class);
        //assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        org.assertj.core.api.Assertions.assertThat(response.getBody()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getBody().getFirstName()).isEqualTo("abdu");

    }

    @Test
    void getStudentById() {
        //arrange
        Student student = Student.builder()
                .id(1)
                .firstName("abdu")
                .lastName("ahmed")
                .email("ab@gmail.com")
                .build();
		studentRepository.save(student);
		//act
		ResponseEntity<Student> response = restTemplate.getForEntity(baseUrl + "/" + student.getId(), Student.class);
		//assert
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("ab@gmail.com", response.getBody().getEmail());
		org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }

	@Test
	void getAllStudents(){
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
		ResponseEntity<Student[]> response = restTemplate.getForEntity(baseUrl, Student[].class);
		//assert
		Student[] retrivedStudent = response.getBody();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		org.assertj.core.api.Assertions.assertThat("a").isEqualTo(retrivedStudent[0].getFirstName());
	}
	@Test
	void updateStudents(){
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
		studentRepository.save(student);
		//act
		restTemplate.put(baseUrl + "/" + student.getId(), student);
		//assert
		Student stu = studentRepository.findById(student.getId()).orElse(null);
		Assertions.assertNotNull(stu);
		Assertions.assertEquals("zakir",stu.getFirstName());
	}
	@Test
	void deleteStudent(){
		//arrange
		Student student = Student.builder()
				.id(1)
				.firstName("abdu")
				.lastName("ahmed")
				.email("ab@gmail.com")
				.build();
		studentRepository.save(student);
		//act
		restTemplate.delete(baseUrl + "/" + student.getId());
		//assert
		boolean isPresent = studentRepository.existsById(1);
		Assertions.assertFalse(isPresent);
	}
	@Test
	void getAllStudentByName(){
		//arrange
		Student student1 = Student.builder()
				.id(1)
				.firstName("abdu")
				.lastName("ahmed")
				.email("ab@gmail.com")
				.build();
		Student student2 = Student.builder()
				.id(2)
				.firstName("maida")
				.lastName("mohammed")
				.email("ma@gmail.com")
				.build();
		studentRepository.save(student1);
		studentRepository.save(student2);
		//act
		ResponseEntity<Student[]> response = restTemplate.getForEntity(baseUrl + "?fName=abdu",Student[].class);
		//assert
		Student[] retriveStudent = response.getBody();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(2, retriveStudent.length);
		org.assertj.core.api.Assertions.assertThat(retriveStudent[0].getFirstName()).isEqualTo("abdu");
	}

}

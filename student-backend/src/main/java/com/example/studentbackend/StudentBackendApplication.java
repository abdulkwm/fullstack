package com.example.studentbackend;

import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.studentbackend", "com.example.studentbackend.repository"})

public class StudentBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StudentBackendApplication.class, args);
	}
	@Autowired
	private StudentRepository studentRepository;
	@Override
	public void run(String... args) throws Exception {
		Student student1 = Student.builder()
				.id(1)
				.firstName("abdu")
				.lastName("ahmed")
				.email("ab@gmail.com")
				.build();
		Student student2 = Student.builder()
				.id(2)
				.firstName("zakir")
				.lastName("Mohammed")
				.email("za@gmail.com")
				.build();
		Student student3 = Student.builder()
				.id(3)
				.firstName("sekina")
				.lastName("ahmed")
				.email("se@gmail.com")
				.build();
		studentRepository.save(student1);
		studentRepository.save(student2);
		studentRepository.save(student3);
	}
}

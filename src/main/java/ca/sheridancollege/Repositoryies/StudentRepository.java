package ca.sheridancollege.Repositoryies;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.Beans.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
	Student findById(int id);
	ArrayList<Student> findAll();
Student findByStudentId(String studentId);
Student findByName(String name);
}

package ca.sheridancollege.Repositoryies;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.Beans.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Integer> {
	Professor findById(int id);
	Professor findByUserName(String userName);
}

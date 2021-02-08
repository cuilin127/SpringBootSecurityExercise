package ca.sheridancollege.Repositoryies;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.Beans.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
Role findById(int id);
}

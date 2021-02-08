package ca.sheridancollege.Beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String rolename;
	
	public Role(String rolename) {
		this.rolename = rolename;		
	}
	@OneToMany
	List<Student> students= new ArrayList<Student>();
	
	@OneToMany
	List<Professor> professors= new ArrayList<Professor>();
}

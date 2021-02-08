package ca.sheridancollege.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.Beans.Role;
import ca.sheridancollege.Beans.Student;
import ca.sheridancollege.Repositoryies.ProfessorRepository;
import ca.sheridancollege.Repositoryies.RoleRepository;
import ca.sheridancollege.Repositoryies.StudentRepository;


@Controller
public class HomeController {

	@Autowired
	private RoleRepository rrp;

	@Autowired
	@Lazy
	private StudentRepository srp;

	@Autowired
	@Lazy
	private ProfessorRepository prp;

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied.html";
	}

	// Mapping Stuff
	@GetMapping("/")
	public String goHome() {
		return "home.html";
	}

	@GetMapping("/student/studentView")
	public String goStudentView(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			   username = ((UserDetails)principal).getUsername();
			} else {
			   username = principal.toString();
			}
		model.addAttribute("student",srp.findByName(username));
		System.out.print(username);
		return "viewAsStudent.html";
	}
	
	@GetMapping("/prof/addStudent")
	public String goAdd() {
		return "insertStudent.html";
	}
	
	@GetMapping("/prof/profView")
	public String goProfView(Model model) {

		model.addAttribute("students", srp.findAll());
		double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
		for (Student s : srp.findAll()) {
			allexercise += s.getExercises();
			allA1 += s.getAssignment1();
			allA2 += s.getAssignment2();
			allMid += s.getMitermExam();
			allFinal += s.getFinalExam();
			allProject += s.getFinalProject();
			allOverall += s.getOverallGrade();
		}

		model.addAttribute("avgA1", allA1 / srp.count());
		model.addAttribute("avgA2", allA2 / srp.count());
		model.addAttribute("avgExercise", allexercise / srp.count());
		model.addAttribute("avgMid", allMid / srp.count());
		model.addAttribute("avgFinal", allFinal / srp.count());
		model.addAttribute("avgProject", allProject / srp.count());
		model.addAttribute("avgAll", allOverall / srp.count());

		return "viewAsProf.html";
	}

	public void dropStudentFromRole(int sId, int rId) {

		Student s = srp.findById(sId);
		Role r = rrp.findById(rId);
		for (Student ss : r.getStudents()) {
			if (ss.getId() == s.getId()) {
				r.getStudents().remove(ss);
				s.setRole(null);
				break;
			}
		}
		rrp.save(r);
		srp.save(s);

	}

	@GetMapping("/prof/deleteStudent/{id}")
	public String delete(@PathVariable int id, Model model) {

		dropStudentFromRole(id, srp.findById(id).getRole().getId());

		if (srp.findById(id) != null) {
			srp.deleteById(id);
			model.addAttribute("students", srp.findAll());
			double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
			for (Student s : srp.findAll()) {
				allexercise += s.getExercises();
				allA1 += s.getAssignment1();
				allA2 += s.getAssignment2();
				allMid += s.getMitermExam();
				allFinal += s.getFinalExam();
				allProject += s.getFinalProject();
				allOverall += s.getOverallGrade();
			}

			model.addAttribute("avgA1", allA1 / srp.count());
			model.addAttribute("avgA2", allA2 / srp.count());
			model.addAttribute("avgExercise", allexercise / srp.count());
			model.addAttribute("avgMid", allMid / srp.count());
			model.addAttribute("avgFinal", allFinal / srp.count());
			model.addAttribute("avgProject", allProject / srp.count());
			model.addAttribute("avgAll", allOverall / srp.count());

			return "viewAsProf.html";
		} else {
			model.addAttribute("students", srp.findAll());
			double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
			for (Student s : srp.findAll()) {
				allexercise += s.getExercises();
				allA1 += s.getAssignment1();
				allA2 += s.getAssignment2();
				allMid += s.getMitermExam();
				allFinal += s.getFinalExam();
				allProject += s.getFinalProject();
				allOverall += s.getOverallGrade();
			}

			model.addAttribute("avgA1", allA1 / srp.count());
			model.addAttribute("avgA2", allA2 / srp.count());
			model.addAttribute("avgExercise", allexercise / srp.count());
			model.addAttribute("avgMid", allMid / srp.count());
			model.addAttribute("avgFinal", allFinal / srp.count());
			model.addAttribute("avgProject", allProject / srp.count());
			model.addAttribute("avgAll", allOverall / srp.count());

			return "viewAsProf.html";
		}
	}

	@GetMapping("/prof/editStudent/{id}")
	public String edit(@PathVariable int id, Model model) {



		if (srp.findById(id) != null) {
				model.addAttribute("student", srp.findById(id));
			return "edit.html";
		} else {
			
			model.addAttribute("students", srp.findAll());
			double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
			for (Student s : srp.findAll()) {
				allexercise += s.getExercises();
				allA1 += s.getAssignment1();
				allA2 += s.getAssignment2();
				allMid += s.getMitermExam();
				allFinal += s.getFinalExam();
				allProject += s.getFinalProject();
				allOverall += s.getOverallGrade();
			}

			model.addAttribute("avgA1", allA1 / srp.count());
			model.addAttribute("avgA2", allA2 / srp.count());
			model.addAttribute("avgExercise", allexercise / srp.count());
			model.addAttribute("avgMid", allMid / srp.count());
			model.addAttribute("avgFinal", allFinal / srp.count());
			model.addAttribute("avgProject", allProject / srp.count());
			model.addAttribute("avgAll", allOverall / srp.count());
			return "viewAsProf.html";
		}
	}
	@GetMapping("/prof/modifyStudent")
	public String modifyStudent(Model model, @ModelAttribute Student student) {
		student.setRole(rrp.findById(1));
		srp.save(student);
		model.addAttribute("students", srp.findAll());
		double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
		for (Student s : srp.findAll()) {
			allexercise += s.getExercises();
			allA1 += s.getAssignment1();
			allA2 += s.getAssignment2();
			allMid += s.getMitermExam();
			allFinal += s.getFinalExam();
			allProject += s.getFinalProject();
			allOverall += s.getOverallGrade();
		}

		model.addAttribute("avgA1", allA1 / srp.count());
		model.addAttribute("avgA2", allA2 / srp.count());
		model.addAttribute("avgExercise", allexercise / srp.count());
		model.addAttribute("avgMid", allMid / srp.count());
		model.addAttribute("avgFinal", allFinal / srp.count());
		model.addAttribute("avgProject", allProject / srp.count());
		model.addAttribute("avgAll", allOverall / srp.count());
		return "viewAsProf.html";
	}
	
	@GetMapping("/prof/addNewStudent")
	public String doAddStudent(Model model,@RequestParam String name, @RequestParam String sId) {
		
		Student student = Student.builder()
				.name(name)
				.studentId(sId)
				.role(rrp.findById(1))
				.build();
		student.setInitValue();
		srp.save(student);
		rrp.findById(1).getStudents().add(student);
		//System.out.print(rrp.findById(1).getStudents());
		
		model.addAttribute("students", srp.findAll());
		double allexercise = 0, allA1 = 0, allA2 = 0, allMid = 0, allFinal = 0, allProject = 0, allOverall = 0;
		for (Student s : srp.findAll()) {
			allexercise += s.getExercises();
			allA1 += s.getAssignment1();
			allA2 += s.getAssignment2();
			allMid += s.getMitermExam();
			allFinal += s.getFinalExam();
			allProject += s.getFinalProject();
			allOverall += s.getOverallGrade();
		}

		model.addAttribute("avgA1", allA1 / srp.count());
		model.addAttribute("avgA2", allA2 / srp.count());
		model.addAttribute("avgExercise", allexercise / srp.count());
		model.addAttribute("avgMid", allMid / srp.count());
		model.addAttribute("avgFinal", allFinal / srp.count());
		model.addAttribute("avgProject", allProject / srp.count());
		model.addAttribute("avgAll", allOverall / srp.count());
		return "viewAsProf.html";
	
	}

}

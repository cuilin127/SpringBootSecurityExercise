package ca.sheridancollege.Beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String studentId;
	
	double exercises;
	double assignment1;
	double assignment2;
	double mitermExam;
	double finalExam;
	double finalProject;
	
	@ManyToOne
	Role role =new Role();
	
	public double getOverallGrade() {
		return (0.1*this.exercises+0.05*(this.assignment1+this.assignment2)
				+0.3*this.mitermExam+0.35*this.finalExam+0.15*this.finalProject);
	}
	public String getGreadeLetter() {
		
		double overallGrade = this.getOverallGrade();
		if(overallGrade>=90 && overallGrade<=100) {
			return "A+";
		}else if(overallGrade>=80 && overallGrade<90) {
			return "A";
		}else if(overallGrade>=75 && overallGrade<80) {
			return "B+";
		}else if(overallGrade>=70 && overallGrade<75) {
			return "B";
		}else if(overallGrade>=65 && overallGrade<70) {
			return "C+";
		}else if(overallGrade>=60 && overallGrade<65) {
			return "C";
		}else if(overallGrade>=50 && overallGrade<60) {
			return "D";
		}else {
			return "F";
		}
		
	}
	public void setInitValue() {
		this.exercises = 0 ;
		this.assignment1 = 0 ;
		this.assignment2 = 0 ;
		this.mitermExam = 0 ;
		this.finalExam = 0 ;
		this.finalProject = 0 ;		
	}
	public String decodeStudentId() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(this.getStudentId());

	}

}

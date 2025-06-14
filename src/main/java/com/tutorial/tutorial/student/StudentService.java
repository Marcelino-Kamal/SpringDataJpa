package com.tutorial.tutorial.student;



import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {



    private final StudentRepo Repo;

    
    public StudentService(StudentRepo Repo) {
        this.Repo=Repo;
    }

    


    public List<Student> getStudents(){
		return Repo.findAll();
	}




    public void addNewStudent(Student student) {
        Optional<Student> studentOptional= Repo.findByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        Repo.save(student);
    }

    public void deleteStudent(Long studentId){
        
        if(!Repo.existsById(studentId)){
            throw new IllegalStateException("Student with the following Id: "+studentId+
            " doesn't exists");
        }
        Repo.deleteById(studentId);
    }
    //use Setter to directly connect to database without need to the Repository
    // this Transcational of the Jakatra package 
    @Transactional
    public void updateStudent(Long id, String name, String email) {
       Student student = Repo.findById(id).orElseThrow(
        ()-> new IllegalStateException("This Id doesn't exists")
       );
       student.setEmail(email);
       student.setName(name);
    }

}

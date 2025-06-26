package com.tutorial.tutorial.student;



import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.tutorial.tutorial.DTO.StudentDto;

import jakarta.transaction.Transactional;

@Service
public class StudentService {



    private final StudentRepo Repo;

    
    public StudentService(StudentRepo Repo) {
        this.Repo=Repo;
    }

    


    public List<StudentDto> getStudents(){
		return Repo.findAll().stream().
        map(s-> 
        new StudentDto(s.getId(),
        s.getName(),
        s.getEmail(),
        s.getBooks(),
        s.getCourseEnrollments(),
        s.getCreatedAt())).toList();
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

     /*
     * The Following function is created because of the Lazy,Eager problem
     * To overcome it with more suitable way than using the annotatin 
     *              fetch = FETCHTYPE.Eager
     *  the Hibernate offer a function to load the the books for us before the student 
     * and then add it with the student output
     * this is much safer and better way than the Eager
     */
    

    @Transactional
    public Optional<Student> StudentWithBook(Long id){
        Optional<Student> student = Repo.findById(id);
        if(student.isEmpty()){
            return Optional.empty();
        }
        Hibernate.initialize(student.get().getBooks());
        return student;
    }
    
}

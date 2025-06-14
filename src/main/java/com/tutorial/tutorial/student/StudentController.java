package com.tutorial.tutorial.student;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        service.addNewStudent(student);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        service.deleteStudent(studentId);
    }
    @PutMapping("{Id}")
    public void editStudent(@PathVariable("Id") Long Id,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email){
        service.updateStudent(Id,name,email);
    }
}

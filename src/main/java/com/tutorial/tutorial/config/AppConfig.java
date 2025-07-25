package com.tutorial.tutorial.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.github.javafaker.Faker;
import com.tutorial.tutorial.account.Account;
import com.tutorial.tutorial.account.AccountRepo;
import com.tutorial.tutorial.account.AccountServices;
import com.tutorial.tutorial.book.Book;
import com.tutorial.tutorial.book.BookRepo;
import com.tutorial.tutorial.card.StudentCard;
import com.tutorial.tutorial.card.StudentCardRepo;
import com.tutorial.tutorial.course.Course;
import com.tutorial.tutorial.course.CourseRepo;
import com.tutorial.tutorial.enrollment.CourseEnrollment;
import com.tutorial.tutorial.enrollment.CourseEnrollmentRepo;
import com.tutorial.tutorial.student.Student;
import com.tutorial.tutorial.student.StudentRepo;
import com.tutorial.tutorial.student.StudentService;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepo Repo, StudentCardRepo CardRepo,
            StudentService Studentservice, CourseRepo CR,CourseEnrollmentRepo CER,
            BookRepo BRepo,AccountServices AS,AccountRepo aRepo) {
        return args -> {
           Account a = new Account();
           a.setbalance(new BigDecimal("100"));
           aRepo.save(a);
            Account b = new Account();
           b.setbalance(new BigDecimal("150"));
           aRepo.save(b);
           AS.transfer(a,b, BigDecimal.TEN);

           aRepo.findAll().forEach(ax->{
            System.out.println(ax.getId()+ "  " + ax.getbalance());
           });
            
        };

    }

    private void BypassToString(StudentRepo Repo, BookRepo BRepo) {
        Student Mathew = new Student("Mathew",
        "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));
        Mathew = Repo.save(Mathew);
        Book b = new Book();
        b.setBookName("Hello world");
        b.setStudent(Mathew);
            BRepo.save(b);
            Repo.findAll().forEach(System.out::println);
    }

    private void DTOProjection(StudentRepo Repo, BookRepo BRepo) {
        Student Mathew = new Student("Mathew",
        "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));
        Mathew = Repo.save(Mathew);
        Book b = new Book();
        b.setBookName("Hello world");
        b.setStudent(Mathew);
            BRepo.save(b);
            
            BRepo.getAllBooksDto().forEach(System.out::println);
    }

    private void AuditingMethod(StudentRepo Repo) {
        Student Mathew = new Student("Mathew",
        "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));

        Mathew = Repo.save(Mathew);
        System.out.println("Created At: "+ Mathew.getCreatedAt());
        System.out.println("Created By: "+ Mathew.getCreatedBy());
        
        System.out.println("Updated At: "+ Mathew.getModifedAt());
        System.out.println("Updated By: "+ Mathew.getModifyBy());
        Repo.delete(Mathew);
    }

    private void SoftDelete(StudentRepo Repo) {
        Student Mathew = new Student("Mathew",
          "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));
          Student Marco = new Student(
        "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
            Repo.saveAll(List.of(Marco,Mathew));
            System.out.println(Repo.count());
            Repo.deleteById(1L);
            System.out.println(Repo.count());
    }

    private void ManyToManyAddingStudentWithCoursesWithBiDriectiona(StudentRepo Repo, CourseEnrollmentRepo CER) {
        Student Mathew = new Student("Mathew", "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));
      Student Marco = new Student(
        "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
      Course java = new Course("OOP java", "CESS");
      Course mechnics = new Course("Fluid", "Mechatronics");
      Mathew.addCourseEnrollment(mechnics);
      Mathew.addCourseEnrollment(java);
      Marco.addCourseEnrollment(java);    
      Repo.saveAll(List.of(Marco, Mathew));
         CER.findAll().forEach(s ->{
         System.out.println(s.getEnrollmentId());
         System.out.println(String.format(
        "%s %s%n",s.getStudent().getName(),
        s.getCourse().getTitle()));
         
      } );


      System.out.println(CER.count());
    }

    private void ManyToManyAddingStudentWithCourse(StudentRepo Repo, CourseRepo CR, CourseEnrollmentRepo CER) {
        Student Mathew = new Student("Mathew", "Mathew@root.com", LocalDate.of(2001, Month.OCTOBER, 13));
        Student Marco = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        Repo.saveAll(List.of(Marco, Mathew));
        Course java = new Course("OOP java", "CESS");
        Course mechnics = new Course("Fluid", "Mechatronics");

        CR.saveAll(List.of(java,mechnics));

        CER.saveAll(List.of(
            new CourseEnrollment(Marco, java),
            new CourseEnrollment(Mathew, java),
            new CourseEnrollment(Mathew, mechnics)
        ));
        CER.findAll().forEach(s ->{
            System.out.println(s.getEnrollmentId());
            System.out.println(String.format(
                "%s %s%n",s.getStudent().getName(),
                s.getCourse().getTitle()));
            
        } );


        System.out.println(CER.count());
    }

    private void ManyToManyStudentAndCourse(StudentRepo Repo) {
        Student Karo = new Student("Karo", "karo@gmail.com", LocalDate.of(2002, Month.APRIL, 1));
        Student Marco = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        Course java = new Course("OOP java", "CESS");
        Course mechnics = new Course("Fluid", "Mechatronics");

        // Karo.addCourse(java);
        // Karo.addCourse(mechnics);
        // Marco.addCourse(java);

        Repo.saveAll(List.of(Karo, Marco));

        System.out.println(Repo.findAll());
    }

    private void removeBookWhileKeepingStudent(StudentRepo Repo, StudentService Studentservice) {
        Student student = new Student("Karo", "karo@gmail.com", LocalDate.of(2002, Month.APRIL, 1));
        Book b = new Book();
        b.setBookName("Hello world");
        student.addBook(b);

        Repo.save(student);

        System.out.println(Studentservice.StudentWithBook(1L));

        student.removeBook(b);

        Repo.save(student);

        System.out.println(Studentservice.StudentWithBook(1L));
    }

    private void GetBookAndStudentWithOneToManyBidirectional(StudentRepo Repo, StudentService Studentservice) {
        Student student = new Student("Karo", "karo@gmail.com", LocalDate.of(2002, Month.APRIL, 1));
        Book b = new Book();
        b.setBookName("Hello world");
        student.addBook(b);

        Repo.save(student);

        System.out.println(Studentservice.StudentWithBook(1L));
    }

    private void getStudentWithBookWithoutEager(StudentRepo Repo, StudentService Studentservice) {
        Student student = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        Book harry = new Book();
        harry.setBookName("Harry Potter");
        harry.setStudent(student);
        Book got = new Book();
        got.setBookName("Game of Thrones");
        got.setStudent(student);
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("12345");
        student.setStudentCard(studentCard);
        studentCard.setStudent(student);

        student.setBooks(Set.of(harry, got));
        System.out.println("-------------");
        Repo.save(student);
        System.out.println(student);
        System.out.println(Studentservice.StudentWithBook(1L));
    }

    private void CreateStudentWithCardAndBook(StudentRepo Repo) {
        Student student = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        Book harry = new Book();
        harry.setBookName("Harry Potter");
        harry.setStudent(student);
        Book got = new Book();
        got.setBookName("Game of Thrones");
        got.setStudent(student);
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("12345");
        student.setStudentCard(studentCard);
        studentCard.setStudent(student);

        student.setBooks(Set.of(harry, got));
        System.out.println("-------------");
        Repo.save(student);
        System.out.println("-------------");
        System.out.println(Repo.findById(1L));
    }

    private void DeleteBothStudentAndCard(StudentRepo Repo) {
        Student student = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("12345");

        student.setStudentCard(studentCard);
        studentCard.setStudent(student);
        Repo.save(student);
        Repo.deleteById(1L);
    }

    private void UniVsBiDirectional(StudentRepo Repo) {
        Student student = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("12345");

        student.setStudentCard(studentCard);
        studentCard.setStudent(student);
        Repo.save(student);
        Optional<Student> s = Repo.findById(1L);
        System.out.println(s.get().getStudentCard());
    }

    private static void Example1(StudentRepo Repo, StudentCardRepo CardRepo) {
        Student student = new Student(
                "Marco", "Marco@gmail.com", LocalDate.of(1999, Month.JULY, 3));
        student = Repo.save(student);
        StudentCard studentCard = new StudentCard();
        studentCard.setCardNumber("12345");
        studentCard.setStudent(student);
        CardRepo.save(studentCard);
        System.out.println("---------------");
        Optional<StudentCard> card = CardRepo.findById(1L);
        System.out.println(card.get().getId());
        System.out.println(card.get().getCardNumber());
        System.out.println(card.get().getCreatedAt());
        Optional<StudentCard> studentCardWithStudent = CardRepo.findStudentCardWithStudent(1L);
        System.out.println(studentCardWithStudent.get().getStudent());
        System.out.println(CardRepo.findById(1L));
    }

    private static void getPaged(StudentRepo Repo) {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Student> page = Repo.findAll(pageable);
        System.out.println(page);
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(page.getSize());
        page.get().forEach(System.out::println);
    }

    private static void getSorted(StudentRepo Repo) {
        // Sort.by(Sort.Direction.ASC, "name");
        Sort sort = Sort.by("name").descending().and(Sort.by("dob").descending());
        Repo.findAll(sort).forEach(s -> {
            System.out.println(s.getName() + " " + s.getDob());
        });
    }

    // Seeders
    private static void StudentSeeder(StudentRepo Repo) {
        Faker faker = new Faker();
        for (int i = 0; i < 2; i++) {
            String name = faker.name().firstName();
            String email = """
                    %s_%d@root.com
                    """.formatted(name, faker.number().numberBetween(1000, 9999));
            LocalDate dob = faker.date()
                    .birthday(18, 60)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            Student x = new Student(name, email, dob);
            Repo.save(x);
        }
        System.out.println(Repo.count());
    }

}

// Student Marco = new Student("Marco","Marco@root.com",
// LocalDate.of(1999,Month.JULY, 3));
// Student Mathew = new Student("Mathew","Mathew@root.com",
// LocalDate.of(2001,Month.NOVEMBER, 14));
// Student Marco_2 = new Student("Marco Kamal","Marcox@root.com",
// LocalDate.of(1999,Month.JULY, 3));

// Repo.saveAll(List.of(Marco,Mathew,Marco_2));

// System.out.println(Repo.count());
// System.out.println(
// Repo.findByNameContains("Marco"));
// System.out.println(
// Repo.findByDobBetween(LocalDate.now().minusYears(25),LocalDate.now().minusYears(18)));

// System.out.println(Repo.findByhablola("Mathew"));
// System.out.println(Repo.ddeleteByEmail("Marcox@root.com"));
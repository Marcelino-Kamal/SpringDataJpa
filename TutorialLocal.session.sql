


SELECT * FROM students
JOIN course_enrollment ce ON students.id = ce.student_id
JOIN courses c ON  c.id = ce.course_id;
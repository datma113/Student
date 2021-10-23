package iuh.nctd.student.service;

import io.github.resilience4j.retry.annotation.Retry;
import iuh.nctd.student.VO.Faculty;
import iuh.nctd.student.VO.StudentVO;
import iuh.nctd.student.entity.Student;
import iuh.nctd.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.faculty}")
    private String facultyUrl;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public StudentVO getStudentById(int id) {
        StudentVO studentVO = null;
        Student student = studentRepository.findById(id).get();
        Faculty faculty = restTemplate.getForObject(facultyUrl + student.getFacultyId(), Faculty.class);
        studentVO = new StudentVO();
        studentVO.setFaculty(faculty);
        studentVO.setStudent(student);
        return studentVO;
    }

    @Retry(name = "test-retry")
    public Student getStudentByIdRetry(int id) {
        return studentRepository.findById(id).get();
    }

}

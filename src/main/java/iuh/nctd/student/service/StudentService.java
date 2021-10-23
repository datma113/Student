package iuh.nctd.student.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import iuh.nctd.student.VO.Faculty;
import iuh.nctd.student.VO.StudentVO;
import iuh.nctd.student.entity.Student;
import iuh.nctd.student.repository.StudentRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
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

    @RateLimiter(name = "testRateLimiter")
    public Student getStudentByIdRateLimiter(int id) {
        log.info("rate limiter: ");
        return studentRepository.findById(id).get();
    }

}

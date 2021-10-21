package iuh.nctd.student.VO;

import iuh.nctd.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentVO {

    private Student student;
    private Faculty faculty;
}

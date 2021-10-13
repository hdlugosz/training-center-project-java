import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Student class contains students name and data about the course, start date of the course etc.
 */
@Data
public class Student {
    private String name;
    private String curriculum;
    private LocalDateTime startDateTime;
    private Map<String, Integer> courseMap;

    public Student(String name, String curriculum, LocalDateTime startDateTime) {
        this.name = name;
        this.curriculum = curriculum;
        this.startDateTime = startDateTime;
        courseMap = new LinkedHashMap<>();
    }

    private Student() {}
}

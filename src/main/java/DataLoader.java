import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private static final String STUDENTS_JSON_PATH = "src/main/resources/students.json";

    /**
     * loadData method returns List of Student class that contains students data that is hardcoded.
     */
    public static List<Student> loadData() {

        List<Student> students = new ArrayList<>();

        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();

        try {
            students = mapper.readValue(
                    new File(STUDENTS_JSON_PATH), new TypeReference<List<Student>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }
}

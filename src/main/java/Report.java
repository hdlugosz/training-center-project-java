import java.util.List;
import java.util.Map;

/**
 * Report class retrieves information about students from List<Student>
 * and is able to generate reports based on this information.
 */
public class Report {
    private final List<Student> students;

    public Report(List<Student> students) {
        this.students = students;
    }

    /**
     * generateShortReport returns short, sketchy information - one line per student.
     */
    public void generateShortReport() {

        for (Student student : students) {
            System.out.printf("%s(%s) - ", student.getName(), student.getCurriculum());

            int programDuration = calculateProgramDuration(student);

            boolean isFinished = TrainingTimeManager.isTrainingFinished(student.getStartDateTime(), programDuration);

            printTimeToCompletionInfo(student, programDuration, isFinished);
        }
        System.out.println("\n");
    }

    /**
     * generateFullReport returns complete, formatted information about each student.
     */
    public void generateFullReport() {

        for (Student student : students) {
            System.out.printf("Name: %32s\n", student.getName());
            System.out.printf("Curriculum: %26s\n", student.getCurriculum());

            int programDuration = calculateProgramDuration(student);

            printCourseList(student);
            printProgramDurationInfo(programDuration);
            printStartAndEndDateInfo(student, programDuration);

            boolean isFinished = TrainingTimeManager.isTrainingFinished(student.getStartDateTime(), programDuration);

            printTimeToCompletionInfo(student, programDuration, isFinished);

            System.out.println("\n");
        }
    }

    /**
     * auxiliary methods for generating reports
     */
    private int calculateProgramDuration(Student student) {
        int programDuration = 0;
        for (Map.Entry<String, Integer> entry : student.getCourseMap().entrySet()) {
            programDuration += entry.getValue();
        }
        return programDuration;
    }

    private void printCourseList(Student student) {
        for (Map.Entry<String, Integer> entry : student.getCourseMap().entrySet()) {
            System.out.printf("Course: %12s, Duration: %3dhrs\n", entry.getKey(), entry.getValue());
        }
    }

    private void printProgramDurationInfo(int programDuration) {
        int days = 0;
        if (programDuration < 8) {
            System.out.printf("Duration of the program: %10dhrs\n", programDuration);
        } else {
            while (programDuration >= 8) {
                days++;
                programDuration -= 8;
            }
            System.out.printf("Duration of the program: %7dd %dhrs\n", days, programDuration);
        }
    }

    private void printStartAndEndDateInfo(Student student, int programDuration) {
        System.out.printf("Start date: %26s\n", student.getStartDateTime().toString());
        System.out.printf("End date: %28s\n", TrainingTimeManager.calculateEndDateTime(
                student.getStartDateTime(), programDuration).toString());
    }

    private void printTimeToCompletionInfo(Student student, int programDuration, boolean isFinished) {
        String stringHours;
        String stringDaysAndHours;

        if (isFinished) {
            stringHours = "Time passed since completion: %5dhrs\n";
            stringDaysAndHours = "Time passed since completion: %2dd %dhrs\n";
        } else {
            stringHours = "Time left to completion: %10dhrs\n";
            stringDaysAndHours = "Time left to completion: %7dd %dhrs\n";
        }

        int hoursToCompletion = (TrainingTimeManager.calculateHoursToTrainingCompletion(
                student.getStartDateTime(), programDuration));

        if (hoursToCompletion < 8) {
            System.out.printf(stringHours, hoursToCompletion);
        } else {
            int days = 0;
            while (hoursToCompletion >= 8) {
                days++;
                hoursToCompletion -= 8;
            }
            System.out.printf(stringDaysAndHours, days, hoursToCompletion);
        }
    }
}

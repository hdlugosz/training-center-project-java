import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class TrainingTimeManager {
    private final static int WORKING_HOURS_PER_DAY = 8;
    private final static int NON_WORKING_HOURS_PER_DAY = 16;
    private final static int START_OF_WORK_HOUR = 10;
    private final static int END_OF_WORK_HOUR = 18;

    /**
     * calculateHoursBetweenTwoDateTimes method calculates and returns number of hours between two given dates,
     * taking into account the weekends and the fact that working hours are between 10 and 18.
     */
    public static int calculateHoursBetweenTwoDateTimes(LocalDateTime earlierDateTime, LocalDateTime laterDateTime) {

        int hours = INTEGER_ZERO;

        if (earlierDateTime.getHour() == END_OF_WORK_HOUR) {
            earlierDateTime = earlierDateTime.plusHours(NON_WORKING_HOURS_PER_DAY);
        }

        while (earlierDateTime.isBefore(laterDateTime)) {
            if (earlierDateTime.getHour() >= START_OF_WORK_HOUR &&
                    earlierDateTime.getHour() <= END_OF_WORK_HOUR - INTEGER_ONE &&
                    earlierDateTime.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    earlierDateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
                earlierDateTime = earlierDateTime.plusHours(INTEGER_ONE);
                hours += INTEGER_ONE;
            } else {
                earlierDateTime = earlierDateTime.plusHours(INTEGER_ONE);
            }
        }

        return hours;
    }

    /**
     * calculateEndDateTime method calculates and returns the end date of the course knowing the start date and
     * duration in hours, taking into account the weekends and the fact that working hours are between 10 and 18.
     */
    public static LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, Integer programDuration) {

        while (programDuration > WORKING_HOURS_PER_DAY) {
            if (startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                startDateTime = startDateTime.plusDays(INTEGER_ONE);
            } else {
                startDateTime = startDateTime.plusDays(INTEGER_ONE);
                programDuration -= WORKING_HOURS_PER_DAY;
            }
        }

        while (startDateTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                startDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            startDateTime = startDateTime.plusDays(INTEGER_ONE);
        }

        if (programDuration > INTEGER_ZERO) {
            startDateTime = startDateTime.plusHours(programDuration);
        }

        return startDateTime;
    }

    /**
     * isTrainingFinished method returns a boolean value depending on whether the course is finished or not.
     */
    public static boolean isTrainingFinished(LocalDateTime startDateTime, Integer programDuration) {

        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, programDuration);
        LocalDateTime currentDateTime = calculateCurrentTime();

        return endDateTime.isBefore(currentDateTime) || endDateTime.isEqual(currentDateTime);
    }


    /**
     * calculateHoursToTrainingCompletion calculates and returns amount of hours from now until
     * the end date of the course (both, remaining to completion or passed from completion).
     */
    public static int calculateHoursToTrainingCompletion(LocalDateTime startDateTime, Integer programDuration) {

        LocalDateTime endDateTime = calculateEndDateTime(startDateTime, programDuration);
        LocalDateTime currentDateTime = calculateCurrentTime();

        LocalDateTime earlierDateTime;
        LocalDateTime laterDateTime;

        if (isTrainingFinished(startDateTime, programDuration)) {
            earlierDateTime = endDateTime;
            laterDateTime = currentDateTime;
        } else {
            earlierDateTime = currentDateTime;
            laterDateTime = endDateTime;
        }

        return calculateHoursBetweenTwoDateTimes(earlierDateTime, laterDateTime);
    }

    public static LocalDateTime calculateCurrentTime() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }
}

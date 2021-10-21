import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

public class TrainingTimeManagerTest {

    /* calculateHoursBetweenTwoDateTimes method: */
    @Test
    public void calculateHoursBetweenTwoDateTimes_SameDayTimeDifference() {
        assertThat(TrainingTimeManager.calculateHoursBetweenTwoDateTimes(
                LocalDateTime.of(2021, 9, 6, 10, 0),
                LocalDateTime.of(2021, 9, 6, 17, 0)))
                .isEqualTo(7);
    }

    @Test
    public void calculateHoursBetweenTwoDateTimes_OutsideWorkingHours() {
        assertThat(TrainingTimeManager.calculateHoursBetweenTwoDateTimes(
                LocalDateTime.of(2021, 9, 6, 7, 0),
                LocalDateTime.of(2021, 9, 6, 19, 0)))
                .isEqualTo(8);
    }

    @Test
    public void calculateHoursBetweenTwoDateTimes_TwoSameDates() {
        assertThat(TrainingTimeManager.calculateHoursBetweenTwoDateTimes(
                LocalDateTime.of(2021, 9, 6, 11, 0),
                LocalDateTime.of(2021, 9, 6, 11, 0)))
                .isEqualTo(0);
    }

    @Test
    public void calculateHoursBetweenTwoDateTimes_DatesSeparatedByWeekend() {
        assertThat(TrainingTimeManager.calculateHoursBetweenTwoDateTimes(
                LocalDateTime.of(2021, 9, 10, 15, 0),
                LocalDateTime.of(2021, 9, 13, 12, 0)))
                .isEqualTo(5);
    }

    @Test
    public void calculateHoursBetweenTwoDateTimes_DatesSeparatedByNight() {
        assertThat(TrainingTimeManager.calculateHoursBetweenTwoDateTimes(
                LocalDateTime.of(2021, 9, 9, 15, 0),
                LocalDateTime.of(2021, 9, 10, 15, 0))).isEqualTo(8);
    }

    /* calculateEndDateTime method: */
    @Test
    public void calculateEndDateTime_durationLessThanOneWorkingDay() {
        assertThat(TrainingTimeManager.calculateEndDateTime(
                LocalDateTime.of(2021, 9, 9, 10, 0), 7))
                .isEqualTo(LocalDateTime.of(2021, 9, 9, 17, 0));
    }

    @Test
    public void calculateEndDateTime_durationLongerThanOneWorkingDay() {
        assertThat(TrainingTimeManager.calculateEndDateTime(
                LocalDateTime.of(2021, 9, 9, 10, 0), 10))
                .isEqualTo(LocalDateTime.of(2021, 9, 10, 12, 0));
    }

    @Test
    public void calculateEndDateTime_durationLongerThanOneWorkingWeek() {
        assertThat(TrainingTimeManager.calculateEndDateTime(
                LocalDateTime.of(2021, 9, 9, 10, 0), 45))
                .isEqualTo(LocalDateTime.of(2021, 9, 16, 15, 0));
    }

    @Test
    public void calculateEndDateTime_NegativeDuration() {
        assertThat(TrainingTimeManager.calculateEndDateTime(
                LocalDateTime.of(2021, 9, 16, 10, 0), -10))
                .isEqualTo(LocalDateTime.of(2021, 9, 16, 10, 0));
    }

    /* isTrainingFinished method: */
    @Test
    public void isTrainingFinished_Finished() {
        assertThat(TrainingTimeManager.isTrainingFinished(
                LocalDateTime.of(2021, 9, 7, 10, 0), 12))
                .isTrue();
    }

    @Test
    public void isTrainingFinished_NotFinished() {
        assertThat(TrainingTimeManager.isTrainingFinished(
                LocalDateTime.now(), 12))
                .isFalse();
    }
}

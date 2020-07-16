import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeAPIDemo {
    public static void main(String[] args) {
        DateTimeAPIDemo demo = new DateTimeAPIDemo();

        demo.example1();
    }

    public void example1() {
        ZoneId zVancouver = ZoneId.of("America/Los_Angeles");
        ZoneId zBeijing = ZoneId.of("Asia/Shanghai");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime departureVancouverTime = ZonedDateTime.of(LocalDateTime.of(LocalDate.of(2020,1,9), LocalTime.of(12, 30)), zVancouver);
        ZonedDateTime departureBeijingTime = departureVancouverTime.toOffsetDateTime().atZoneSameInstant(zBeijing);
        System.out.println("Away Flight");
        System.out.println("\tdepartures from Vancouver: " + df.format(departureVancouverTime) + " (Beijing " + df.format(departureBeijingTime) + ")");

        ZonedDateTime arrivalBeijingTime =  departureVancouverTime.plusHours(10).plusMinutes(30).toOffsetDateTime().atZoneSameInstant(zBeijing);
        ZonedDateTime arrivalVancouverTime = arrivalBeijingTime.toOffsetDateTime().atZoneSameInstant(zVancouver);
        System.out.println("\tarrives at Beijing: " + df.format(arrivalBeijingTime) + " (Vancouver " + df.format(arrivalVancouverTime) + ")");

        System.out.println("Return Flight");
        departureBeijingTime = ZonedDateTime.of(LocalDate.of(2020,8,17), LocalTime.of(16,30), zBeijing);
        departureVancouverTime = departureBeijingTime.toOffsetDateTime().atZoneSameInstant(zVancouver);
        System.out.println("\tdepartures from Beijing: " + df.format(departureBeijingTime) + " (Vancouver " + df.format(departureVancouverTime) + ")");

        arrivalVancouverTime = departureVancouverTime.plusHours(11);
        arrivalBeijingTime = arrivalVancouverTime.toOffsetDateTime().atZoneSameInstant(zBeijing);
        System.out.println("\tarrives at Vancouver: " + df.format(arrivalVancouverTime) + " (Beijing " + df.format(arrivalBeijingTime) + ")");
    }
}

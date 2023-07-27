package com.example.gladoscheckin.common;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeRangeConverter {

    public static String convertTimeRange(String input) {
        String[] parts = input.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input format. Expected format: \"0800-0810\"");
        }

        String startTime = parts[0];
        String endTime = parts[1];

        String formattedStartTime = addMinutes(startTime, -10);
        String formattedEndTime = addMinutes(endTime, 10);

        return formattedStartTime + "~" + formattedEndTime;
    }

    private static String addMinutes(String time, int minutesToAdd) {
        if (time.length() != 4) {
            throw new IllegalArgumentException("Invalid time format. Expected format: \"0800\"");
        }

        String hours = time.substring(0, 2);
        String minutes = time.substring(2, 4);

        int hour = Integer.parseInt(hours);
        int minute = Integer.parseInt(minutes);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.add(Calendar.MINUTE, minutesToAdd);

        int modifiedHour = calendar.get(Calendar.HOUR_OF_DAY);
        int modifiedMinute = calendar.get(Calendar.MINUTE);

        String formattedHour = String.format("%02d", modifiedHour);
        String formattedMinute = String.format("%02d", modifiedMinute);

        return formattedHour + ":" + formattedMinute;
    }

    public static void main(String[] args) {
        String input = "0610-0620";
        String result = convertTimeRange(input);
        System.out.println(result); // Output: "07:50~08:20"
    }
}

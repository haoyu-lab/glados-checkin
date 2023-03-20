package com.example.gladoscheckin.timingtask.common;//package com.bfjz.wmannouncement.modules.timer.common;
//
//import com.cronutils.model.CronType;
//import com.cronutils.model.definition.CronDefinition;
//import com.cronutils.model.definition.CronDefinitionBuilder;
//import com.cronutils.parser.CronParser;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author houhaoyu
// * @title: com.bfjz.wmannouncement.modules.timer.common
// * @projectName wealthmanagement
// * @description: TODO
// * @date 2023/3/1714:49
// */
//@Component
//@Slf4j
//public class CronUtils {
//
//    private static final String QUESTION = "?";
//
//    private static final String ASTERISK = "*";
//
//    private static final String COMMA = ",";
//
//    /**
//     * 替换 分钟、小时、日期、星期
//     */
//    private static final String ORIGINAL_CRON = "0 %s %s %s * %s";
//
//    /**
//     * 检查cron表达式的合法性
//     *
//     * @param cron cron exp
//     * @return true if valid
//     */
//    public static boolean checkValid(String cron) {
//        try {
//            // SPRING应该是使用最广泛的类型,但假若任务调度依赖于xxl-job平台,则需要调整为CronType.QUARTZ
//            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING);
//            CronParser parser = new CronParser(cronDefinition);
//            parser.parse(cron);
//        } catch (IllegalArgumentException e) {
//            log.error(String.format("cron=%s not valid", cron));
//            return false;
//        }
//        return true;
//    }
//
//    public static String buildCron(List<Integer> minutes, List<Integer> hours, List<Integer> weekdays) {
//        String minute;
//        if (minutes.equals(CronUtils.getInitMinutes())) {
//            minute = ASTERISK;
//        } else {
//            minute = StringUtils.join(minutes, COMMA);
//        }
//        String hour;
//        if (hours.equals(CronUtils.getInitHours())) {
//            hour = ASTERISK;
//        } else {
//            hour = StringUtils.join(hours, COMMA);
//        }
//        String weekday;
//        if (weekdays.equals(CronUtils.getInitWeekdays())) {
//            weekday = QUESTION;
//        } else {
//            weekday = StringUtils.join(weekdays, COMMA);
//        }
//        // 重点：星期和日字段冲突，判断周日的前端输入
//        if (weekday.equals(QUESTION)) {
//            return String.format(ORIGINAL_CRON, minute, hour, ASTERISK, weekday);
//        } else {
//            return String.format(ORIGINAL_CRON, minute, hour, QUESTION, weekday);
//        }
//    }
//
//    /**
//     * 解析db cron expression展示到前端
//     *
//     * @param cron cron
//     * @return minutes/hours/weekdays
//     */
//    public static CustomCronField parseCon(String cron) {
//        if (!CronUtils.checkValid(cron)) {
//            return null;
//        }
//        List<String> result = Arrays.asList(cron.trim().split(" "));
//        CustomCronField field = new CustomCronField();
//        if (result.get(1).contains(COMMA)) {
//            field.setMinutes(Arrays.stream(result.get(1).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
//        } else if (result.get(1).equals(ASTERISK)) {
//            field.setMinutes(CronUtils.getInitMinutes());
//        } else {
//            field.setMinutes(new ArrayList(Integer.parseInt(result.get(1))));
//        }
//        if (result.get(2).contains(COMMA)) {
//            field.setHours(Arrays.stream(result.get(2).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
//        } else if (result.get(2).equals(ASTERISK)) {
//            field.setHours(CronUtils.getInitHours());
//        } else {
//            field.setHours(new ArrayList(Integer.parseInt(result.get(2))));
//        }
//        if (result.get(5).contains(COMMA)) {
//            field.setWeekdays(Arrays.stream(result.get(5).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
//        } else if (result.get(5).equals(QUESTION)) {
//            field.setWeekdays(CronUtils.getInitWeekdays());
//        } else {
//            field.setWeekdays(new ArrayList(Integer.parseInt(result.get(5))));
//        }
//        return field;
//    }
//
//    private static List<Integer> initArray(Integer num) {
//        List<Integer> result =new ArrayList<>(num);
//        for (int i = 0; i <= num; i++) {
//            result.add(i);
//        }
//        return result;
//    }
//
//    private static List<Integer> getInitMinutes() {
//        return CronUtils.initArray(59);
//    }
//
//    private static List<Integer> getInitHours() {
//        return CronUtils.initArray(23);
//    }
//
//    private static List<Integer> getInitWeekdays() {
//        return CronUtils.initArray(7).subList(1, 8);
//
//    }
//
//    @Data
//    public static class CustomCronField {
//        private List<Integer> minutes;
//        private List<Integer> hours;
//        private List<Integer> weekdays;
//    }
//}

//package com.example.gladoscheckin.timingtask.common;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.List;
//
///**
// * cron 表达式工具类
// *
// * @author hhy
// */
//public class CronUtil {
//    /**
//     * 每天
//     */
//    private static final int DAY_JOB_TYPE = 1;
//    /**
//     * 每周
//     */
//    private static final int WEEK_JOB_TYPE = 2;
//    /**
//     * 每月
//     */
//    private static final int MONTH_JOB_TYPE = 3;
//
//    /**
//     * 构建Cron表达式
//     *
//     * @param jobType        作业类型: 1/每天; 2/每周; 3/每月
//     * @param minute         指定分钟
//     * @param hour           指定小时
//     * @param lastDayOfMonth 指定一个月的最后一天：0/不指定；1/指定
//     * @param weekDays       指定一周哪几天：1/星期天; 2/...3/..   ; 7/星期六
//     * @param monthDays      指定一个月的哪几天
//     * @return String
//     */
//    public static String createCronExpression(Integer jobType, Integer minute, Integer hour, Integer lastDayOfMonth, List<Integer> weekDays, List<Integer> monthDays) {
//        StringBuilder cronExp = new StringBuilder();
//        // 秒
//        cronExp.append("0 ");
//        // 指定分钟，为空则默认0分
//        cronExp.append(minute == null ? "0" : minute).append(" ");
//        // 指定小时，为空则默认0时
//        cronExp.append(hour == null ? "0" : hour).append(" ");
//        // 每天
//        if (jobType == DAY_JOB_TYPE) {
//            // 日
//            cronExp.append("* ");
//            // 月
//            cronExp.append("* ");
//            // 周
//            cronExp.append("?");
//        } else if (lastDayOfMonth != null && lastDayOfMonth == 1) {
//            // 日
//            cronExp.append("L ");
//            // 月
//            cronExp.append("* ");
//            // 周
//            cronExp.append("?");
//        }
//        // 按每周
//        else if (weekDays != null && jobType == WEEK_JOB_TYPE) {
//            // 日
//            cronExp.append("? ");
//            // 月
//            cronExp.append("* ");
//            // 一个周的哪几天
//            cronExp.append(StringUtils.join(weekDays, ","));
//        }
//        // 按每月
//        else if (monthDays != null && jobType == MONTH_JOB_TYPE) {
//            // 日
//            cronExp.append(StringUtils.join(monthDays, ",")).append(" ");
//            // 月
//            cronExp.append("* ");
//            // 周
//            cronExp.append("?");
//        } else {
//            cronExp.append("* ").append("* ").append("?");
//        }
//        return cronExp.toString();
//    }
//}

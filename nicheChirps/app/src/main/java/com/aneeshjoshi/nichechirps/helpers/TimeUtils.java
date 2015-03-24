package com.aneeshjoshi.nichechirps.helpers;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    public static String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final Pattern MATCH_ELAPSED_TIME = Pattern.compile("\\s(minutes\\sago|minute\\sago|" +
            "hours\\sago|hour\\sago|" +
            "day\\sago|days\\sago|" +
            "week\\sago|weeks\\sago|" +
            "month\\sago|months\\sago|" +
            "year\\sago|years\\sago)");

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        long dateMillis = getTimeInMillisFromCreatedString(rawJsonDate);

        String relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        return relativeDate;
    }

    public static long getTimeInMillisFromCreatedString(String createdAt) {

        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        try {
            return sf.parse(createdAt).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String getElapsedString(String elapsedTime) {
        Matcher m = MATCH_ELAPSED_TIME.matcher(elapsedTime);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String text = m.group(1);
            if(text.contains("minute")){
                m.appendReplacement(sb, "m");
            } else if(text.contains("hour")){
                m.appendReplacement(sb, "h");
            } else if(text.contains("day")){
                m.appendReplacement(sb, "d");
            } else if(text.contains("week")){
                m.appendReplacement(sb, "w");
            } else if(text.contains("month")){
                m.appendReplacement(sb, "Mons");
            } else if(text.contains("year")){
                m.appendReplacement(sb, "Yrs");
            }
            m.appendTail(sb);
        }
        return sb.toString();
    }
}

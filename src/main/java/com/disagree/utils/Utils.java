package com.disagree.utils;

import com.disagree.bean.resultBean.BaseModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by disagree on 2017/4/17.
 */
public class Utils {

    public static boolean checkAllNumbers(String str) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence)str);
        return matcher.matches();
    }

    public static String getResult(int ret_code1, int ret_code2, String message, Object _ret) {
        BaseModel baseModel = new BaseModel(ret_code1, ret_code2, message, _ret);
        return new Gson().toJson(baseModel);
    }

    public static long getDayBeginTimestamp() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
                * 1000 - gc.get(gc.MILLISECOND));
        return date2.getTime();
    }
    public static long getMinuteBeginTimestamp(){
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        Date date2 = new Date(date.getTime()  - gc.get(gc.SECOND)
                * 1000 - gc.get(gc.MILLISECOND));
        return date2.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getDayBeginTimestamp());
        System.out.println(System.currentTimeMillis());
        System.out.println("当前分钟的时间戳："+ getMinuteBeginTimestamp());
        System.out.println("当前分钟-1的时间戳："+ (getMinuteBeginTimestamp() - 60*1000));
    }
}

package graduation.mcs.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: xucz
 * @date: 2016-2-29
 * @description: 时间转制方法，其中的时间和日期都是狭义的，即两者是区分开的
 */
public class TimeUtils {

    /**
     * 带时间的毫秒 --> xxxx-xx-xx xx:xx
     *
     * @param secT 带时间的毫秒数
     * @return xxxx-xx-xx xx:xx
     */
    public static String secT2DateStrEn(long secT){
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateStr = sdf.format(new Date(secT));
        return dateStr;
    }

    /**
     * 带时间的秒secT --> "xx-xx","今天","明天" 若日期与当前系统日期一致，则显示"今天"，多1天则显示"明天"
     * 
     * @param secT
     *            带时间的秒数
     * @return "xx-xx","今天","明天"
     */
    public static String secT2DateStr(long secT) {
        String dateStr = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        dateStr = sdf.format(new Date(secT * 1000));
        int index = dateStr.indexOf("-");
        dateStr = dateStr.subSequence(0, index) + "月"
                + dateStr.substring(index + 1) + "日";

        // 判断日期与当前系统日期是否一致，用不带时间的秒secNT来判断！
        long secNT = TimeUtils.secT2secNT(secT);
        long todaysecT = new Date().getTime() / 1000;
        long todaysecNT = TimeUtils.secT2secNT(todaysecT);
        if (secNT == todaysecNT) {
            dateStr = "今天";
        } else if (secNT == (todaysecNT + 24 * 60 * 60)) {
            dateStr = "明天";
        }

        return dateStr;
    }

    /**
     * 带时间的秒secT --> "xx:xx"
     * 
     * @param secT
     *            带时间的秒secT
     * @return "xx:xx"
     */
    public static String secT2TimeStr(long secT) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(secT * 1000));
    }

    /**
     * 带时间的秒secT --> "xx-xx xx:xx" "今天 xx:xx" "明天 xx:xx"
     * 
     * @param secT
     *            带时间的秒secT
     * @return “xx-xx xx:xx” "今天 xx:xx" "明天 xx:xx"
     */
    public static String secT2DateAndTimeStr(long secT) {
        String dateStr = TimeUtils.secT2DateStr(secT);
        String timeStr = TimeUtils.secT2TimeStr(secT);

        return dateStr + " " + timeStr;
    }

    /**
     * 难点：带时间的秒secT --> 不带时间的秒secNT
     * 
     * @param secT
     *            带时间的秒secT
     * @return 不带时间的秒secNT
     */
    public static long secT2secNT(long secT) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(new Date(secT * 1000));
        Date result_date;
        long result_time = 0;
        try {
            result_date = sdf.parse(str);
            // 返回的是毫秒数故除以1000
            result_time = result_date.getTime() / 1000;
        } catch (ParseException e) {
            // 出现异常时间赋值为20000101000000
            result_time = 946684800;
            e.printStackTrace();
        }
        return result_time;
    }

    /**
     * 带时间的秒secT --> Calendar对象
     * 
     * @param secT
     *            带时间的秒secT
     * @return Calendar对象
     */
    public static Calendar secT2Calendar(long secT) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(secT * 1000);
        return calendar;
    }

    /**
     * Calendar对象 --> 带时间的秒secT
     * 
     * @param calendar
     *            Calendar对象
     * @return 带时间的秒secT
     */
    public static long calendar2secT(Calendar calendar) {
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * "xxx-xx-xx xx:xx" --> 带时间的秒secT 测试DAO用
     * 
     * @param dateAndTimeStr
     *            "xxx-xx-xx xx:xx"
     * @return 带时间的秒secT
     */
    public static long dateAndTimeStr2secT(String dateAndTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date result_date;
        long result_time = 0;
        try {
            result_date = sdf.parse(dateAndTimeStr);
            // 返回的是毫秒数故除以1000
            result_time = result_date.getTime() / 1000;
        } catch (ParseException e) {
            // 出现异常时间赋值为20000101000000
            result_time = 946684800;
            e.printStackTrace();
        }
        return result_time;
    }

    // ---- 用于调整DatePicker和TimePicker的大小

    /**
     * 调整FrameLayout大小
     * @param tp
     */
    public static void resizePikcer(FrameLayout tp, int width){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){
            resizeNumberPicker(np, width);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = null;
        if(npList == null){
            npList = new ArrayList<NumberPicker>();
        }
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /*
     * 调整numberpicker大小
     */
    private static void resizeNumberPicker(NumberPicker np, int width){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        np.setLayoutParams(params);
    }
}

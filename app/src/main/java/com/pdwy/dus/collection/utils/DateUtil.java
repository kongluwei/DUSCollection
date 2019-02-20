package com.pdwy.dus.collection.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : KongLW
 * e-mail : kongluweishujia@163.com
 * date   : 2019/2/2015:39
 * desc   :
 */
public class DateUtil {
    /**
     *
     * @param nowDate   要比较的时间
     * @param startDate   开始时间
     * @param endDate   结束时间
     * @return   true在时间段内，false不在时间段内
     * @throws Exception
     */
    public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception{

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = format.parse(nowDate);
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);

        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime >= startTime && nowTime <= endTime;
    }
//---------------------
//    作者：永恒的春天
//    来源：CSDN
//    原文：https://blog.csdn.net/lz199719/article/details/81261836
//    版权声明：本文为博主原创文章，转载请附上博文链接！

}

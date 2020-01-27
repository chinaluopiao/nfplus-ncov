package com.southcn.nfapp.ncov;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSpiderTest {

    @Test
    public void testSpider() throws Exception {

        String text = "截至 2020-01-27 13:58（北京时间）数据统计";
        Pattern compile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
        Matcher matcher = compile.matcher(text);
        while (matcher.find()){
            Date date = DateUtils.parseDate(matcher.group(), "yyyy-MM-dd HH:mm");
            System.out.println(DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
            System.out.println(matcher.group());
        }
        //System.out.println(JSON.toJSONString(provinceStats));
    }
}

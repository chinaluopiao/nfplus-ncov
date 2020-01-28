package com.southcn.nfapp.ncov;

import com.southcn.nfapp.ncov.utils.HtmlClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataSpiderTest {

    @Test
    public void testSpider() throws Exception {

        /*String text = "截至 2020-01-27 13:58（北京时间）数据统计";
        Pattern compile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
        Matcher matcher = compile.matcher(text);
        while (matcher.find()){
            Date date = DateUtils.parseDate(matcher.group(), "yyyy-MM-dd HH:mm");
            System.out.println(DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
            System.out.println(matcher.group());
        }*/

        String url = "https://3g.dxy.cn/newh5/view/pneumonia";
        HtmlClientUtils htmlClient = HtmlClientUtils.builder().build();
        htmlClient.executeJavaScript(url,"JSON.stringify(window.getStatisticsService)");

        String text1 = "确诊 2846 例，疑似 5794 例↵死亡 81 例，治愈 56 例";
        Pattern compile1 = Pattern.compile("\\d*");
        Matcher matcher1 = compile1.matcher(text1);
        List<String> list = new ArrayList<>();

        while (matcher1.find()){
            //System.out.println();
            list.add(matcher1.group());
        }
        String collect = list.stream().filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
        System.out.println(collect);
        System.out.println(list.size());
        System.out.println(list);
        //System.out.println(JSON.toJSONString(provinceStats));
    }
}

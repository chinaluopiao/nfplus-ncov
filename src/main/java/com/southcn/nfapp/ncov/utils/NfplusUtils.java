package com.southcn.nfapp.ncov.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.southcn.nfapp.ncov.bean.dxy.DxyAreaStat;
import com.southcn.nfapp.ncov.bean.nfplus.NfXlsData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.entity.NfXlsUnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NfplusUtils {

    public static UnifiedGlobal getUnifiedGlobal(String infoTime, Date date, List<NfXlsData> list) {
        UnifiedGlobal.UnifiedGlobalBuilder builder = UnifiedGlobal.builder();
        builder.time(date);
        builder.infoTime(infoTime);
        builder.confirm(list.parallelStream().mapToInt(NfXlsData::getConfirm).sum());
        builder.suspected(NumberUtils.INTEGER_ZERO);
        builder.cure(list.parallelStream().mapToInt(NfXlsData::getHeal).sum());
        builder.die(list.parallelStream().mapToInt(NfXlsData::getDead).sum());
        return builder.build();
    }

    public static Date parseXlsHeaderDate(String text) {
        //时间截止到29日12时
        try {
            Calendar calendar = Calendar.getInstance();
            LocalDate localDate = LocalDate.now();
            //时间减一秒，防止出现24小时
            long time = DateUtils.parseDate(text, "截止MM月dd日HH时数据统计").getTime();
            if (text.contains(NcovConst.NF_XLS_TIME_KEYWORD)) {
                time = time - 1000;
            }
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.YEAR, localDate.getYear());
            //月份需要减去1
            //calendar.set(Calendar.MONTH, localDate.getMonthValue() - NumberUtils.INTEGER_ONE);
            return calendar.getTime();
        } catch (ParseException e) {
            return new Date();
        }
    }


    public static UnifiedArea getUnifiedArea(UnifiedGlobal global, List<NfXlsData> list) {
        //UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder();
        UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder().country(NcovConst.CHINA_KEYWORD)
                .area(NcovConst.CHINA_GUANG_DONG_KEYWORD);
        //先解析数据  做数据处理转换
        builder.confirm(global.getConfirm()).suspect(NumberUtils.INTEGER_ZERO)
                .heal(global.getCure()).dead(global.getDie());

        //注释的代码为去掉排序
        builder.children(list.parallelStream().map(NfXlsData::toUnifiedArea)
                /*.sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed())*/.collect(Collectors.toList()));
        return builder.build();
    }

    public static List<UnifiedDay> getGdUnifiedDay(List<NfXlsUnifiedData> list) {
        List<UnifiedDay> gdDays = JSON.parseArray(NcovConst.NFPLUS_GUANG_DONG_DATA, UnifiedDay.class);
        return Stream.concat(gdDays.stream(), list.parallelStream().map(NfplusUtils::unifiedDataToUnifiedDay)).collect(Collectors.groupingBy(UnifiedDay::getDate)).values().stream()
                .map(unifiedDays -> unifiedDays.stream().max(Comparator.comparing(UnifiedDay::getConfirm)).orElse(null))
                .filter(Objects::nonNull).sorted(Comparator.comparing(UnifiedDay::getDate))
                .collect(Collectors.toList());
    }

    public static UnifiedDay unifiedDataToUnifiedDay(NfXlsUnifiedData data) {
        UnifiedDay.UnifiedDayBuilder builder = UnifiedDay.builder();
        builder.date(DateFormatUtils.format(data.getGlobal().getTime(), "MM.dd"))
                .confirm(data.getGlobal().getConfirm()).suspect(NumberUtils.INTEGER_ZERO)
                .heal(data.getGlobal().getCure()).dead(data.getGlobal().getDie());
        return builder.build();
    }
}

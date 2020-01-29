package com.southcn.nfapp.ncov.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.southcn.nfapp.ncov.bean.dxy.DxyAreaStat;
import com.southcn.nfapp.ncov.bean.nfplus.NfXlsData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NfplusUtils {

    public static UnifiedGlobal getUnifiedGlobal(Date date, List<NfXlsData> list) {
        UnifiedGlobal.UnifiedGlobalBuilder builder = UnifiedGlobal.builder();
        builder.time(date);
        builder.confirm(list.parallelStream().mapToInt(NfXlsData::getConfirm).sum());
        builder.suspected(NumberUtils.INTEGER_ZERO);
        builder.cure(list.parallelStream().mapToInt(NfXlsData::getHeal).sum());
        builder.die(list.parallelStream().mapToInt(NfXlsData::getDead).sum());
        return builder.build();
    }

    public static Date parseXlsHeaderDate(String text) {
        //时间截止到29日12时
        try {
            return DateUtils.parseDate(text, "时间截止到dd日HH时");
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

        builder.children(list.parallelStream().map(NfXlsData::toUnifiedArea)
                .sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed()).collect(Collectors.toList()));
        return builder.build();
    }

    public static List<UnifiedDay> getUnifiedDay(List<UnifiedData> list) {
        List<UnifiedDay> gdDays = JSON.parseArray(NcovConst.NFPLUS_GUANG_DONG_DATA, UnifiedDay.class);
        return Stream.concat(gdDays.stream(), list.parallelStream().map(NfplusUtils::unifiedDataToUnifiedDay))
                .sorted(Comparator.comparing(UnifiedDay::getDate)).collect(Collectors.toList());
    }

    public static UnifiedDay unifiedDataToUnifiedDay(UnifiedData data) {
        UnifiedDay.UnifiedDayBuilder builder = UnifiedDay.builder();
        builder.date(DateFormatUtils.format(data.getGlobal().getTime(), "MM.dd"))
                .confirm(data.getGlobal().getConfirm()).suspect(NumberUtils.INTEGER_ZERO)
                .heal(data.getGlobal().getCure()).dead(data.getGlobal().getDie());
        return builder.build();
    }
}

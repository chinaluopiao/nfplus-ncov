package com.southcn.nfapp.ncov.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.southcn.nfapp.ncov.bean.dxy.DxyAbroadStat;
import com.southcn.nfapp.ncov.bean.dxy.DxyAreaStat;
import com.southcn.nfapp.ncov.unified.UnifiedArea;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DxyUtils {

    /**
     * 解析境内数据
     *
     * @param areaStatScriptResult
     * @return
     */
    public static List<UnifiedArea> parseToComesticUnifiedArea(ScriptResult areaStatScriptResult) {
        //先解析数据  做数据处理转换
        return JSON.parseObject(areaStatScriptResult.getJavaScriptResult().toString(), new TypeReference<List<DxyAreaStat>>() {
        }).parallelStream().map(DxyAreaStat::toUnifiedArea)
                .sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed()).collect(Collectors.toList());
    }

    public static List<UnifiedArea> parseToAbroadUnifiedArea(ScriptResult abroadScriptResult) {
        return JSON.parseObject(abroadScriptResult.getJavaScriptResult().toString(), new TypeReference<List<DxyAbroadStat>>() {
        }).parallelStream().map(DxyAbroadStat::toUnifiedArea)
                .sorted(Comparator.comparing(UnifiedArea::getConfirm)).collect(Collectors.toList());
    }

}

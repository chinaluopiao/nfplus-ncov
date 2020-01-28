package com.southcn.nfapp.ncov.bean.tx;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 腾讯应答数据
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TxRespond implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * 状态
     */
    private Integer ret;

    /**
     * 数据
     */
    private String data;


    /**
     * 获取解析的数据
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getParseData(Class<T> clazz) {
        return JSON.parseObject(this.data, clazz);
    }

    public <T> List<T> getParseArrData(Class<T> clazz) {
        return JSON.parseArray(this.data, clazz);
    }

    public UnifiedGlobal getGlobal() {
        return this.getParseArrData(TxGlobalData.class).stream().findFirst().map(TxGlobalData::getGlobal).orElse(null);
    }

    public List<UnifiedDay> getDayData() {
        //return this.getParseArrData(TxDayCounts.class).stream().findFirst().map(TxDayCounts::getUnifiedDay).orElse(null);
        return this.getParseArrData(TxDayCounts.class).stream().map(TxDayCounts::getUnifiedDay).collect(Collectors.toList());
    }

    public List<UnifiedArea> getUnifiedAreas() {
        return this.getParseArrData(TxAreaCounts.class).stream().map(TxAreaCounts::getUnifiedArea).collect(Collectors.toList());
    }

    /**
     * 组装国内数据
     *
     * @return
     */
    public List<UnifiedArea> getDomesticUnifiedArea() {
        List<UnifiedArea> list = this.getUnifiedAreas();
        Map<String, List<UnifiedArea>> listMap = list.stream().collect(Collectors.groupingBy(UnifiedArea::getCountry));
        List<UnifiedArea> domesticAreas = listMap.get(NcovConst.CHINA_KEYWORD);
        return domesticAreas.stream().collect(Collectors.groupingBy(UnifiedArea::getArea)).entrySet().stream().map(e -> {
            UnifiedArea.UnifiedAreaBuilder builder = UnifiedArea.builder();
            builder.country(NcovConst.CHINA_KEYWORD);
            builder.area(e.getKey());
            builder.children(e.getValue());
            builder.confirm(e.getValue().parallelStream().mapToInt(UnifiedArea::getConfirm).sum());
            builder.suspect(e.getValue().parallelStream().mapToInt(UnifiedArea::getSuspect).sum());
            builder.heal(e.getValue().parallelStream().mapToInt(UnifiedArea::getHeal).sum());
            builder.dead(e.getValue().parallelStream().mapToInt(UnifiedArea::getDead).sum());
            return builder.build();
        }).sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed()).collect(Collectors.toList());
    }


    /**
     * 获取广东数据
     *
     * @return
     */
    public UnifiedArea getGuangDongUnifiedArea() {
        return this.getDomesticUnifiedArea().stream().filter(value -> StringUtils.equals(value.getArea(), NcovConst.CHINA_GUANG_DONG_KEYWORD)).findFirst().orElse(null);
    }

    /**
     * 组装国外数据
     *
     * @return
     */
    public List<UnifiedArea> getAbroadUnifiedArea() {
        List<UnifiedArea> list = this.getUnifiedAreas();
        return list.stream().filter(unifiedArea -> !StringUtils.equals(unifiedArea.getCountry(), NcovConst.CHINA_KEYWORD))
                .sorted(Comparator.comparing(UnifiedArea::getConfirm).reversed()).collect(Collectors.toList());

    }

}

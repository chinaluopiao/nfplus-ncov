package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.bean.tx.TxRespond;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.TxDataService;
import com.southcn.nfapp.ncov.unified.UnifiedArea;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedDay;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TxDataServiceImpl implements TxDataService {
    private final String url1 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_global_vars";
    private final String url2 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_cn_day_counts";
    private final String url3 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_area_counts";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Boolean spider() {
        UnifiedData.UnifiedDataBuilder builder = UnifiedData.builder();
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        TxRespond globalTRespond = JSON.parseObject(httpUtils.get(url1), TxRespond.class);
        TxRespond dayCountsRespond = JSON.parseObject(httpUtils.get(url2), TxRespond.class);
        TxRespond areaCountsRespond = JSON.parseObject(httpUtils.get(url3), TxRespond.class);
        builder.global(globalTRespond.getGlobal());
        builder.days(dayCountsRespond.getDayData());
        builder.domestic(areaCountsRespond.getDomesticUnifiedArea());
        builder.abroad(areaCountsRespond.getAbroadUnifiedArea());
        UnifiedArea guangDongUnifiedArea = areaCountsRespond.getGuangDongUnifiedArea();
        List<UnifiedDay> gdDays = JSON.parseArray(NcovConst.NFPLUS_GUANG_DONG_DATA, UnifiedDay.class);
        Object value = this.redisTemplate.opsForValue().get(NcovConst.NFPLUS_GUANG_DONG_DATA_KEY);
        if (value instanceof List) {
            gdDays.addAll((List<UnifiedDay>) value);
        }

        if (Objects.nonNull(guangDongUnifiedArea)) {
            gdDays.add(UnifiedDay.builder().date(DateFormatUtils.format(new Date(), "MM.dd"))
                    .confirm(guangDongUnifiedArea.getConfirm()).suspect(guangDongUnifiedArea.getSuspect())
                    .dead(guangDongUnifiedArea.getDead()).heal(guangDongUnifiedArea.getHeal()).build());
        }

        List<UnifiedDay> gdDayList = gdDays.stream().collect(Collectors.groupingBy(UnifiedDay::getDate)).values().stream()
                .map(unifiedDays -> unifiedDays.stream().max(Comparator.comparing(UnifiedDay::getConfirm)).orElse(null))
                .filter(Objects::nonNull).sorted(Comparator.comparing(UnifiedDay::getDate))
                .collect(Collectors.toList());
        builder.gdDays(gdDayList);
        UnifiedData data = builder.build();
        log.info("腾讯数据缓存:{}", JSON.toJSONString(data).length());
        this.redisTemplate.opsForValue().set(NcovConst.TX_NCOV_DATA, data);
        return Boolean.TRUE;
    }
}

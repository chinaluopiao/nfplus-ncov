package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.southcn.nfapp.ncov.bean.DxStatistics;
import com.southcn.nfapp.ncov.bean.PneumoniaStats;
import com.southcn.nfapp.ncov.bean.ProvinceStat;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.DxyDataService;
import com.southcn.nfapp.ncov.utils.HtmlClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class DxyDataServiceImpl implements DxyDataService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean spider() {
        try {
            String url = "https://3g.dxy.cn/newh5/view/pneumonia";
            HtmlClientUtils clientUtils = HtmlClientUtils.builder().build();
            String getAreaStatCommand = "JSON.stringify(window.getAreaStat)";
            String getStatisticsCommand = "JSON.stringify(window.getStatisticsService)";
            Map<String, ScriptResult> resultMap = clientUtils.executeJavaScript(url, getAreaStatCommand, getStatisticsCommand);

            ScriptResult areaStatScriptResult = resultMap.get(getAreaStatCommand);
            ScriptResult statisticsScriptResult = resultMap.get(getStatisticsCommand);
            List<ProvinceStat> provinceStats = JSON.parseObject(areaStatScriptResult.getJavaScriptResult().toString(), new TypeReference<List<ProvinceStat>>() {
            });
            PneumoniaStats.PneumoniaStatsBuilder builder = PneumoniaStats.builder();
            AtomicInteger confirmedCount = new AtomicInteger();
            AtomicInteger suspectedCount = new AtomicInteger();
            AtomicInteger curedCount = new AtomicInteger();
            AtomicInteger deadCount = new AtomicInteger();
            log.info("丁香汇总数据执行结果:{}", JSON.toJSONString(statisticsScriptResult));
            DxStatistics statistics = JSON.parseObject(statisticsScriptResult.getJavaScriptResult().toString(), DxStatistics.class);
            log.info("丁香汇总数据:{}", JSON.toJSONString(statistics));
            if (Objects.nonNull(statistics)) {
                confirmedCount.addAndGet(statistics.getConfirmedCount());
                suspectedCount.addAndGet(statistics.getSuspectedCount());
                curedCount.addAndGet(statistics.getCuredCount());
                deadCount.addAndGet(statistics.getDeadCount());
                //处理时间
                builder.statsTime(statistics.getModifyTime());
                //处理图片
                try {
                    String dailyPic = statistics.getDailyPic();
                    log.info("丁香医生趋势图地址:{}", dailyPic);
                    byte[] bytes = IOUtils.toByteArray(new URL(dailyPic));
                    this.stringRedisTemplate.opsForValue().set(NcovConst.DXY_NCOV_DATA_DIAGRAM, Base64.encodeBase64String(bytes));
                } catch (Exception e) {
                    log.error("丁香医生趋势图地址出错", e);
                }
            }
            PneumoniaStats pneumoniaStats = builder.confirmedCount(confirmedCount.get())
                    .suspectedCount(suspectedCount.get()).curedCount(curedCount.get())
                    .deadCount(deadCount.get()).provinceStats(provinceStats).build();
            this.stringRedisTemplate.opsForValue().set(NcovConst.DXY_NCOV_DATA, JSON.toJSONString(pneumoniaStats));
        } catch (Exception e) {
            log.error("丁香医生数据抓取失败", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

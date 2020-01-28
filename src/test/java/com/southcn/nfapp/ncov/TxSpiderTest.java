package com.southcn.nfapp.ncov;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.southcn.nfapp.ncov.bean.tx.TxRespond;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import com.southcn.nfapp.ncov.unified.UnifiedGlobal;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class TxSpiderTest {
    private String url1 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_global_vars";
    private String url2 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_cn_day_counts";
    private String url3 = "https://view.inews.qq.com/g2/getOnsInfo?name=wuwei_ww_area_counts";

    @Test
    public void testSpider() throws Exception {
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        Arrays.asList(url1, url2, url3).forEach(url -> {
            String string = httpUtils.get(url);
            //log.info("原始数据:{}", string);
            JSONObject response = JSON.parseObject(string);
            log.info(response.get("data").toString());
        });
    }
    @Test
    public void testSpider1() throws Exception {
        UnifiedData.UnifiedDataBuilder builder = UnifiedData.builder();
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        TxRespond globalTRespond = JSON.parseObject(httpUtils.get(url1), TxRespond.class);
        TxRespond dayCountsRespond = JSON.parseObject(httpUtils.get(url2), TxRespond.class);
        TxRespond areaCountsRespond = JSON.parseObject(httpUtils.get(url3), TxRespond.class);
        builder.global(globalTRespond.getGlobal());
        builder.days(dayCountsRespond.getDayData());
        builder.domestic(areaCountsRespond.getDomesticUnifiedArea());
        builder.abroad(areaCountsRespond.getAbroadUnifiedArea());
        UnifiedData data = builder.build();
        log.info("腾讯数据缓存:{}", JSON.toJSONString(data));
    }

}

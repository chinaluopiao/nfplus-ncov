package com.southcn.nfapp.ncov.task;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class SpiderTask {

    private OkHttpClient client = new OkHttpClient();
    private String url = "https://h5.peopleapp.com/2019ncov/Home/index";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedDelay = 1800000)
    public void spider() {
        log.info("数据抓取开始。。。。");
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Optional.ofNullable(response.body()).ifPresent(value -> {
                    try {
                        String string = value.string();
                        if (StringUtils.isNotBlank(string)) {
                            NcovData ncovData = JSON.parseObject(string, NcovData.class);
                            log.info("{} 抓取数据:{}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), string);
                            this.stringRedisTemplate.opsForValue().set(NcovConst.NCOV_DATA, JSON.toJSONString(ncovData));
                        }
                    } catch (IOException e) {
                        log.error("数据抓取出错", e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("数据抓取出错", e);
        }
        log.info("数据抓取结束。。。。");
    }


}

package com.southcn.nfapp.ncov.task;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.PneumoniaService;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SpiderTask {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PneumoniaService pneumoniaService;

    @Scheduled(fixedDelay = 1800000)
    public void spider() {
        log.info("人民网数据抓取开始。。。。");
        final String url = "https://h5.peopleapp.com/2019ncov/Home/index";
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            NcovData ncovData = JSON.parseObject(string, NcovData.class);
            this.stringRedisTemplate.opsForValue().set(NcovConst.NCOV_DATA, JSON.toJSONString(ncovData));
        }
        log.info("人民网数据抓取结束。。。。");
    }

    @Scheduled(fixedDelay = 1800000)
    public void spiderPneumonia() {
        log.info("丁香医生数据抓取开始。。。。");
        Boolean result = this.pneumoniaService.spider();
        log.info("丁香医生数据抓取结束。 结果:{}", result);

    }

    @Scheduled(fixedDelay = 3000000)
    public void spiderSpecialTopic() {
        log.info("专题稿件数据。。。。");
        final String url = "https://api.nfapp.southcn.com/nanfang_if/v1/getSpecialTopic?columnId=17076&count=20&type=0";
        try {
            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 5));
        } catch (InterruptedException e) {
            log.error("睡眠",e);
        }
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            log.info("专题稿件数据不为空，存储缓存");
            this.stringRedisTemplate.opsForValue().set(NcovConst.NFPLUS_SPECIAL_TOPIC_DATA, string);
        }
        log.info("专题稿件数据抓取结束。");
    }

    @Scheduled(fixedDelay = 3000000)
    public void spiderRefuting() {
        log.info("辟谣专题稿件数据。。。。");
        final String url = "https://api.nfapp.southcn.com/nanfang_if/v1/getArticles?columnId=17162&rowNumber=0&lastFieldId=0&service=0&location=&version=0&nfhSubCount=0&count=20";
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            this.stringRedisTemplate.opsForValue().set(NcovConst.NFPLUS_REFUTING_TOPIC_DATA, string);
        }
        log.info("辟谣稿件数据抓取结束。");
    }
}

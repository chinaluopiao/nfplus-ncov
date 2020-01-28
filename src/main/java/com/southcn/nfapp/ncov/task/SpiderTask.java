package com.southcn.nfapp.ncov.task;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.DxyDataService;
import com.southcn.nfapp.ncov.service.NfplusService;
import com.southcn.nfapp.ncov.service.TxDataService;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpiderTask {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DxyDataService dxyDataService;

    @Autowired
    private TxDataService txDataService;

    @Autowired
    private NfplusService nfplusService;

    @Scheduled(fixedDelay = 60000)
    public void spiderPeopleapp() {
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

    @Scheduled(fixedDelay = 60000)
    public void spiderDxy() {
        log.info("丁香医生数据抓取开始。。。。");
        Boolean result = this.dxyDataService.spider();
        log.info("丁香医生数据抓取结束。 结果:{}", result);
    }


    @Scheduled(fixedDelay = 60000)
    public void spiderTx() {
        log.info("腾讯数据抓取开始。。。。");
        Boolean result = this.txDataService.spider();
        log.info("腾讯数据抓取结束。 结果:{}", result);
    }


    @Scheduled(fixedDelay = 300000)
    public void spiderSpecialTopic() {
        log.info("专题稿件数据。。。。");
        this.nfplusService.spiderSpecialTopic();
        log.info("专题稿件数据抓取结束。");
    }

    @Scheduled(fixedDelay = 300000)
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

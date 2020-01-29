package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.PeopleDataService;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PeopleDataServiceImpl implements PeopleDataService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Boolean spider() {
        log.info("人民网数据抓取开始。。。。");
        final String url = "https://h5.peopleapp.com/2019ncov/Home/index";
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            NcovData ncovData = JSON.parseObject(string, NcovData.class);
            this.redisTemplate.opsForValue().set(NcovConst.NCOV_DATA, ncovData);
        }
        log.info("人民网数据抓取结束。。。。");
        return Boolean.TRUE;
    }
}

package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.NfplusService;
import com.southcn.nfapp.ncov.utils.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NfplusServiceImpl implements NfplusService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean spiderSpecialTopic() {
        log.info("专题稿件数据。。。。");
        final String url = "https://api.nfapp.southcn.com/nanfang_if/v1/getSpecialTopic?columnId=17076&count=20&type=0";
        OkHttpUtils httpUtils = OkHttpUtils.builder().build();
        String string = httpUtils.get(url);
        if (StringUtils.isNotBlank(string)) {
            log.info("专题稿件数据不为空，存储缓存");
            this.stringRedisTemplate.opsForValue().set(NcovConst.NFPLUS_SPECIAL_TOPIC_DATA, string);

            JSONObject jsonObject = JSON.parseObject(string);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray columns = data.getJSONArray("columns");
            //获取到全部稿件数据
            List<JSONObject> articles = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                JSONArray jsonArray = columns.getJSONObject(i).getJSONObject("articles").getJSONArray("list");
                for (int j = 0; j < jsonArray.size(); j++) {
                    articles.add(jsonArray.getJSONObject(j));
                }
            }

            //全国版本最新动态
            List<JSONObject> collect = articles.parallelStream().
                    filter(value -> NcovConst.NEW_DYNAMIC_COLUMNS.contains(value.getInteger("colID"))).sorted(Comparator.comparing(value -> value.getString("publishtime"))).collect(Collectors.toList());
            Collections.reverse(collect);
            this.redisTemplate.opsForValue().set(NcovConst.NEW_DYNAMIC_COLUMNS_ARTICLES, collect);

            //广东版本

            List<JSONObject> gDcollect = articles.parallelStream().
                    filter(value -> NcovConst.NEW_DYNAMIC_GUANGDONG_COLUMNS.contains(value.getInteger("colID"))).sorted(Comparator.comparing(value -> value.getString("publishtime"))).collect(Collectors.toList());
            Collections.reverse(gDcollect);
            this.redisTemplate.opsForValue().set(NcovConst.NEW_DYNAMIC_COLUMNS_GUANGDONG_ARTICLES, gDcollect);

        }
        log.info("专题稿件数据抓取结束。");
        return null;
    }
}

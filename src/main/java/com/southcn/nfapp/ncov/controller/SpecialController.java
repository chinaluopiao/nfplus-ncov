package com.southcn.nfapp.ncov.controller;

import com.southcn.nfapp.ncov.assist.Response;
import com.southcn.nfapp.ncov.assist.ResponseBuilder;
import com.southcn.nfapp.ncov.bean.NfplusCnov;
import com.southcn.nfapp.ncov.constant.NcovConst;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("special")
public class SpecialController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "获取专题数据", response = String.class)
    @GetMapping("getSpecialTopic")
    public Mono<Response> getSpecialTopic() {
        return Mono.just(ResponseBuilder.buildSuccess(this.stringRedisTemplate.opsForValue().get(NcovConst.NFPLUS_SPECIAL_TOPIC_DATA)));
    }
}

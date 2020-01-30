package com.southcn.nfapp.ncov.controller;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.assist.Response;
import com.southcn.nfapp.ncov.assist.ResponseBuilder;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.bean.NfplusCnov;
import com.southcn.nfapp.ncov.bean.PneumoniaStats;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("ncov")
public class NcovController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation(value = "获取疫情数据-人民日报", response = NfplusCnov.class)
    @GetMapping("data")
    @Deprecated
    public Mono<Response> data() {
        return Mono.just(ResponseBuilder.buildSuccess(this.redisTemplate.opsForValue().get(NcovConst.NCOV_DATA)));
    }

    @ApiOperation(value = "获取地区疫情数据-丁香医生", response = PneumoniaStats.class)
    @Deprecated
    @GetMapping("areaData")
    public Mono<Response> areaData() {
        String value = this.stringRedisTemplate.opsForValue().get(NcovConst.DXY_NCOV_DATA);
        if (StringUtils.isNoneBlank(value)) {
            return Mono.just(ResponseBuilder.buildSuccess(JSON.parseObject(value, PneumoniaStats.class)));
        } else {
            return Mono.just(ResponseBuilder.buildFail());
        }
    }

    @ApiOperation(value = "获取疫情趋势图，base64编码", response = PneumoniaStats.class)
    @Deprecated
    @GetMapping("getDiagram")
    public Mono<Response> getDiagram() {
        String value = this.stringRedisTemplate.opsForValue().get(NcovConst.DXY_NCOV_DATA_DIAGRAM);
        return Mono.just(ResponseBuilder.buildSuccess(value));
    }

    @ApiOperation(value = "获取疫情数据-腾讯版本(统一版本)", response = UnifiedData.class)
    @GetMapping(value = {"getTxUnifiedData"})
    public Mono<Response> getTxUnifiedData() {
        return Mono.just(ResponseBuilder.buildSuccess(this.redisTemplate.opsForValue().get(NcovConst.TX_NCOV_DATA)));
    }


    @ApiOperation(value = "获取疫情数据-南方+版本(统一版本)", response = UnifiedData.class)
    @GetMapping(value = {"getUnifiedData"})
    public Mono<Response> getUnifiedData() {
        Object value = this.redisTemplate.opsForValue().get(NcovConst.NF_UNIFIED_NCOV_DATA);
        if (Objects.isNull(value)) {
            value = this.redisTemplate.opsForValue().get(NcovConst.TX_NCOV_DATA);
        }
        return Mono.just(ResponseBuilder.buildSuccess(value));
    }

    @ApiOperation(value = "获取疫情数据-丁香版本(统一版本)", response = UnifiedData.class)
    @GetMapping("getDxUnifiedData")
    public Mono<Response> getDxUnifiedData() {
        return Mono.just(ResponseBuilder.buildSuccess(this.redisTemplate.opsForValue().get(NcovConst.DYX_UNIFIED_NCOV_DATA)));
    }

    @ApiOperation(value = "获取疫情数据-南方+广东版本(统一版本)", response = UnifiedData.class)
    @GetMapping("getGdUnifiedData")
    public Mono<Response> getGdUnifiedData() {
        return Mono.just(ResponseBuilder.buildSuccess(this.redisTemplate.opsForValue().get(NcovConst.NF_XLS_UNIFIED_NCOV_DATA)));
    }


}

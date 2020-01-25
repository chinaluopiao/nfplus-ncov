package com.southcn.nfapp.ncov.controller;

import com.alibaba.fastjson.JSON;
import com.southcn.nfapp.ncov.assist.Response;
import com.southcn.nfapp.ncov.assist.ResponseBuilder;
import com.southcn.nfapp.ncov.bean.NcovData;
import com.southcn.nfapp.ncov.bean.NfplusCnov;
import com.southcn.nfapp.ncov.constant.NcovConst;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("ncov")
public class NcovController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "获取疫情数据", response = NfplusCnov.class)
    @GetMapping("data")
    public Mono<Response> data() {
        String value = this.stringRedisTemplate.opsForValue().get(NcovConst.NCOV_DATA);
        if (StringUtils.isNoneBlank(value)) {
            NcovData ncovData = JSON.parseObject(value, NcovData.class);
            NfplusCnov nfplusCnov = NfplusCnov.builder().statsTime(ncovData.getStatsTime()).globalStats(ncovData.getGlobalStats())
                    .provStats(ncovData.getProvStats()).otherStats(ncovData.getOtherStats()).build();
            return Mono.just(ResponseBuilder.buildSuccess(nfplusCnov));
        } else {
            return Mono.just(ResponseBuilder.buildFail());
        }
    }

}
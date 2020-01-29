package com.southcn.nfapp.ncov.controller;

import com.southcn.nfapp.ncov.assist.Response;
import com.southcn.nfapp.ncov.assist.ResponseBuilder;
import com.southcn.nfapp.ncov.bean.PneumoniaStats;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.DxyDataService;
import com.southcn.nfapp.ncov.service.PeopleDataService;
import com.southcn.nfapp.ncov.service.TxDataService;
import com.southcn.nfapp.ncov.unified.UnifiedData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sync")
public class SyncController {

    @Autowired
    private TxDataService txDataService;

    @Autowired
    private DxyDataService dxyDataService;

    @Autowired
    private PeopleDataService peopleDataService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation(value = "刷新腾讯数据-手动触发", response = Boolean.class)
    @GetMapping("tx")
    public Mono<Response> syncTx() {
        return Mono.just(ResponseBuilder.buildSuccess(this.txDataService.spider()));
    }

    @ApiOperation(value = "刷丁香数据-手动触发", response = Boolean.class)
    @GetMapping("dx")
    public Mono<Response> syncDx() {
        return Mono.just(ResponseBuilder.buildSuccess(this.dxyDataService.spider()));
    }

    @ApiOperation(value = "刷人民网数据-手动触发", response = Boolean.class)
    @GetMapping("people")
    public Mono<Response> syncPeople() {
        return Mono.just(ResponseBuilder.buildSuccess(this.peopleDataService.spider()));
    }

    //@ApiOperation(value = "人工刷新腾讯数据", response = Boolean.class)
    //@PostMapping("updateTx")
    /*public Mono<Response> updateTx(@RequestBody UnifiedData unifiedData) {
        this.redisTemplate.opsForValue().set(NcovConst.TX_NCOV_DATA, unifiedData);
        this.redisTemplate.opsForValue().set(NcovConst.NF_UNIFIED_NCOV_DATA, unifiedData);
        return Mono.just(ResponseBuilder.buildSuccess(Boolean.TRUE));
    }*/

}

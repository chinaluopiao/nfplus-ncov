package com.southcn.nfapp.ncov.controller;

import com.southcn.nfapp.ncov.assist.Response;
import com.southcn.nfapp.ncov.assist.ResponseBuilder;
import com.southcn.nfapp.ncov.service.DxyDataService;
import com.southcn.nfapp.ncov.service.PeopleDataService;
import com.southcn.nfapp.ncov.service.TxDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}

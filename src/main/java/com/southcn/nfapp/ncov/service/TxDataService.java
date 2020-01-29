package com.southcn.nfapp.ncov.service;

public interface TxDataService {

    /**
     * 腾讯数据抓取
     *
     * @return
     */
    public Boolean spider();

    /**
     * 腾讯数据回写
     *
     * @return
     */
    public Boolean txWriteback();
}

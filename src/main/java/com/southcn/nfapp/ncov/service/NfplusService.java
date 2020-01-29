package com.southcn.nfapp.ncov.service;

public interface NfplusService {
    /**
     * 爬取专题数据
     *
     * @return
     */
    public Boolean spiderSpecialTopic();

    /**
     * 南方+数据回写
     *
     * @return
     */
    public Boolean nfWriteback();

    /**
     * 南方+文件回写版本
     *
     * @return
     */
    public Boolean nfFileWriteback();
}

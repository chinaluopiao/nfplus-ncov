package com.southcn.nfapp.ncov.service;

import com.gargoylesoftware.htmlunit.ScriptResult;

public interface DxyDataService {

    /**
     * 丁香医生数据抓取
     *
     * @return
     */
    public Boolean spider();

    /**
     * 常规处理，兼容旧版
     *
     * @param areaStatScriptResult
     * @param statisticsScriptResult
     * @return
     */
    public Boolean handleNormal(ScriptResult areaStatScriptResult, ScriptResult statisticsScriptResult);

    /**
     * 统一格式处理
     *
     * @param areaStatScriptResult
     * @param statisticsScriptResult
     * @return
     */
    public Boolean handleUnified(ScriptResult areaStatScriptResult, ScriptResult statisticsScriptResult, ScriptResult abroadScriptResult);

    /**
     * 丁香医数据回写
     *
     * @return
     */
    public Boolean dxyWriteback();

}

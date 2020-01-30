package com.southcn.nfapp.ncov.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NcovConst {
    //人民网数据
    public static final String NCOV_DATA = "ncov_data";
    //丁香医生普通数据
    public static final String DXY_NCOV_DATA = "dxy_ncov_data";
    //丁香医生折线图
    public static final String DXY_NCOV_DATA_DIAGRAM = "dxy_ncov_data_diagram";
    //腾讯统一处理的数据格式
    public static final String TX_NCOV_DATA = "tx_ncov_data";
    /**
     * 丁香医统一数据格式
     */
    public static final String DYX_UNIFIED_NCOV_DATA = "dyx_unified_ncov_data";
    //南方+统一处理的数据格式
    public static final String NF_UNIFIED_NCOV_DATA = "nf_unified_ncov_data";
    //南方+专题数据
    public static final String NFPLUS_SPECIAL_TOPIC_DATA = "nfplus_special_topic_data";
    //南方+辟谣数据
    public static final String NFPLUS_REFUTING_TOPIC_DATA = "nfplus_refuting_topic_data";
    //广东省缓存数据，南方+来源
    public static final String NFPLUS_GUANG_DONG_DATA_KEY = "nfplus_guang_dong_data_key";
    //广东省每日历史数据，手动收集的
    public static final String NFPLUS_GUANG_DONG_DATA = "[{\"date\":\"01.19\",\"confirm\":1,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.20\",\"confirm\":17,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.21\",\"confirm\":26,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.22\",\"confirm\":32,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.23\",\"confirm\":53,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.24\",\"confirm\":78,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.25\",\"confirm\":98,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.26\",\"confirm\":146,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.27\",\"confirm\":188,\"suspect\":0,\"dead\":0,\"heal\":4},{\"date\":\"01.28\",\"confirm\":241,\"suspect\":0,\"dead\":0,\"heal\":5},{\"date\":\"01.29\",\"confirm\":311,\"suspect\":1514,\"dead\":0,\"heal\":6}]";
    public static final String CHINA_KEYWORD = "中国";
    public static final String CHINA_GUANG_DONG_KEYWORD = "广东";
    /**
     * 最新动态栏目数据 武汉动态、广东情况、各地情况、病毒溯源
     */
    public static final List<Integer> NEW_DYNAMIC_COLUMNS = Arrays.asList(17112, 17078, 17079, 17117);
    public static final String NEW_DYNAMIC_COLUMNS_ARTICLES = "NEW_DYNAMIC_COLUMNS_ARTICLES";


    /**
     * 广东省版本 最新动态
     */
    public static final List<Integer> NEW_DYNAMIC_GUANGDONG_COLUMNS = Collections.singletonList(17078);
    public static final String NEW_DYNAMIC_COLUMNS_GUANGDONG_ARTICLES = "new_dynamic_columns_guangdong_articles";

    public static final String NF_FILE_WRITEBACK_FILENAME = "广东省疫情数据统计.xls";
    public static final String NF_XLS_TIME_KEYWORD = "24时";
    //南方+版本的统一数据格式
    public static final String NF_XLS_UNIFIED_NCOV_DATA = "nf_xls_unified_ncov_data";


}

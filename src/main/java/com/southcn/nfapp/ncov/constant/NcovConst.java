package com.southcn.nfapp.ncov.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NcovConst {
    public static final String NCOV_DATA = "ncov_data";
    public static final String DXY_NCOV_DATA = "dxy_ncov_data";
    public static final String DXY_NCOV_DATA_DIAGRAM = "dxy_ncov_data_diagram";
    public static final String TX_NCOV_DATA = "tx_ncov_data";
    public static final String NFPLUS_SPECIAL_TOPIC_DATA = "nfplus_special_topic_data";
    public static final String NFPLUS_REFUTING_TOPIC_DATA = "nfplus_refuting_topic_data";
    public static final String NFPLUS_GUANG_DONG_DATA_KEY = "nfplus_guang_dong_data_key";
    public static final String NFPLUS_GUANG_DONG_DATA = "[{\"date\":\"01.19\",\"confirm\":1,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.20\",\"confirm\":17,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.21\",\"confirm\":26,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.22\",\"confirm\":32,\"suspect\":0,\"dead\":0,\"heal\":0},{\"date\":\"01.23\",\"confirm\":53,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.24\",\"confirm\":78,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.25\",\"confirm\":98,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.26\",\"confirm\":146,\"suspect\":0,\"dead\":0,\"heal\":2},{\"date\":\"01.27\",\"confirm\":188,\"suspect\":0,\"dead\":0,\"heal\":4}]";

    public static final String CHINA_KEYWORD = "中国";
    public static final String CHINA_GUANG_DONG_KEYWORD = "广东";
    /**
     * 最新动态栏目数据 武汉动态、广东情况、各地情况、病毒溯源
     */
    public static final List<Integer> NEW_DYNAMIC_COLUMNS = Arrays.asList(17112, 17078, 17079, 17117);
    public static final String NEW_DYNAMIC_COLUMNS_ARTICLES = "NEW_DYNAMIC_COLUMNS_ARTICLES";


    /**
     * 广东省版本
     */
    public static final List<Integer> NEW_DYNAMIC_GUANGDONG_COLUMNS = Collections.singletonList(17078);
    public static final String NEW_DYNAMIC_COLUMNS_GUANGDONG_ARTICLES = "new_dynamic_columns_guangdong_articles";

}

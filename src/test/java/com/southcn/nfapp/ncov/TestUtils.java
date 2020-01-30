package com.southcn.nfapp.ncov;

import com.southcn.nfapp.ncov.utils.NfplusUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

public class TestUtils {
    @Test
    public void testSpider() throws Exception {
        Date date = NfplusUtils.parseXlsHeaderDate("截止01月30日24时数据统计");
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
    }
}

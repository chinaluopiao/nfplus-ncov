package com.southcn.nfapp.ncov.utils;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Builder
public class OkHttpUtils {

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    public String get(String url) {
        Request request = new Request.Builder().url(url)
                .removeHeader(HttpHeaders.USER_AGENT)
                .addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Mobile Safari/537.36")
                .removeHeader(HttpHeaders.REFERER)
                .addHeader(HttpHeaders.REFERER,"https://activity.peopleapp.com/broadcast/")
                .removeHeader(HttpHeaders.ORIGIN)
                //.addHeader(HttpHeaders.ORIGIN,"https://activity.peopleapp.com")
                .build();
        StringBuilder sb = new StringBuilder();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Optional.ofNullable(response.body()).ifPresent(value -> {
                    try {
                        String string = value.string();
                        log.info("{} 抓取数据大小:{}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), string.length());
                        if (StringUtils.isNotBlank(string)) {
                            sb.append(string);
                        }
                    } catch (IOException e) {
                        log.error("数据抓取出错", e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("url:{},据抓取出错", url, e);
        }
        log.info("{}:url:{}, 抓取数据大小:{}", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), url, sb.length());
        return sb.toString();
    }

}

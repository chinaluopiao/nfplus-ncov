package com.southcn.nfapp.ncov.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class PermissionsInterceptors extends HandlerInterceptorAdapter {

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域处理
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");

        String requestId = UUID.randomUUID().toString();
        log.info("*********************请求id:{},开始 ：{}**************************************", requestId, handler.getClass());
        log.info("请求id:{},请求方式:{}", requestId, request.getMethod());
        log.info("请求id:{},请求来源:{}", requestId, request.getHeader(HttpHeaders.REFERER));
        log.info("请求id:{},浏览器信息:{}", requestId, request.getHeader(HttpHeaders.USER_AGENT));
        log.info("请求id:{},请求链接:{}", requestId, urlPathHelper.getLookupPathForRequest(request));
        log.info("*********************请求id:{},请求开始 ：{}**************************************", requestId, handler.getClass());
        return super.preHandle(request, response, handler);
    }
}

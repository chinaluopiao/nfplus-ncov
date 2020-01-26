package com.southcn.nfapp.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.southcn.nfapp.ncov.bean.Pneumonia;
import com.southcn.nfapp.ncov.bean.PneumoniaStats;
import com.southcn.nfapp.ncov.bean.ProvinceStat;
import com.southcn.nfapp.ncov.constant.NcovConst;
import com.southcn.nfapp.ncov.service.PneumoniaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class PneumoniaServiceImpl implements PneumoniaService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean spider() {
        try {
            String url = "https://3g.dxy.cn/newh5/view/pneumonia";
            WebClient wc = new WebClient(BrowserVersion.CHROME);
            //是否使用不安全的SSL
            wc.getOptions().setUseInsecureSSL(true);
            //启用JS解释器，默认为true
            wc.getOptions().setJavaScriptEnabled(true);
            //禁用CSS
            wc.getOptions().setCssEnabled(false);
            //js运行错误时，是否抛出异常
            wc.getOptions().setThrowExceptionOnScriptError(false);
            //状态码错误时，是否抛出异常
            wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
            //是否允许使用ActiveX
            wc.getOptions().setActiveXNative(false);
            //等待js时间
            wc.waitForBackgroundJavaScript(600 * 1000);
            //设置Ajax异步处理控制器即启用Ajax支持
            wc.setAjaxController(new NicelyResynchronizingAjaxController());
            //设置超时时间
            wc.getOptions().setTimeout(1000000);
            //不跟踪抓取
            wc.getOptions().setDoNotTrackEnabled(false);
            WebRequest request = new WebRequest(new URL(url));
            request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
            String uuid = UUID.randomUUID().toString();
            request.setAdditionalHeader("Cookie", "PLAY_LANG=cn; _plh=" + uuid + "; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157");
            HtmlPage page = wc.getPage(request);
            wc.waitForBackgroundJavaScript(10000);
            ScriptResult scriptResult = page.executeJavaScript("JSON.stringify(window.getAreaStat)");
            Pneumonia pneumonia = JSON.parseObject(JSON.toJSONString(scriptResult), Pneumonia.class);
            List<ProvinceStat> provinceStats = JSON.parseObject(pneumonia.getJavaScriptResult(), new TypeReference<List<ProvinceStat>>() {
            });

            AtomicInteger confirmedCount = new AtomicInteger();
            AtomicInteger suspectedCount = new AtomicInteger();
            AtomicInteger curedCount = new AtomicInteger();
            AtomicInteger deadCount = new AtomicInteger();
            provinceStats.parallelStream().forEach(provinceStat -> {
                confirmedCount.addAndGet(provinceStat.getConfirmedCount());
                suspectedCount.addAndGet(provinceStat.getSuspectedCount());
                curedCount.addAndGet(provinceStat.getCuredCount());
                deadCount.addAndGet(provinceStat.getDeadCount());
            });

            PneumoniaStats pneumoniaStats = PneumoniaStats.builder().confirmedCount(confirmedCount.get())
                    .suspectedCount(suspectedCount.get()).curedCount(curedCount.get()).statsTime(new Date())
                    .deadCount(deadCount.get()).provinceStats(provinceStats).build();
            this.stringRedisTemplate.opsForValue().set(NcovConst.DXY_NCOV_DATA, JSON.toJSONString(pneumoniaStats));
        } catch (Exception e) {
            log.error("丁香医生数据抓取失败", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

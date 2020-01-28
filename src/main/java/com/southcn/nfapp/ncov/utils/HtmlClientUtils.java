package com.southcn.nfapp.ncov.utils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;

@Slf4j
@Builder
public class HtmlClientUtils {

    private HtmlClientUtils() {

    }

    public Map<String, ScriptResult> executeJavaScript(String url, String... commands) {
        try (WebClient wc = new WebClient(BrowserVersion.CHROME)) {
            //WebClientUtils.attachVisualDebugger(wc);
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
            request.setAdditionalHeader(HttpHeader.USER_AGENT, "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Mobile Safari/537.36");
            String uuid = UUID.randomUUID().toString();
            request.setAdditionalHeader(HttpHeader.COOKIE, "PLAY_LANG=cn; _plh=" + uuid + "; PLATFORM_SESSION=39034d07000717c664134556ad39869771aabc04-_ldi=520275&_lsh=8cf91cdbcbbb255adff5cba6061f561b642f5157&csrfToken=209f20c8473bc0518413c226f898ff79cd69c3ff-1539926671235-b853a6a63c77dd8fcc364a58&_lpt=%2Fcn%2Fvehicle_sales%2Fsearch&_lsi=1646321; _ga=GA1.2.2146952143.1539926675; _gid=GA1.2.1032787565.1539926675; _plh_notime=8cf91cdbcbbb255adff5cba6061f561b642f5157");
            request.setAdditionalHeader(HttpHeader.UPGRADE_INSECURE_REQUESTS, "1");
            request.setAdditionalHeader(HttpHeader.ACCEPT_LANGUAGE, "zh,en;q=0.9,en-US;q=0.8,zh-CN;q=0.7");
            HtmlPage page = wc.getPage(request);
            Map<String, ScriptResult> resultMap = new HashMap<>();
            if (Objects.nonNull(commands)) {
                Arrays.asList(commands).forEach(command -> {
                    ScriptResult scriptResult = page.executeJavaScript(command);
                    resultMap.put(command, scriptResult);
                    log.info("url:{},执行:{},结果大小:{}", url, command, scriptResult.toString().length());
                });
            }
            return resultMap;
        } catch (Exception e) {
            log.error("网页:{},执行:{} 异常", url, commands, e);
            return null;
        }

    }

    /**
     * 执行单单个java script
     *
     * @param url
     * @param command
     * @return
     */
    public ScriptResult executeJavaScript(String url, String command) {
        return executeJavaScript(url, new String[]{command}).get(command);
    }

}

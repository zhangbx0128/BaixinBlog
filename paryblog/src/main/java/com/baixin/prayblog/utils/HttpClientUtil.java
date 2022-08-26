package com.baixin.prayblog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @ClassName: HttpClientUtil
 * @description get请求HttpClient工具类
 * @Author: Kirayor
 */
@Slf4j
public class HttpClientUtil {

    /**
     * get请求
     * @return
     */
    public static String doGet(String requestUrl) {

        CloseableHttpClient client = null;
        try {
            // 获取当前客户端
            client = HttpClients.createDefault();
            //封装get请求对象
            HttpGet httpGetRequest = new HttpGet(requestUrl);
            // 连接主机服务超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                    // 请求超时时间
                    .setConnectionRequestTimeout(35000)
                    // 数据读取超时时间
                    .setSocketTimeout(60000)
                    .build();
            httpGetRequest.setConfig(requestConfig);
            HttpResponse response = client.execute(httpGetRequest);
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        } catch (IOException e) {
            log.error("Http request Error", e);
        } finally {
            client.getConnectionManager().shutdown();
            try {
                client.close();
            } catch (IOException e) {
                log.error("Http request Error", e);
            }
        }

        return null;
    }
}

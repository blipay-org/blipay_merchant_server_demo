package com.blipay.merchant.demo.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Http method caller
 */
public class SOLEasyHttp {

    static Logger logger = LoggerFactory.getLogger(SOLEasyHttp.class);

    private final static PoolingHttpClientConnectionManager connMgr;

    private final static RequestConfig requestConfig;

    private final static int MAX_TIMEOUT = 7000;

    static {

        connMgr = new PoolingHttpClientConnectionManager();

        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        connMgr.setValidateAfterInactivity(1000);
        RequestConfig.Builder configBuilder = RequestConfig.custom();

        configBuilder.setConnectTimeout(MAX_TIMEOUT);

        configBuilder.setSocketTimeout(MAX_TIMEOUT);

        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    /**
     * post with params formatted by json
     *
     * @param url
     * @param json
     * @return
     */
    public static String postWithJson(String url, Object json) {
        logger.info("doPostJson ## , url->{},json->{}", url, json);
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {

            HttpPost httpPost = new HttpPost(url);

            StringEntity stringEntity = new StringEntity(json.toString(), StandardCharsets.UTF_8);

            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            logger.info("resultString ->{}", resultString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }
}

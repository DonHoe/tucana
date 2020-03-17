package com.hepic.tucana.util;

import com.hepic.tucana.util.model.HttpClientResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

public class HttpClientUtil {

    // 编码格式 UTF-8
    private static final String ENCODING = "UTF-8";

    // 连接超时时间,毫秒。
    private static final int CONNECT_TIMEOUT = 20 * 1000;

    // 响应超时时间,毫秒。
    private static final int SOCKET_TIMEOUT = 20 * 1000;

    /**
     * get
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult get(String url) throws Exception {
        return get(url, null, null);
    }

    /**
     * get
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static HttpClientResult get(String url, Map<String, String> params) throws Exception {
        return get(url, params, null);
    }

    /**
     * post
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult post(String url) throws Exception {
        return post(url, null, null);
    }

    /**
     * post
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static HttpClientResult post(String url, HttpEntity entity) throws Exception {
        return post(url, entity, null);
    }

    /**
     * get
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return
     * @throws Exception
     */
    public static HttpClientResult get(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //接入参数
        if (params != null) {
            params.entrySet().forEach(p -> uriBuilder.setParameter(p.getKey(), p.getValue()));
        }

        // 设置请求头
        if (headers != null) {
            headers.entrySet().forEach(p -> httpGet.setHeader(p.getKey(), p.getValue()));
        }

        //超时时间设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        return getHttpClientResult(httpClient, httpResponse);
    }

    /**
     * post
     *
     * @param url     请求地址
     * @param params  请求参数集合
     * @param headers 请求头集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult post(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        // 设置请求头
        if (headers != null) {
            headers.entrySet().forEach(p -> httpPost.setHeader(p.getKey(), p.getValue()));
        }

        //设置参数
        if (entity != null) {
            // 设置到请求的http对象中
            httpPost.setEntity(entity);
        }
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            return getHttpClientResult(httpClient, httpResponse);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * 处理返回值
     *
     * @param httpClient
     * @param httpResponse
     * @return
     * @throws Exception
     */
    private static HttpClientResult getHttpClientResult(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) throws Exception {
        try {
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                String content = "";

                if (httpResponse.getEntity() != null) {

                    content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                }
                return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return null;
    }
}

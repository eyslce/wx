package com.eyslce.wx.commons.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.List;

public class HttpClient {

    /**
     * 构造一个post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, List<NameValuePair> params) throws Exception {
        // 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httppost实例
        HttpPost httpPost = new HttpPost(url);
        try {
            // 将参数设置到entity对象中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            // 将entity对象设置到httppost对象中
            httpPost.setEntity(entity);
            // 发送请求并获取响应
            CloseableHttpResponse resp = client.execute(httpPost);
            return getStringReponse(resp);

        }  finally {
            // 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  raw类型参数
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public  static String post(String url, String params) throws Exception{
        // 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();
        //创建httppost实例
        HttpPost httpPost = new HttpPost(url);
        try {
            // 将参数设置到entity对象中
            StringEntity entity = new StringEntity(params, "UTF-8");
            // 将entity对象设置到httppost对象中
            httpPost.setEntity(entity);
            // 发送请求并获取响应
            CloseableHttpResponse resp = client.execute(httpPost);
            return getStringReponse(resp);

        }  finally {
            // 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  获取响应内容
     * @param resp
     * @return
     * @throws Exception
     */
    private static String getStringReponse(CloseableHttpResponse resp) throws Exception{
        try {
            // 获取响应entity
            HttpEntity respEntity = resp.getEntity();
            if(null == respEntity){
                throw new Exception("服务端没有数据返回");
            }
            return EntityUtils.toString(respEntity,"UTF-8");

        } finally {
            // 关闭响应对象
            resp.close();
        }
    }
}

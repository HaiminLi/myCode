//package com.social.commerce.member.common;
//
///**
// * @Author Haimin Li
// * @Description
// * @Date 2019/6/26 11:15
// * @Param
// * @return
// **/
//
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingClientConnectionManager;
//import org.apache.http.message.BasicNameValuePair;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.KeyManager;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.Socket;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.UnknownHostException;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//
//public class HttpUtil {
//
//  private static String ENCODE = "UTF-8";
//
//
//  /**
//   * 封装HTTPS/HTTP POST方法
//   */
//  public static String httpPost(String url, Map<String, String> paramMap) throws Exception {
//    boolean isSSL = url.startsWith("https");
//    HttpClient httpClient = getHttpClient(isSSL);
//    HttpPost httpPost = new HttpPost(url);
//    httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//    List<NameValuePair> formparams = setHttpParams(paramMap);
//    UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, ENCODE);
//    httpPost.setEntity(param);
//    HttpResponse response = httpClient.execute(httpPost);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpPost.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * getHttpClient
//   * @param isSSL
//   * @return
//   */
//  public static DefaultHttpClient getHttpClient(boolean isSSL) throws Exception {
//    DefaultHttpClient httpClient = new DefaultHttpClient();
//    if (!isSSL) {
//      return httpClient;
//    } else {
//      SchemeRegistry registry = new SchemeRegistry();
//      registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
//
//      try {
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sslContext.init((KeyManager[]) null, new TrustManager[]{new TrustAllX509TrustManager() {
//        }}, (SecureRandom) null);
//        SSLSocketFactory sf = new TrustAllSSLSocketFactory(sslContext);
//        registry.register(new Scheme("https", 443, sf));
//      } catch (Exception var5) {
//        var5.printStackTrace();
//      }
//
//      PoolingClientConnectionManager connManager = new PoolingClientConnectionManager(registry);
//      httpClient = new DefaultHttpClient(connManager);
//      return httpClient;
//    }
//  }
//
//  /**
//   * TrustAllX509TrustManager
//   */
//  private static class TrustAllX509TrustManager implements X509TrustManager {
//
//    public void checkClientTrusted(X509Certificate[] chain, String authType)
//        throws CertificateException {
//    }
//
//    public void checkServerTrusted(X509Certificate[] chain, String authType)
//        throws CertificateException {
//    }
//
//    public X509Certificate[] getAcceptedIssuers() {
//      return new X509Certificate[]{};
//    }
//  }
//
//  private static class TrustAnyHostnameVerifier implements HostnameVerifier {
//    public boolean verify(String hostname, SSLSession session) {
//      return true;
//    }
//  }
//
//  /**
//   * TrustAllSSLSocketFactory
//   */
//  private static class TrustAllSSLSocketFactory extends SSLSocketFactory {
//
//    SSLContext sslContext;
//
//    public TrustAllSSLSocketFactory(KeyStore truststore)
//        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//      super(truststore);
//      if (this.sslContext == null) {
//        this.sslContext = SSLContext.getInstance("TLS");
//      }
//
//      TrustManager tm = new TrustAllX509TrustManager() {
//      };
//      this.sslContext.init((KeyManager[]) null, new TrustManager[]{tm}, (SecureRandom) null);
//    }
//
//    public TrustAllSSLSocketFactory(SSLContext sslContext2) {
//      super(sslContext2, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//      this.sslContext = sslContext2;
//    }
//
//    public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
//        throws IOException, UnknownHostException {
//      return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
//    }
//
//    public Socket createSocket() throws IOException {
//      return this.sslContext.getSocketFactory().createSocket();
//    }
//  }
//
//  /**
//   * 封装HTTP POST方法
//   * @param url
//   * @param jsonParam
//   * @return
//   * @throws ClientProtocolException
//   * @throws IOException
//   */
//  public static String postJson(String url, JSONObject jsonParam) throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpPost httpPost = new HttpPost(url);
//    StringEntity entity = new StringEntity(jsonParam.toString(),ENCODE);//解决中文乱码问题
//    entity.setContentEncoding(ENCODE);
//    entity.setContentType("application/json");
//    httpPost.setEntity(entity);
//    HttpResponse result = httpClient.execute(httpPost);
//    String httpEntityContent = getHttpEntityContent(result);
//    httpPost.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP POST方法
//   */
//  public static String post(String url, Map<String, String> paramMap)
//      throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpPost httpPost = new HttpPost(url);
//    httpPost.setHeader("Content-Type","text/json; charset=UTF-8");
//    List<NameValuePair> formparams = setHttpParams(paramMap);
//    UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, ENCODE);
//    httpPost.setEntity(param);
//    HttpResponse response = httpClient.execute(httpPost);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpPost.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP POST方法
//   */
//  public static String post(String url, String data) throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpPost httpPost = new HttpPost(url);
//    httpPost.setHeader("Content-Type", "text/json; charset=GBK");
//    httpPost.setEntity(new StringEntity(URLEncoder.encode(data, ENCODE)));
//    HttpResponse response = httpClient.execute(httpPost);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpPost.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP GET方法
//   */
//  public static String get(String url) throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpGet httpGet = new HttpGet();
//    httpGet.setURI(URI.create(url));
//    HttpResponse response = httpClient.execute(httpGet);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpGet.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP GET方法
//   */
//  public static String get(String url, Map<String, String> paramMap)
//      throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpGet httpGet = new HttpGet();
//    List<NameValuePair> formparams = setHttpParams(paramMap);
//    String param = URLEncodedUtils.format(formparams, ENCODE);
//    httpGet.setURI(URI.create(url + "?" + param));
//    HttpResponse response = httpClient.execute(httpGet);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpGet.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP PUT方法
//   */
//  public static String put(String url, Map<String, String> paramMap)
//      throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpPut httpPut = new HttpPut(url);
//    List<NameValuePair> formparams = setHttpParams(paramMap);
//    UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, ENCODE);
//    httpPut.setEntity(param);
//    HttpResponse response = httpClient.execute(httpPut);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpPut.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP DELETE方法
//   */
//  public static String delete(String url) throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpDelete httpDelete = new HttpDelete();
//    httpDelete.setURI(URI.create(url));
//    HttpResponse response = httpClient.execute(httpDelete);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpDelete.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 封装HTTP DELETE方法
//   */
//  public static String delete(String url, Map<String, String> paramMap)
//      throws ClientProtocolException, IOException {
//    HttpClient httpClient = HttpClients.createDefault();
//    HttpDelete httpDelete = new HttpDelete();
//    List<NameValuePair> formparams = setHttpParams(paramMap);
//    String param = URLEncodedUtils.format(formparams, ENCODE);
//    httpDelete.setURI(URI.create(url + "?" + param));
//    HttpResponse response = httpClient.execute(httpDelete);
//    String httpEntityContent = getHttpEntityContent(response);
//    httpDelete.abort();
//    return httpEntityContent;
//  }
//
//  /**
//   * 设置请求参数
//   */
//  private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
//    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//    Set<Map.Entry<String, String>> set = paramMap.entrySet();
//    for (Map.Entry<String, String> entry : set) {
//      formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//    }
//    return formparams;
//  }
//
//  /**
//   * 获得响应HTTP实体内容
//   */
//  private static String getHttpEntityContent(HttpResponse response)
//      throws IOException, UnsupportedEncodingException {
//    HttpEntity entity = response.getEntity();
//    if (entity != null) {
//      InputStream is = entity.getContent();
//      BufferedReader br = new BufferedReader(new InputStreamReader(is, ENCODE));
//      String line = br.readLine();
//      StringBuilder sb = new StringBuilder();
//      while (line != null) {
//        sb.append(line + "\n");
//        line = br.readLine();
//      }
//      return sb.toString();
//    }
//    return "";
//  }
//
//
//}

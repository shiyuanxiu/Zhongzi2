package utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private static OkHttpClient okHttpClient;

    private static OkHttpClient sslOkHttpClient;

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static String certPath;

    private static int connectTimeout;

    private static int readTimeout;

    private static final String SCHEME_HTTPS = "https";

    @Value("${http.certPath:cert/ontest-root.cer}")
    public void setCertPath(String certPath) {
        OkHttpUtil.certPath = certPath;
    }

    @Value("${http.connectTimeout:300}")
    public void setConnectTimeout(int connectTimeout) {
        OkHttpUtil.connectTimeout = connectTimeout;
    }

    @Value("${http.readTimeout:300}")
    public void setReadTimeout(int readTimeout) {
        OkHttpUtil.readTimeout = readTimeout;
    }

    public void initHttps() {
        InputStream in = null;
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            in = OkHttpUtil.class.getClassLoader().getResourceAsStream(certPath);
            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
            if (certificates.isEmpty()) {
                throw new IllegalStateException("没有找到证书");
            }
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            int index = 0;
            for (Certificate certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificate);
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("unexepected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            if (trustManager == null) {
                throw new IllegalStateException("导入证书失败");
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                    null,
                    new TrustManager[] { trustManager },
                    null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            sslOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build();
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("https设置失败", e);
            throw new IllegalStateException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭证书IO流失败", e);
                }
            }
        }
    }

    public void initHttp() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }

   @PostConstruct
   public void init() {
        initHttp();
        initHttps();
   }

    /**
     * 同步GET请求
     * @param url kms url
     * @return response body
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        logger.debug("发送get请求, url:{}", url);
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String res =  response.body().string();
            logger.debug("get返回结果, response:{}", res);
            return res;
        } else {
            throw new IOException("Unexpected code" + response);
        }
    }

    /**
     * 同步POST请求
     * @param url kms url
     * @param json post json
     * @return response body
     */
    public static String post(String url, String json) throws IOException {
        logger.debug("发送post请求, url:{}, json:{}", url, json);
        RequestBody body = RequestBody.create(JSON_TYPE, json);
        Request request = new Request.Builder().url(url)
                .addHeader("X-CHJ-Timestamp", String.valueOf(System.currentTimeMillis()))
                .addHeader("X-CHJ-Nonce", UUID.randomUUID().toString().replace("-", ""))
                .post(body).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String res = response.body().string();
            logger.debug("post返回结果, response:{}", res);
            return res;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private static Response execute(Request request) throws IOException {
        return okHttpClient(request).newCall(request).execute();
    }

    private static OkHttpClient okHttpClient(Request request) {
        if (SCHEME_HTTPS.equals(request.url().scheme())) {
            return sslOkHttpClient;
        } else {
            return okHttpClient;
        }
    }

}

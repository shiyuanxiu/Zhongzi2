package serviceImpl;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import utils.JsonFormatUtil;

import java.io.IOException;

/**
 * Created by LT on 2018/7/9.
 */
public abstract class ServiceImplBase {

    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplBase.class);

    public String env ;

    public Retrofit retrofit ;

    public OkHttpClient httpClient;

    public ServiceImplBase(String env){
        initClient();
        setEvn(env);
        initEnv();
        initService();
    }

    public void apiGatewayHeader(){
        //TODO  apiGateway 防重放，验签
    }

    public void initClient() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "JSESSIONID=xuX2E8sYHLXUwDAZkxFUvxxaNzLQ23W-hQP2htnP")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();
    }

    public void setEvn(String env){
        this.env = env;

    }

    public void initEnv(){
        this.retrofit= new Retrofit.Builder().baseUrl(this.env ).client(httpClient).build();
    }

    public abstract void initService();

    /**
     * string 转 RequestBody
     * @param body
     * @return
     */
    public static RequestBody strToReqBody(String body){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        return requestBody;
    }

    public int getResponseCode(Response<ResponseBody> response){
        int code = -1;
        if (response!=null){
            code = response.code();
        }
        LOGGER.info("code：\n" + code);
        return code;
    }

    public Headers getResponseHeaders(Response<ResponseBody> response){
        Headers headers = null;
        if (response!=null){
            headers = response.headers();
        }
        LOGGER.info("header：\n" + headers);
        return headers;
    }

    public String getResponseBody(Response<ResponseBody> response){
        String body = "";
        if (response!=null&&response.isSuccessful()){
            try {
                body = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("response 获取 body IO异常");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("response 获取 body 异常");
            }
        }
        LOGGER.info("body：\n" + JsonFormatUtil.formatJson(body));

        return body;
    }

    public void doFailure(){
        LOGGER.error("请求失败");
    }

}

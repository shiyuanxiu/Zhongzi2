package service;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ZhongziService {
    /**
     * 推荐种子用户
     */
//    @POST("ssp-vds/v1-0/log-config")
//    Call<ResponseBody> getLogConfig(@Body RequestBody body);
    @POST("seed-customer/recommend")
    Call<ResponseBody> getLogConfig(@Body RequestBody body);
    /**
     * 已推荐历史查询
     */
//    @GET("ssp-vds/v1-0/location/vehicle/{vin}/track/{startTime}/{endTime}")
//    Call<ResponseBody> getTrack(@Path("vin") String vin, @Path("startTime") String startTime, @Path("endTime") String endTime);
    @GET("seed-customer/recommend/list")
    Call<ResponseBody> getTrack();

}

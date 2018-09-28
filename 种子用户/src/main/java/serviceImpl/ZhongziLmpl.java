package serviceImpl;

import configs.Constants;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import param.DefaultParam;
import retrofit2.Response;
import service.ZhongziService;

import java.io.IOException;

/**
 * Created by LT on 2018/8/17.
 */
public class ZhongziLmpl extends ServiceImplBase {

    public static final Logger LOGGER = LoggerFactory.getLogger(ZhongziLmpl.class);

    public service.ZhongziService zhongziService;

    public ZhongziLmpl(String env) {
        super(env);
    }

    @Override
    public void initService() {
        zhongziService = retrofit.create(ZhongziService.class);
    }

    public static void main(String[] args) throws IOException {
        ZhongziLmpl zhongzi = new ZhongziLmpl(Constants.ONTEST_RTS);
        Response<ResponseBody> response;
        response = zhongzi.zhongziService.getLogConfig(strToReqBody(DefaultParam.collectionConfig)).execute();  //推荐种子用户
//        response = zhongzi.zhongziService.getTrack().execute();  //已推荐历史查询
        zhongzi.getResponseCode(response);
        zhongzi.getResponseBody(response);
    }
}

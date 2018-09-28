package testcase;
import configs.Constants;
import listeners.AutoTestListener;
import listeners.RetryListener;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import param.DefaultParam;
import retrofit2.Response;
import serviceImpl.ZhongziLmpl;

import java.io.IOException;

import static param.ParamFactory.changeParam;

@Listeners({ AutoTestListener.class, RetryListener.class })
public class ZhongziCase extends TestCaseBase {

    public static final Logger LOGGER = LoggerFactory.getLogger(ZhongziCase.class);

    public ZhongziLmpl zhongziLmpl;;

    public void initServiceImpl(){
        zhongziLmpl= new ZhongziLmpl(Constants.ONTEST_RTS);
    }

    @DataProvider(name = "CollectionConfigBody2")
    public Object[][] createCollectionConfigBody2() {
        return new Object[][]{
                {"test", DefaultParam.collectionConfig}
        };

       }
    @Test(dataProvider="CollectionConfigBody2")
    public void testGetCollectionConfig(String caseTitle, String body) throws IOException {
        Response<ResponseBody> response = zhongziLmpl.zhongziService.getLogConfig(ZhongziLmpl.strToReqBody(body)).execute();
        LOGGER.info(caseTitle);
        LOGGER.info("请求：{}", body);
        Assert.assertEquals(response.code(),200);
        zhongziLmpl.getResponseBody(response);
    }



    }


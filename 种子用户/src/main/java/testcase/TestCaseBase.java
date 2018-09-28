package testcase;

import listeners.ExtentTestNGIReporterListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import param.DefaultParam;

import static param.ParamFactory.changeJsonArrayParam;
import static param.ParamFactory.changeParam;

/**
 * Created by LT on 2018/7/13.
 */
public abstract class TestCaseBase {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestCaseBase.class);

    public abstract void initServiceImpl();

    @BeforeClass
    public void beforeClass(){
        LOGGER.info("====={} begin=====", this.getClass().getName());
        ExtentTestNGIReporterListener.setFileName(this.getClass().getName()+" TestReport.html");
        initServiceImpl();
    }

    @AfterClass
    public void afterClass() {
        LOGGER.info("====={} TestCase finished=====", this.getClass().getName());
    }

    public static void main(String[] args) {
//        String bodyStr = changeParam(DefaultParam.collectionConfig, new String[]{"vin", "123"});
//        System.out.println(bodyStr);
//        bodyStr = changeJsonArrayParam(DefaultParam.collectionConfig, "strategys", 0, new Object[]{"version", 7});
//        System.out.println(bodyStr);

    }

}

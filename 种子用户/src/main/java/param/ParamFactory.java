package param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.RandomUtil;

/**
 * Created by LT on 2018/7/12.
 */
public class ParamFactory {

    private ParamFactory(){}

    /**
     *  获取m到n的随机数（包括m和n）
     * @param m
     * @param n
     * @return
     */
    public static String getRandomNumberParam(int m, int n){
        int param = RandomUtil.getRandomNumber(m, n);
        return Integer.toString(param);
    }

    /**
     * 获取一个带前缀的m到n的顺序二维数组（包括m和n），可用于DataProvider
     * @param prefix 前缀
     * @param m
     * @param n
     * @return
     */
    public static Object[][] getCountNumberParam(String prefix, int m, int n){
        if (m>n){
            int temp = m;
            m = n;
            n = temp;
        }
        Object[][] obj = new Object[n-m+1][1];
        for (int i = 0; i < n-m+1; i++) {
            String param = prefix;
            param += m+i;
            obj[i][0] = param;
        }
        return obj;
    }

    /**
     *  更改一个参数
     * @param jsonbody
     * @param key
     * @param value
     * @return
     */
    public static String changeParam(String jsonbody, String key, Object value){
        JSONObject JsonObject = JSONObject.parseObject(jsonbody);
        JsonObject.put(key, value);
        return JsonObject.toJSONString();
    }

    /**
     *  更改多个参数
     * @param jsonbody
     * @param kv
     * @return
     */
    public static String changeParam(String jsonbody, Object[] kv){
        JSONObject JsonObject = JSONObject.parseObject(jsonbody);
        for (int i = 0; i < kv.length; i++) {
            if (i % 2 !=0)continue;
            JsonObject.put((String) kv[i], kv[i+1]);
        }
        return JsonObject.toJSONString();
    }

    /**
     *  更换json中数组中的参数
     * @param jsonbody
     * @param arrayKey
     * @param index
     * @param kv
     * @return
     */
    public static String changeJsonArrayParam(String jsonbody, String arrayKey, int index, Object[] kv){
        JSONObject jsonObject = JSONObject.parseObject(jsonbody);
        JSONArray jsonArray= jsonObject.getJSONArray(arrayKey);
        JSONObject jsonArrayObject =jsonArray.getJSONObject(index);
        String changeStr = changeParam(jsonArrayObject.toJSONString(), kv);
        jsonArrayObject = JSONObject.parseObject(changeStr);
        jsonArray.set(index, jsonArrayObject);
        jsonObject.put(arrayKey, jsonArray);
        return jsonObject.toJSONString();
    }

    public static void main(String[] args) {
//        Object[][] objtest = getCountNumberParam("CHJ", 100, 200);
//        for (int i = 0; i < objtest.length; i++) {
//            System.out.println(objtest[i][0]);
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(getRandomNumberParam(1, 10));
//        }
//        JSONObject result = new JSONObject();
//        result.put("chj001", "hello");
//        result.put("chj002", "jesse");
//        result.put("chj001", "jesse");
//        System.out.println(result.toJSONString());
//        result.replace("chj001", "bye");
//        result.replace("chj002", "jese", "lt");
//        System.out.println(result.toJSONString());
//        result.replace("chj002", "jesse", "lt");
//        System.out.println(result.toJSONString());
        JSONObject jsonObject = JSONObject.parseObject(DefaultParam.collectionConfig);
        System.out.println(jsonObject);
        jsonObject.put("vin", DefaultParam.vin2);
        System.out.println(jsonObject);
        JSONArray jsonArray= jsonObject.getJSONArray("strategys");
        JSONObject jsonArrayObject =jsonArray.getJSONObject(0);
        System.out.println(jsonArrayObject);
        jsonArrayObject.put("version", 9);
        System.out.println(jsonArrayObject);
    }

}

package param;

/**
 * Created by LT on 2018/7/12.
 */
public class DefaultParam {
    /**
     * 默认vin
     */
    public static final String vin ="CHJ00000000000001";
    public static final String vin1 ="CHJ00000000001001";
    public static final String vin2 ="CHJ00000000001999";
    public static final String vin3 ="CHJ00000000001000";
    public static final String vinNoSn ="CHJ00000000002000";
    public static final String vinNoThing ="CHJ99000000001000";

    /**
     * OTA升级版本
     */
    public static final String currentVersion = "1.0.0";
    public static final String targetVersion = "1.0.1";

    /**
     * 同步车辆维保记录
     */
    public static final String syncMaintRecord = "{\n" +
            "  \"maintItemRequestList\": [\n" +
            "    {\n" +
            "      \"isMaint\": 1,\n" +
            "      \"itemName\": \"换刹车片\",\n" +
            "      \"itemNo\": \"003344\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"maintNo\": \"101144\",\n" +
            "  \"maintTime\": \"2018-05-29T16:16:01.556Z\",\n" +
            "  \"vin\": \"CHJ00000000001399\"\n" +
            "}";

    /**
     * 更新车辆状态
     */
    public static final String updateStatus = "{\n" +
            "  \"status\": 7,\n" +
            "  \"vin\": \"CHJ00000000001399\"\n" +
            "}";

    /**
     *  获取采集策略配置（车机端使用）——获取指定模板
     */
//    public static  final String collectionConfig = "{\n" +
//            "  \"configTemplateId\": 1,\n" +
//            "  \"strategys\": [\n" +
//            "    {\n" +
//            "      \"majorType\": 1,\n" +
//            "      \"groupId\": 1532399830661,\n" +
//            "      \"version\": 2\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"vin\": \"CHJ00000000001000\"\n" +
//            "}";
    public static  final String collectionConfig ="{\n" +
            "\t\"type\": \"内部员工购车\",\n" +
            "\t\"name\": \"送检\",\n" +
            "\t\"wxNo\": \"1231·32\",\n" +
            "\t\"phoneNo\": \"18810894066\",\n" +
            "\t\"city\": \"北京\",\n" +
            "\t\"sex\": \"男\",\n" +
            "\t\"ageRange\": \"51-55\",\n" +
            "\t\"childs\": 1,\n" +
            "\t\"cars\": 1,\n" +
            "\t\"carsInfo\": \"1\"\n" +
            "}";

    /**
     *  获取采集策略配置（车机端使用）——注册时获取默认模板
     */
    public static  final String collectionConfigFirst = "{\n" +
            "  \"vin\": \"CHJ00000000001000\"\n" +
            "}";

}

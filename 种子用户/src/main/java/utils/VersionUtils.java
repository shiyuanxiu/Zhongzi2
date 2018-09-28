package utils;

/**
 * @Author ZHJ
 * @Date 2018/7/4
 */
public class VersionUtils {

    private static final String versionPatten = "^((?:[1-9]\\d?|0)(\\.(?:[1-9]\\d?|0)){2})$";

    public static Long valueOf(String version) {
        if (null == version || !version.matches(versionPatten)) {
            throw new IllegalArgumentException();
        }
        String[] valStrs = version.split("\\.");
        long value = 0;
        value += Long.parseLong(valStrs[0]) << 24;
        value += Long.parseLong(valStrs[1]) << 12;
        value += Long.parseLong(valStrs[2]);
        return value;
    }

    public static String format(Long version) {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append((version & 0xfff000000L) >> 24);
        stringBuilder.append('.');
        stringBuilder.append((version & 0xfff000L) >> 12);
        stringBuilder.append('.');
        stringBuilder.append(version & 0xfffL);

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Long versionLong = 16777218l;
        String versionStr = "1.1.2";
        System.out.println(format(versionLong));
        System.out.println(valueOf(versionStr));
    }

}

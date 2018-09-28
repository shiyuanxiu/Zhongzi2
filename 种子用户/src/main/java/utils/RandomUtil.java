package utils;

import java.util.Random;

public class RandomUtil {

	private RandomUtil(){}

	/**
	 * 得到一个m到n之间的int值，包括m和n
	 * @param m
	 * @param n
	 * @return
	 */
	public static int getRandomNumber(int m, int n){
		if (m>n){
			int temp = m;
			m = n;
			n = temp;
		}
		Random ran = new Random();
		int num = ran.nextInt((n-m)+1)+m;
		return num;
	}

	public static String randomBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//字母和数字
	public static String randomNumberBase = "0123456789";
	
	//Unicode 基本汉字编码范围0x4e00~0x9fa5 共 20902个
    private final static int HANZI_LENGTH = 20902 ; //汉字生成码
    
    public static Random random = new Random();  
    
	/**
	 * 随机生成一个汉字
	 * @return
	 */
	public static char getRandomHanZi() {
		Random ran = new Random();
		return (char) (0x4e00 + ran.nextInt(HANZI_LENGTH));
	}
	
	/**
	 * 随机生成一个字母或者数字
	 * @return
	 */
	public static char getRandomStr() {
		Random ran = new Random();
		return (char) randomBase.charAt(ran.nextInt(62));
	}
	
	
	
	/**
	 * 随机生成一个带有中文的字符串
	 * @param length
	 * @return
	 */
	public static String getRandomText(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int randomNum = random.nextInt(2);
			char ch = 0 ;
			if (randomNum==0) {//生成汉字
				ch = getRandomHanZi();
			}else if (randomNum==1) {//生成数字或者字符
				ch = getRandomStr();
			}
			sb.append(String.valueOf(ch));
		}
		return sb.toString();
	}

	/**
	 * 随机生成字母数字或纯数字
	 * @param length
	 * @param onlyNumber
	 * @return
	 */
	public static String getRandom(int length, boolean onlyNumber) {
		String base;
		if (onlyNumber) {
			base = randomNumberBase;
		} else {
			base = randomBase;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char chr;
			do {
				int number = random.nextInt(base.length());
				chr = base.charAt(number);
			} while (i==0&&chr=='0') ;//第一个字符不能为0,
			
			sb.append(chr);
		}
		return sb.toString();
	}

	/**
	 *  随机生成字符串组，用","隔开
	 * @param arrLength
	 * @param length
	 * @param flag
	 * @return
	 */
	public static String getRandomArr(int arrLength, int length, boolean flag) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < arrLength; i++) {
			sBuffer.append(getRandom(length, flag)).append(",");
		}
		int leng = sBuffer.toString().length();
		return sBuffer.toString().substring(0, leng-1);
	}

	/**
	 * 生成定长的带有引号的字符串数组，用","隔开
	 * @param arrLength
	 * @param length
	 * @param flag
	 * @return
	 */
	public static String generateStrArr(int arrLength, int length, boolean flag) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < arrLength; i++) {
			sBuffer.append("\"").append(getRandom(length, flag)).append("\"").append(",");
		}
		int leng = sBuffer.toString().length();
		return sBuffer.toString().substring(0, leng-1);
	}

	public static void main(String args[]) {
		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomNumber(1, 10));
		}
//		System.out.println(getRandomHanZi());
//		System.out.println(getRandomStr());
//		System.out.println(getRandomText(10));
//		System.out.println(getRandom(6,false));
//		System.out.println(getRandom(5,true));
//		System.out.println(getRandomArr(2,2,false));
//		System.out.println(generateStrArr(5, 2,false));
	}
}


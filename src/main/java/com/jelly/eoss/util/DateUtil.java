package com.jelly.eoss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *	为保证线程安全，必须用new SimpleDateFormat(xxx);
 */
public class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYYMMDD = "yyyyMMdd";
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	//星期特征日的一个标识，DAY1=星期一，DAY2=星期二，依次类推
	public static class DAYOFWEEK {
		public static String DAY1;
		public static String DAY2;
		public static String DAY3;
		public static String DAY4;
		public static String DAY5;
		public static String DAY6;
		public static String DAY7;
		public static String[] DAY_ARY;
		
		//为保证不受本机自定义设置影响，采用以下方法初始化相关信息
		static{
			SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat SDF2 = new SimpleDateFormat("E");
			
			try{
				DAY1 = SDF2.format(SDF1.parse("2012-07-02"));//周一
				DAY2 = SDF2.format(SDF1.parse("2012-07-03"));//周二
				DAY3 = SDF2.format(SDF1.parse("2012-07-04"));//周三
				DAY4 = SDF2.format(SDF1.parse("2012-07-05"));//周四
				DAY5 = SDF2.format(SDF1.parse("2012-07-06"));//周五
				DAY6 = SDF2.format(SDF1.parse("2012-07-07"));//周六
				DAY7 = SDF2.format(SDF1.parse("2012-07-08"));//周日
				DAY_ARY = new String[]{DAY1, DAY2, DAY3, DAY4, DAY5, DAY6, DAY7};
				
				//SDF1和SDF2已无用
				SDF1 = null;
				SDF2 = null;
			}catch(Exception e){
				log.error("Exception", e);
			}
		}
	}
	
	/**
	 * 取得日期时间<br/>
	 * 	2010-01-01<br/>
	 * 	20100101
	 * 
	 * @param withSeparator
	 *			true:输出含有分隔符"-";false:输出不含分隔符"-"
	 * @return java.lang.String
	 */
	public static final String GetCurrentDate(boolean withSeparator) {
		if(withSeparator){
			return new SimpleDateFormat(YYYY_MM_DD).format(new Date());
		}else{
			return new SimpleDateFormat(YYYYMMDD).format(new Date());
		}
	}
	
	public static final String GetDateStr(Date date, boolean withSeparator) {
		if(withSeparator){
			return new SimpleDateFormat(YYYY_MM_DD).format(date);
		}else{
			return new SimpleDateFormat(YYYYMMDD).format(date);
		}
	}
	
	/**
	 * 取得日期时间<br/>
	 * 	2010-01-01 22:30:56<br/>
	 * 	20100101223056
	 * 
	 * @param withSeparator
	 *			true:输出含有分隔符"-";false:输出不含分隔符"-"
	 * @return java.lang.String
	 */
	public static final String GetCurrentDateTime(boolean withSeparator) {
		if(withSeparator){
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date());
		}else{
			return new SimpleDateFormat(YYYYMMDDHHMMSS).format(new Date());
		}
	}
	
	/**
	 * 日期时间格式转换成<br/>
	 * 输入:2012-05-08 17:59:41<br/>
	 * 输出:20100101223056
	 * 
	 * @return
	 */
	public static final long ChangeDateTimeToLong(String dateTime) {
		dateTime = dateTime.replaceAll("-|:| ", "");
		return Long.parseLong(dateTime);
	}
	
	/**
	 * 
	 */
	public static final String ChangeLongToDataTime(String longStr){
		String str = null;
		
		try {
			Date date = new SimpleDateFormat(YYYYMMDDHHMMSS).parse(longStr);
			str = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(date);
		} catch (ParseException e) {
			log.error("ParseException", e);
		}
		
		return str;
	}
	
	/**
	 * 日期时间格式转<br/>
	 * 	输入:2012-05-08 17:59:41 或 20100101223056<br/>
	 * 	输出:1336471181000<br/>
	 *  转换异常时返回-1
	 * 
	 * @param withSeparator
	 *			true:输入含有分隔符"-";false:输入不含分隔符"-"
	 * @return java.lang.String
	 * @throws ParseException
	 */
	public static final long ChangeToTimeMillis(String dateTimeStr, boolean withSeparator) {
		long timeMillis = -1;
		
		try{
			if(withSeparator){
				timeMillis = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(dateTimeStr).getTime();
			}else{
				timeMillis = new SimpleDateFormat(YYYYMMDDHHMMSS).parse(dateTimeStr).getTime();
			}
		}catch(ParseException e){
            log.error("ParseException", e);
		}
		
		return timeMillis;
	}
	
	/**
	 * 输入：无<br/>
	 * 输出：当前本机时间是星期几<br/>
	 * 星期一,星期二,星期三,星期四,星期五,星期六,星期日
	 * @return String
	 */
	public static final String GetDayOfWeek(){
		return new SimpleDateFormat("E").format(new Date());
	}
	
	public static final String GetDayOfWeek(String dateTimeStr, boolean withSeparator){
		String rs = null;
		
		try{
			if(withSeparator){
				 rs = new SimpleDateFormat("E").format(new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(dateTimeStr));
			}else{
				rs = new SimpleDateFormat("E").format(new SimpleDateFormat(YYYYMMDDHHMMSS).parse(dateTimeStr));
			}
		}catch(Exception e){
            log.error("Exception", e);
		}
		return rs;
	}
}

package com.gj.test.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用数据格式验证.<br/>
 * 此类中的方法内部实现与表达式:<br/>
 * <blockquote><tt> {@link java.util.regex.Pattern}.{@link
 * java.util.regex.Pattern#matches(String,CharSequence)
 * matches}(</tt><i>regex</i><tt>,</tt> <i>input</i><tt>)</tt></blockquote> 的行为是一致的,不同的是此类中的方法效率更高,因为内部重用了相同的模式,<br>
 * 尤其在需要多次调用正则校验数据时,推荐首选该类中的方法。
 * <p>
 * 此类中的方法是线程安全的,可供多个并发线程安全使用。
 * </p>
 * Created by linlixiang on 16/5/11.
 */
public class Validator {

	/**
	 * 普通用户名正则
	 */
	private static Pattern usernamePatten = Pattern.compile( "^[a-zA-Z][a-zA-Z0-9]{6,18}$" );

	/**
	 * 普通密码名正则,允许的字符有:<br/>
	 * 单词字符：[a-zA-Z_0-9]<br/>
	 * 标点符号：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
	 */
	private static Pattern passwordPatten = Pattern.compile( "^(\\w|\\p{Punct}){6,18}$" );

	/**
	 * 中国手机号正则
	 */
	private static Pattern mobilePatten = Pattern.compile( "^((13\\d)|(14[57])|(15[0-35-9])|(17[0136-8])|(18\\d))\\d{8}$" );

	/**
	 * 邮箱地址正则
	 */
	private static Pattern emailPatten = Pattern.compile( "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$" );

	/**
	 * 中国居民身份证号正则
	 */
	private static Pattern idPattren = Pattern.compile( "(^\\d{18}$)|(^\\d{15}$)" );

	/**
	 * 汉字正则
	 */
	private static Pattern chinesePattren = Pattern.compile( "^[\u4e00-\u9fa5]{1,}$" );

	/**
	 * 验证是否符合用户名规则,以大小写字母开头,加数字,6~18位大小写字母及数字组合<br/>
	 * 注:请使用手机号或邮箱验证,采伴不使用普通用户名规则
	 *
	 * @param input 要匹配的字符序列
	 */
	public static boolean isUsername( CharSequence input ) {
		Matcher m = usernamePatten.matcher( input );
		return m.matches();
	}

	/**
	 * 验证是否符合密码规则,6~18位单词字符<br/>
	 * 允许的字符有:<br/>
	 * 单词字符：[a-zA-Z_0-9]<br/>
	 * 标点符号：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
	 * @param input 要匹配的字符序列
	 */
	public static boolean isPassword( CharSequence input ) {
		Matcher m = passwordPatten.matcher( input );
		return m.matches();
	}

	/**
	 * 验证是否是中国居民身份证,简单的15或18位数字<br/>
	 *
	 * @param input 要匹配的字符序列
	 */
	public static boolean isID( CharSequence input ) {
		Matcher m = idPattren.matcher( input );
		return m.matches();
	}

	/**
	 * 验证是否是汉字<br/>
	 *
	 * @param input 要匹配的字符序列
	 */
	public static boolean isChinese( CharSequence input ) {
		Matcher m = chinesePattren.matcher( input );
		return m.matches();
	}

	/**
	 * 验证是否是一个中国手机号码.目前支持以下手机号段:<br>
	 * <p>
	 * 电信:<br>
	 * 2G/3G号段（CDMA2000网络）133、153、180、181、189<br>
	 * 4G号段 177、173<br>
	 * 联通:<br>
	 * 2G号段（GSM网络）130、131、132、155、156<br>
	 * 3G上网卡 145<br>
	 * 3G号段（WCDMA网络）185、186<br>
	 * 4G号段 176、185<br>
	 * 移动:<br>
	 * 2G号段（GSM网络）134、135、136、137、138、139、150、151、152、158、159、182、183、184。<br>
	 * 3G号段（TD-SCDMA网络）157、187、188<br>
	 * 3G上网卡 147<br>
	 * 4G号段 178、184<br>
	 * 虚拟运营商:<br>
	 * 170、171
	 * </p>
	 *
	 * @param input 要匹配的字符序列
	 */
	public static boolean isMobile( CharSequence input ) {
		Matcher m = mobilePatten.matcher( input );
		return m.matches();
	}

	/**
	 * 验证是否是一个邮箱地址.
	 *
	 * @param input 要匹配的字符序列
	 */
	public static boolean isEmail( CharSequence input ) {
		Matcher m = emailPatten.matcher( input );
		return m.matches();
	}
}

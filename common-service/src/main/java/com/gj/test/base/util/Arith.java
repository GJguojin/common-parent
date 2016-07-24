package com.gj.test.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Arith {

	/**
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
	 */

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	// 保留小数位值
	public static final int DECIMAL_DIGIT_TWO = 2;

	// 这个类不能实例化
	private Arith() {
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */

	public static double add( double v1, double v2 ) {
		BigDecimal b1 = new BigDecimal( Double.toString( v1 ) );
		BigDecimal b2 = new BigDecimal( Double.toString( v2 ) );
		return b1.add( b2 ).doubleValue();
	}

	/**
	 * 返回一个 double，其值为 (value1 + values[0] + values[1] + ... + values[n])，其标度为 new BigDecimal(value1).scale()。
	 * 
	 * @param value1 被加数
	 * @param values 多个加数
	 * @return value1 + values[0] + values[1] + ... + values[n]
	 */
	public static double add( double value1, double... values ) {
		int len = 0;
		BigDecimal decimal = new BigDecimal( Double.toString( value1 ) );
		if( values != null && ( len = values.length ) > 0 ) {
			for( int i = 0; i < len; i++ ) {
				decimal = decimal.add( new BigDecimal( Double.toString( values[i] ) ) );
			}
		}
		return decimal.doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */

	public static double sub( double v1, double v2 ) {
		BigDecimal b1 = new BigDecimal( Double.toString( v1 ) );
		BigDecimal b2 = new BigDecimal( Double.toString( v2 ) );
		return b1.subtract( b2 ).doubleValue();
	}

	/**
	 * 返回一个 double，其值为 (value1 - values[0] - values[1] - ... - values[n])，其标度为 new BigDecimal(value1).scale()。
	 * 
	 * @param value1 被减数
	 * @param values 多个减数
	 * @return value1 - values[0] - values[1] - ... - values[n]
	 */
	public static double sub( double value1, double... values ) {
		int len = 0;
		BigDecimal decimal = new BigDecimal( Double.toString( value1 ) );
		if( values != null && ( len = values.length ) > 0 ) {
			for( int i = 0; i < len; i++ ) {
				decimal = decimal.subtract( new BigDecimal( Double.toString( values[i] ) ) );
			}
		}
		return decimal.doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */

	public static double mul( double v1, double v2 ) {
		BigDecimal b1 = new BigDecimal( Double.toString( v1 ) );
		BigDecimal b2 = new BigDecimal( Double.toString( v2 ) );
		return b1.multiply( b2 ).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */

	public static double div( double v1, double v2 ) {
		return div( v1, v2, DEF_DIV_SCALE );
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */

	public static double div( double v1, double v2, int scale ) {
		if( scale < 0 ) {
			throw new IllegalArgumentException( "The   scale   must   be   a   positive   integer   or   zero" );
		}
		BigDecimal b1 = new BigDecimal( Double.toString( v1 ) );
		BigDecimal b2 = new BigDecimal( Double.toString( v2 ) );
		return b1.divide( b2, scale, BigDecimal.ROUND_HALF_UP ).doubleValue();
	}

	public static double divup( double v1, double v2, int scale ) {
		if( scale < 0 ) {
			throw new IllegalArgumentException( "The   scale   must   be   a   positive   integer   or   zero" );
		}
		BigDecimal b1 = new BigDecimal( Double.toString( v1 ) );
		BigDecimal b2 = new BigDecimal( Double.toString( v2 ) );
		return b1.divide( b2, scale, BigDecimal.ROUND_UP ).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round( double v, int scale ) {
		if( scale < 0 ) {
			throw new IllegalArgumentException( "The   scale   must   be   a   positive   integer   or   zero" );
		}
		BigDecimal b = new BigDecimal( Double.toString( v ) );
		BigDecimal one = new BigDecimal( "1" );
		return b.divide( one, scale, BigDecimal.ROUND_HALF_UP ).doubleValue();
	}

	public static String format( double v ) {
		DecimalFormat decimalFormat = new DecimalFormat( "##.##" );
		return decimalFormat.format( v );
	}

	public static double round( double v ) {
		return round( v, 2 );
	}

	/**
	 * 提供精确的小数位进一处理。
	 * 
	 * @param v 需要进一处理的数字
	 * @param scale 小数点后保留几位
	 * @return 进一处理后的结果
	 */

	public static double roundUp( double v, int scale ) {
		if( scale < 0 ) {
			throw new IllegalArgumentException( "The   scale   must   be   a   positive   integer   or   zero" );
		}
		BigDecimal b = new BigDecimal( Double.toString( v ) );
		BigDecimal one = new BigDecimal( "1" );
		return b.divide( one, scale, BigDecimal.ROUND_UP ).doubleValue();
	}

	public static void main( String[] args ) {

		Double totalRmb = mul( 15.4, 6.5 ); // 人民币
		DecimalFormat df = new DecimalFormat( "######0.00" );
		System.out.println( df.format( totalRmb ) );

		System.out.println( new BigDecimal( df.format( totalRmb ) ).toString() );

	}
}

package com.gj.test.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印日志的通用类 <br />
 * 用法：在需要记录log的地方使用:<br />
 * <code>import static com.caiban.b2b.mall.util.WebLog.*;</code><br>
 * <code>log().info("====XXXX====");</code><br />
 * <code>log.error("====XXXX")</code>;etc.
 *
 * @author lx.lin
 * @since 1.0
 */
public class WebLog {
	private WebLog() {
	}

	private static Logger logger = null;

	static {
		logger = LoggerFactory.getLogger( WebLog.class );
	}

	public static Logger log() {
		return logger;
	}
}

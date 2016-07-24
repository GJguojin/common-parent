package com.gj.test.base.util;

import java.text.SimpleDateFormat;

public class ProjectCodeBook {
	// 分系统，菜单组
	public static int MENU_GROUP_ADMIN = 1;
	public static int MENU_GROUP_QUOTE = 70;
	// 计算货币，小数点后金额位数
	public static int SCALE_NUMBER = 2;

	public static final int TYPE_XIANHUO = 1;// 现货
	public static final int TYPE_SHIXIAO = 2;// 试销

	public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
	public static SimpleDateFormat DateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

	public static final String CURRENT_USER = "current_login_user";
	public static final String CAIBAN_URL = "caibanUrl"; // 采伴前台url
	public static final String USER_PASSWORD_DEF = "00000000";
	public static final int USER_LOGIN_ERROR_COUNT = 30;// 登录错误次数限制。

	//
	// type_group
	public static final String TYPE_GROUP_CONSIGN_CHANNEL = "type_group_consign_channel";
	public static final String TYPE_GROUP_WAREHOUSE = "type_group_warehouse";
	public static final String TYPE_GROUP_CATEGORY_MANAGE = "type_group_category_manage"; // 销售类目
	public static final String TYPE_GROUP_MENU = "type_group_menu"; // 销售类目
	public static final String TYPE_GROUP_CATEGORY = "type_group_category";// 报关类目
	public static final String TYPE_GROUP_SUPPLIER = "type_group_supplier";
	public static final String TYPE_GROUP_PRODUCT_BRAND = "type_group_product_brand";// 品牌
	public static final String TYPE_GROUP_PRODUCT_NATION = "type_group_product_nation";// 产地
	public static final String TYPE_GROUP_CURRENTCY = "type_group_currentcy";// 货币
	public static final String TYPE_GROUP_ORGANIZATION = "type_group_organization";// 组织机构
	public static final String TYPE_GROUP_ROLE = "type_group_role";// 角色
	public static final String TYPE_GROUP_CITY = "type_group_city"; // 城市

	public static final String TYPE_GROUP_DEALIER = "type_group_dealier";// 经销商
	public static final String TYPE_GROUP_OPERATE_COMMISSIONER = "type_group_operate_commissioner";// 运营专员

	// public static final String TYPE_GROUP_CONTRACT_CLIENT = "type_group_contract_client";
	public static final String[] CODE_PREFIX = { "", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000" };// prefix 前缀补充
	public static final int CONSIGN_SUFFIX_COUNT = 6;// suffix 发货单后缀位数
	public static final int CONSIGN_SIX_COUNT = 3;// 帐号操作记录流水号时间戳 与账户code中间 位数
	public static final String CONSIGN_ISFIRST_END_TIME = "16:00:00";// 每天截止处理票头件时间

	public static final String CONSIGN_HWT_ACCOUNT = "CBW";// 海外通服务企业代号
	public static final String CONSIGN_HWT_PASSWORD = "88888888";// 海外通服务企业密码
	public static final String CONSIGN_HWT_LOGIN = "http://203.195.200.110:6060/ws/userService/bizLogin";// 海外通增加订单
	public static final String CONSIGN_HWT_ADDORDER = "http://203.195.200.110:6060/ws/orderService/addOrder";// 海外通增加订单
	public static final String CONSIGN_HWT_FINDORDER = "http://203.195.200.110:6060/ws/orderService/findOrder";// 海外通查询订单
	public static final String CONSIGN_HWT_FINDORDERROUTE = "http://203.195.200.110:6060/ws/orderRouteService/findOrderRoute";// 海外通查询订单路由
	public static final String CONSIGN_HWT_RECORDPRODUCT = "http://203.195.200.110:6060/ws/stockService/addStockInfo";// 海外通商品备案
	public static final String CONSIGN_HWT_UPDATEORDER = "http://203.195.200.110:6060/ws/orderService/updateOrder";// 海外通订单修改
	public static final String CONSIGN_HWT_BBC = "1";// 保税
	public static final String CONSIGN_HWT_BC = "2";// 直邮

	// excel 导入
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	public static final String LIB_PATH = "lib";
	public static final String STUDENT_INFO_XLS_PATH = LIB_PATH + "/student_info" + POINT + OFFICE_EXCEL_2003_POSTFIX;
	public static final String STUDENT_INFO_XLSX_PATH = LIB_PATH + "/student_info" + POINT + OFFICE_EXCEL_2010_POSTFIX;
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	public static final String PROCESSING = "Processing...";

	// 语言
	public static final String[] languageGroup = { "en", "fr", "de", "it", "ja", "ko", "zh", };

	// 导入
	// 仓库
	public static final String IMPORT_WAREHOUSE_WTD = "威时沛";
	public static final String IMPORT_WAREHOUSE_FF = "飞凡";
	/**
	 * 导入excel 订单总表 String[序号][是否必填：1是]
	 */
	public static final String[][] EXCEL_CONSIGN_CELL_NAME = { { "订单编号", "1" }, { "买家会员名", "1" }, { "买家支付宝账号", "1" }, { "买家应付货款", "0" }, { "买家应付邮费", "0" }// 5
					, { "买家支付积分", "0" }, { "总金额", "0" }, { "返点积分", "0" }, { "买家实际支付金额", "0" }, { "买家实际支付积分", "0" }// 10
					, { "订单状态", "0" }, { "买家留言", "0" }, { "收货人姓名", "1" }, { "收货地址", "1" }, { "运送方式", "0" }// 15
					, { "联系电话", "0" }, { "联系手机", "1" }, { "订单创建时间", "1" }, { "订单付款时间", "0" }, { "宝贝标题", "0" }// 20
					, { "宝贝种类", "0" }, { "物流单号", "0" }, { "物流公司", "0" }, { "订单备注", "0" }, { "宝贝总数量", "0" }// 25

					, { "店铺Id", "1" }, { "店铺名称", "1" }, { "订单关闭原因", "0" }, { "卖家服务费", "0" }, { "买家服务费", "0" }// 30
					, { "发票抬头", "0" }, { "是否手机订单", "0" }, { "分阶段订单信息", "0" }, { "定金排名", "0" }, { "修改后的sku", "0" }// 35
					, { "修改后的收货地址", "0" }, { "异常信息", "0" }, { "天猫卡券抵扣", "0" }, { "集分宝抵扣", "0" }, { "是否是O2O交易", "0" }// 40
					, { "身份证号码", "1" }, { "支付号", "1" } };
	/**
	 * 导入excel 订单商品表详情表 String[序号][是否必填：1是]
	 */
	public static final String[][] EXCEL_CONSIGN_GOODS_CELL_NAME = { { "订单编号", "1" }, { "标题", "1" }, { "价格", "1" }, { "购买数量", "1" }, { "外部系统编号", "1" },//
					{ "商品属性", "0" }, { "套餐信息", "0" }, { "备注", "0" }, { "订单状态", "0" }, { "商家编码", "0" } };
	/**
	 * 导入excel 飞凡订单 String[序号][是否必填：1是]
	 */
	/*
	 * public static final String[][] EXCEL_CONSIGN_FF_NAME_STRINGS = { { "订单编号", "1" }, { "订单状态", "0" }, { "收件人姓名", "1" }, { "收件人省", "1" }, { "收件人市", "1" }// 5
	 * , { "收件人地址", "1" }, { "收件人电话", "1" }, { "发货人", "0" }, { "发货人所在国家", "0" }, { "发货人地址", "0" }// 10 , { "发货人电话", "0" }, { "订单人ID", "0" }, { "订单人姓名", "1" }, {
	 * "订单人证件类型", "0" }, { "订单人证件号码", "1" }// 15 , { "商品信息", "1" }, { "商品海关备案号", "1" }, { "计量单位", "0" }, { "单个订单商品序号", "0" }, { "总净重", "0" } // 20 , { "总毛重",
	 * "0" }, { "包裹件数", "0" }, { "申报数量", "1" }, { "支付金额", "1" }, { "商品单价", "1" }// 25 , { "商品总价", "1" }, { "运费", "0" }, { "税款", "0" }, { "保价费", "0" }, { "订单日期",
	 * "1" }// 30 , { "付款时间", "0" }, { "支付交易号", "0" }, { "商品商检备案号", "1" }, { "商品批次号", "0" }, { "备注", "0" }// 35 , { "序号", "0" }, { "商品货号", "1" }, { "支付企业名称",
	 * "1" },{"收件人区/县","0"}};//
	 */

	public static final String[][] EXCEL_CONSIGN_FF_NAME_STRINGS = { { "订单编号", "1" }, { "订单状态", "0" }, { "收件人姓名", "1" }, { "收件人省", "1" }, { "收件人市", "1" }// 5
					, { "收件人地址", "1" }, { "收件人电话", "1" }, { "发货人", "0" }, { "发货人所在国家", "0" }, { "发货人地址", "0" }// 10
					, { "发货人电话", "0" }, { "订单人ID", "0" }, { "订单人姓名", "1" }, { "订单人证件类型", "1" }, { "订单人证件号码", "1" }// 15
					, { "商品信息", "1" }, { "商品海关备案号", "0" }, { "计量单位", "0" }, { "单个订单商品序号", "0" }, { "总净重", "0" } // 20
					, { "总毛重", "0" }, { "包裹件数", "0" }, { "申报数量", "1" }, { "支付金额", "1" }, { "商品单价", "1" }// 25
					, { "商品总价", "1" }, { "运费", "0" }, { "税款", "0" }, { "保价费", "0" }, { "订单日期", "1" }// 30
					, { "付款时间", "1" }, { "支付交易号", "1" }, { "商品商检备案号", "0" }, { "商品批次号", "0" }, { "备注", "0" }// 35
					, { "序号", "0" }, { "商品货号", "1" }, { "支付企业名称", "1" }, { "收件人区/县", "0" } };

	/**
	 * 导入excel 威时沛运 订单 String[序号][是否必填：1是]
	 */
	public static final String[][] EXCEL_CONSIGN_WTD_CELL_NAME = { { "订单编号", "1" }, { "订单日期", "1" }, { "发货人", "0" }, { "发货联系电话", "0" }, { "收货人", "1" }//
					, { "身份证号码", "1" }, { "收货地址", "1" }, { "收货联系电话", "1" }, { "商品编号", "1" }, { "商品名称", "1" }//
					, { "件数", "1" }, { "单价", "1" }, { "总价", "1" }, { "运费", "1" }, { "备注", "0" } //
					, { "保价费", "0" } };

	public static final String[] EXCEL_CELL_NAME = { "A", "B", "C", "D", "E",//
					"F", "G", "H", "I", "J",//
					"K", "L", "M", "N", "O",//
					"P", "Q", "R", "S", "T",//
					"U", "V", "W", "X", "Y",//
					"Z", "AA", "AB", "AC", "AD",//
					"AE", "AF", "AG", "AH", "AI",//
					"AJ", "AK", "AL", "AM", "AN",//
					"AO", "AP", "AQ", "AR", "AS",//
					"AT", "AU", "AV", "AW", "AX",//
					"AY", "AZ", "BA", "BB", "BC",//
					"BD", "BE", "BF", "BG", "BH",//
					"BI", "BJ", "BK", "BL", "BM",//
					"BN", "B0", "BP", "BQ", "BR",//
					"BS", "BT", "BU", "BV", "BW",//
					"BX", "BY", "BZ"//

	};
}

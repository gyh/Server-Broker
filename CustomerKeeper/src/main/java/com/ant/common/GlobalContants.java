package com.gome.mobile.commerce.prom.common;

import java.util.ResourceBundle;

/**
 * 公共的静态变量
 * 
 * @author chenzhongwei
 * 
 */
public class GlobalContants {

	/**
	 * 主页模板key
	 */
	// public static final String homepageKey = "homeAp2HmunFW6z4G";
	/**
	 * wap 网址
	 */
	// public static final String wapUrl = "http://m.atguat.com.cn";//
	// http://m.gome.com.cn
	public static final String hessianAdds = "";

	// 店铺装修
	public static final String CMS_MERCHANT = "cms_merchant_"; // 店铺装修redis的key

	public static final Integer SHOP_RECOMMEND_SORT = 3; // 店铺推荐排序

	public static final Integer SHOP_SELLING_SORT = 5; // 店铺热销排序

	public static final Integer SHOP_NEW_SORT = 7; // 店铺新品排序

	public static final String SHOP_RECOMMEND = "店铺推荐"; // 店铺推荐

	public static final String SHOP_SELLING = "店铺热销"; // 店铺热销

	public static final String SHOP_NEW = "店铺新品"; // 店铺新品

	public static final String COUPON_NOT_BEGINNING = "0"; // 未开始

	public static final String COUPON_BEGINNING = "1"; // 开始

	public static final String COUPON_END = "2"; // 已结束

	public static final String COUPON_OVER = "3"; // 已发完

	public static final String COUPON_HAVING = "4"; // 已领过

	public static final String PIC_SUFFIX_W = "_360.jpg"; // 图片后缀_360.jpg

	public static final String PIC_SUFFIX_B = "_160.jpg"; // 图片后缀_160.jpg

	public static final String CatId = "ALL";

	public static final Integer ProdRowNum = 2;

	// 大牌DOWM
	public static final int BIGMAP_SIZE = 1;// 大图数量

	public static final int SMALLMAP_SIZE = 6;// 小图数量

	public static final String ALL_PLATFORM = "all";// 支持所有平台的字段

	public static final String APP_PLATFORM = "app";// 支持app的字段

	public static final String WAP_PLATFORM = "wap";// 支持wap的字段

	public static final String CMS_PAGE = "cms_page_";// cms页面路径

	// 最国美商品列表
	public static final String FILTER_KEYWORD_BESTGOME = "BestGome";

	private static ResourceBundle resource = null;

	// 抢购
	/** 未开始*/
	public static final String NOT_BEGIN = "0"; 
	/** 开始 */
	public static final String BEGINING = "1"; 
	/** 抢光了**/
	public static final String NO_REMAINNUM = "2"; 
    /**结束了 */
	public static final String FINISHED = "3"; 

	public static final String NOT_BEGIN_TEXT = "notStarted"; // 未开始解释

	public static final String BEGINING_TEXT = "buying"; // 开始解释

	public static final String NO_REMAINNUM_TEXT = "soldOut"; // 抢光了解释

	public static final String FINISHED_TEXT = "ending"; // 结束了解释

	public static final String PROM_EHCACHE = "PROM_EHCACHE"; // ehcache的名称

	public static final String USER_GRADE_INVOKE_FROM = "others_mobile"; // 用户公共helper方法->用户等级方法参数

	static {
		resource = ResourceBundle.getBundle("mobile-prom-config");
	}

	/**
	 * 获取mobile-cms-config.properties
	 * 
	 * @author chenzhongwei
	 * @param key
	 *            key
	 * @return value
	 */
	public static Object getValue(String key) {
		return resource.getObject(key);
	}

}

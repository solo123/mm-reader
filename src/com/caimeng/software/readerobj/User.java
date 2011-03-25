package com.caimeng.software.readerobj;

import java.util.Vector;

public class User {

	public static String updateVersion = "";//更新版本叄1�7
	public static String updateURL = "";//客户端升级地坄1�7
	public static boolean mustUpdate = false;//是否强制升级
	public static String updateMessage = "";//客户端升级加

	public static String userId = "";//用户ID  用户唯一标识
	public static String pageID = "";//用户访问首页的页面ID，未记录用户首页信息时为穄1�7
	public static String regCode = "";//濄1�7活码。DRM使用RegCode生成REK（许可证加密密钥），客户端同样根据RegCode生成REK，用于许可证解密〄1�7
	public static String userMonthlyCode = "";//包月区域编号
	public static String userMonthlyType = "";//包月区域类型 2两元包月；5五元包月
	
	public static Vector vePage = new Vector();//String[2] {pageid pagename}
}

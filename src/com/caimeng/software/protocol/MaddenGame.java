package com.caimeng.software.protocol;

import com.caimeng.software.model.Consts;
import com.caimeng.software.network.Connect;

/**
 * 劲爆游戏
 * 
 * @author machao
 * 
 */
public class MaddenGame extends Connect {
	public MaddenGame(int connect, String ip, int port) {
		super(connect, ip, port);
	}

	public MaddenGame(String url, String action, String methed) {
		super(url, action, methed);
	}

	int time = 0;

	public String getGameActivatePath(String parameic) {
		String path = null;
		String result = null;
		byte[] output = null;
		try {
			String server = super.url.substring(7);
			server = server.substring(0, server.indexOf("/"));
			output = this.queryServer(parameic.getBytes());
			if (output != null) {
				result = new String(output, "UTF-8");
				// 分析地址
				int index = result.indexOf("buySalesPackage");

				if (index == -1) {
					index = result.indexOf(Consts.HASBUY);
					if (index != -1) {// 确实已经订购
						return Consts.HASBUY;
					}
				} else {
					time = 0;
					String tempURL = "";
					int start = 0;
					int end = 0;
					for (int i = index; i > 0; i--) {
						if (result.charAt(i) == '"') {
							start = i;
							break;
						}
					}
					for (int i = index; i < result.length(); i++) {
						if (result.charAt(i) == '"') {
							end = i;
							break;
						}
					}

					// 获取订购地址
					tempURL = result.substring(start + 1, end);
					path = "http://" + server + tempURL;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	public String doMaddenGame01(String parameic) {
		String target = "jsessionid=";
		String result = null;
		byte[] output = null;
		try {
			output = this.queryServer(parameic.getBytes());
			System.out.println(1);
			if (output != null) {
				System.out.println(2);
				result = new String(output, "UTF-8");
				Consts.test.append(result+"\n\t");
				int index = 0;
				index = result.indexOf(target);
				int start = 0;
				int end = 0;
				for (int i = index; i > 0; i--) {
					if (result.charAt(i) == '"') {
						start = i;
						break;
					}
				}
				for (int i = index; i < result.length(); i++) {
					if (result.charAt(i) == '"') {
						end = i;
						break;
					}
				}

				// 获取订购地址
				result = result.substring(start + 1, end);
				Consts.test.append(result+"\n\t");
			}
		} catch (Exception e) {

		}
		return result;
	}

	public String doMaddenGame02(String parameic) {
		String result = null;
		byte[] output = null;
		try {
			output = this.queryServer(parameic.getBytes());
			if (output != null) {
				result = new String(output, "UTF-8");
			}
		} catch (Exception e) {

		}
		return result;
	}

}

package com.caimeng.software.xml;

import java.util.Vector;

import com.caimeng.software.readerobj.User;


public class ParserXml {

    // 登陆和注册解析都调用这个方法，调用后User会有数据，User.userID是必须要得到的，
    public static void getUserInfo(KXmlParser parser) {
        try {
            int eventType = parser.getEventType();
            String strText = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT) {
                        strText = parser.getText();
                } else if (eventType == XmlPullParser.END_TAG) {
                    String str = parser.getName().toLowerCase();
                    if ("userid".equals(str)) {
                        User.userId = strText;
                    } else if ("pageid".equals(str)) {
                        User.pageID = strText;
                    } else if ("userinfo".equals(str)) {
                        break;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
        }
    }

    public static void getClientInfo(KXmlParser parser) {
        try {
            int eventType = parser.getEventType();
            String strText = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                 if (eventType == XmlPullParser.TEXT) {
                    if (!parser.getText().equals("")) {
                        strText = parser.getText().trim();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String str = parser.getName().toLowerCase();
                    if ("updateversion".equals(str)) {
                        User.updateVersion = strText;
                    } else if ("updateurl".equals(str)) {
                        User.updateURL = strText;
                    } else if ("updatemessage".equals(str)) {
                        User.updateMessage = strText;
                    } else if ("mustupdate".equals(str)) {
                        User.mustUpdate = strText.equals("true");
                    } else if ("clientinfo".equals(str)) {
                        break;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
        }
    }

    public static void login(KXmlParser parser) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String str = parser.getName().toLowerCase();
                    if (str.equals("userinfo")) {
                        getUserInfo(parser);
                    } else if (str.equals("clientinfo")) {
                        getClientInfo(parser);
                    } 
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            //#ifdef SHOWLOG
        //#endif
        }
    }

    public static void register(KXmlParser parser) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String str = parser.getName().toLowerCase();
                    if (str.equals("userinfo")) {
                        getUserInfo(parser);
                    } else if (str.equals("clientinfo")) {
                        getClientInfo(parser);
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
        }
    }

    public static String[] getCatalogInfoArray(KXmlParser parser) {
        String[] cids = new String[1];
        try {
            int eventType = parser.getEventType();
            String strText = "";
            int count=0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                 if (eventType == XmlPullParser.TEXT) {
                        strText = parser.getText().trim();
                } else if (eventType == XmlPullParser.END_TAG) {
                    String str = parser.getName().toLowerCase();
                    if ("catalogid".equals(str)) {
                        cids[count] = strText;
                        count++;
                        if(count==cids.length)break;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return cids;
    }

    
    public static String[] getCatalogInfoArrayTOPV(KXmlParser parser) {
        String[] cids = null;
        Vector v=new Vector();
        try {
            int eventType = parser.getEventType();
            String strText = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                 if (eventType == XmlPullParser.TEXT) {
                        strText = parser.getText().trim();
                } else if (eventType == XmlPullParser.END_TAG) {
                    String str = parser.getName().toLowerCase();
                    if ("catalogid".equals(str)) {
                    	v.addElement(strText);
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
        }
        if(v.size()>0){
        	cids=new String[v.size()];
        	for(int i=0;i<v.size();i++){
        		cids[i]=(String)v.elementAt(i);
        	}
        }
        return cids;
    }

}

package com.caimeng.software.xml;

import java.util.Vector;

public class PostContent {

    String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request></Request>";

    public void addLabel(String upLabel, String label) {
        try {
            String fatherTag = "<" + upLabel + ">";
            int index = str.indexOf(fatherTag)+fatherTag.length();
            String tmp1 = str.substring(0, index);
            String tmp2 = str.substring(index);
            str = tmp1 + "<" + label + ">" + "</" + label + ">" + tmp2;
        } catch (Exception e) {
            //#ifdef SHOWLOG
//#             LogUtil.showLog("addLabel", this, e.toString());
        //#endif
        }
    }

    public void addParameter(String label, String parameter, String value) {
        try {
            int index = str.indexOf("<" + label) + ("<" + label).length();
            String tmp1 = str.substring(0, index);
            String tmp2 = str.substring(index);
            str = tmp1 + " " + parameter + "=" + value + tmp2;
        } catch (Exception e) {
            //#ifdef SHOWLOG
//#             LogUtil.showLog("addParameter", this, e.toString());
        //#endif
        }
    }

    public void addContent(String label, String content) {
        try {
            String tag = "<" + label + ">";
            int index = str.indexOf(tag)+tag.length();
            String tmp1 = str.substring(0, index);
            String tmp2 = str.substring(index);
            str = tmp1 + content + tmp2;
        } catch (Exception e) {
            //#ifdef SHOWLOG
//#             LogUtil.showLog("addContent", this, e.toString());
        //#endif
        }
    }

//    public void addList(String upLabel, String label, Vector veStr) {
//        try {
//            int index = str.indexOf("</" + upLabel + ">");
//            String tmp1 = str.substring(0, index);
//            String tmp2 = str.substring(index);
//            String conList = "";
//            for (int i = 0; i < veStr.size(); i++) {
//                conList = conList + "<" + label + ">" + veStr.elementAt(i).toString() + "</" + label + ">";
//            }
//            str = tmp1 + conList + tmp2;
//            conList = null;
//        } catch (Exception e) {
//            //#ifdef SHOWLOG
////#             LogUtil.showLog("addList", this, e.toString());
//        //#endif
//        }
//    }

    public String getXml() {
        return str;
    }
}

package com;

/**
 * @author zxy
 * @date 2021/10/1 23:26
 * @description
 */
public class Test {
    public static void main(String[] args) {

        String str="MN=123456;DataTime=20190312113200001;a01011-Rtd=11;a01012-Rtd=22;a01013-Rtd=33;a01014-Rtd=44;a01017-Rtd=55;a05024-Rtd=66;a19002-Rtd=77;a24088-Rtd=88;a34000-Rtd=99;a34041-Rtd=100;ga0601-Rtd=110;ga0611-Rtd=120;ga0701-Rtd=130;ga0801-Rtd=140;ga2001-Rtd=150;ga2011-Rtd=160;ga2101-Rtd=170;ga2201-Rtd=180;";
        String[] mnTmpStrArray=str.split("DataTime=");
        String mn="0";
        if(mnTmpStrArray.length>1) {
            String mnTempStr =mnTmpStrArray[1];
            mn = mnTempStr.substring(0, mnTempStr.indexOf(";"));
        }
        System.out.println(mn);
    }

    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}

/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.common.utils;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author Jiangjiaze
 * @version Id: StringUtil.java, v 0.1 2016/8/22 0022 上午 10:08 FancyKong Exp $$
 */
public class StringUtil {
    public static String fillLeft(String orignalString, char fillchar, int lenth) {
        return Strings.padStart(orignalString, lenth, fillchar);
    }

    public static boolean isNotEmpty(String str) {
        return !Strings.isNullOrEmpty(str);
    }

    public static String generateRandomString(int i) {
        char ac[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        StringBuilder stringbuffer = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < i; j++)
            stringbuffer.append(ac[random.nextInt(ac.length)]);

        return stringbuffer.toString();
    }

    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.equals(""))
            return null;
        s = s.toUpperCase();
        int i = s.length() / 2;
        char ac[] = s.toCharArray();
        byte abyte0[] = new byte[i];
        for (int j = 0; j < i; j++) {
            int k = j * 2;
            abyte0[j] = (byte) (charToByte(ac[k]) << 4 | charToByte(ac[k + 1]));
        }

        return abyte0;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static String bytesToHexString(byte abyte0[]) {
        StringBuilder stringbuilder = new StringBuilder("");
        if (abyte0 == null || abyte0.length <= 0)
            return null;
        for (int i = 0; i < abyte0.length; i++) {
            int j = abyte0[i] & 0xff;
            String s = Integer.toHexString(j);
            if (s.length() < 2)
                stringbuilder.append(0);
            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static String replaceString(String oldString, String... arg)
    {
        for(String replaceStr:arg){
            oldString = oldString.replaceFirst("#", replaceStr);

        }
        return oldString;

    }
    public static String formatNumber(double paramDouble, int paramInt)
    {
        return NumberFormat.getNumberInstance().format(round(paramDouble, paramInt));
    }
    public static double round(double paramDouble, int paramInt)
    {
        if (paramInt < 0) {
            throw new RuntimeException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble));
        BigDecimal localBigDecimal2 = new BigDecimal("1");
        return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
    }

    /**
     * 屏蔽字符串中间字符
     * @param str 原字符串
     * @param leftCount 保留左边位数
     * @param lightCount 保留右边位数
     * @param replaceChar 中间替换的字符
     * @return
     */
    public static String encryptString(String str, int leftCount, int lightCount, String replaceChar) {

        if (str != null) {
            StringBuffer cardNoEn = new StringBuffer();
            int strLength = str.length();
            int sumCount = leftCount + lightCount;
            String cardNoLeft = "";
            String cardNoLight = "";
            String strEn = "";

            if (strLength > sumCount){
                //取前leftCount位
                cardNoLeft = str.substring(0, leftCount);
                //取后lightCount位
                cardNoLight = str.substring(strLength - lightCount, strLength);
                //需要补充加密字符
                strEn = String.format("%" + (strLength - sumCount) + "s", "").replaceAll("\\s", replaceChar);

                str = cardNoEn.append(cardNoLeft).append(strEn).append(cardNoLight).toString();
            }
        }
        return str;
    }
}

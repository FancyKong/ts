package com.jdkcc.ts.util;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <p>
 * Description: 字符串处理的公共类
 * </p>
 * <p>
 * Copyright  
 * </p>
 * 
 * @Create Date :  2016-01-23
 * @Version : 1.0
 */
public class MStringUtils {

	
	/**
	 *  string ->BigDecimal
	 * 
	 * @param value
	 * @return BigDecimal
	 */
	public static BigDecimal strToDecimal(String value)
	{
		return new BigDecimal(value);
		
	}
	
	/**
	 * 将字符串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(String pValue) {
		double S = 0;

		try {
			S = Double.parseDouble(pValue);
		} catch (Exception e) {
		}

		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - (float) integer)); // 取得他的小数部分
			if (integer == 0)
				result = "零圆"; // 如果整数为0,则显示零圆
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				result = temp1 + temp2 + result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				result = result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			 //System.out.print("result frist: "+result);
            result= result.replaceAll("零仟","零");
            result= result.replaceAll("零佰","零");
            result= result.replaceAll("零拾","零");
            while(result.indexOf("零零")>-1)
            {
            	result= result.replaceAll("零零","零");
            }
            result = result.replaceAll("零圆","圆");
            result = result.replaceAll("零万","万");
            if("圆零角零分".equals(result))
            {
            	result="零圆零角零分";
            }
	          //  System.out.print("result second: "+result);
		} catch (Exception se) {
			se.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 空字符串处理,空字符串指定默认值
	 * @param value 查出来的值
	 * @param type 默认值
	 * @return String 返回默认串
	 * **/
	public static String processNullString(String value,String type){
		if(null == value||"".equals(value)){
			return type;
		}else{
			return value.trim();
		}
	}
	
	/**
	 * 转换字符串到日期 
	 * @param value
	 * @return
	 * @throws  
	 */
	public static java.sql.Timestamp convertStringToDate(String value){
		java.sql.Timestamp sd = null;
		Date ud = null;
		if(value!=null && value!=""){
			try {
				SimpleDateFormat sdf=null;
				if(value.indexOf(":")==-1){//判断是否包含时分秒
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}else{
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				ud = sdf.parse(value);
				sd = new java.sql.Timestamp(ud.getTime());
				return sd;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		ud = new Date(System.currentTimeMillis());
		sd = new java.sql.Timestamp(ud.getTime());
		return sd;
	}
	
	/**
	 * 将数字串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(double pValue) {
		double S = pValue;
		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String Result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - integer)); // 取得他的小数部分
			if (integer == 0) {
				Result = "零圆"; // 如果整数为0,则显示零圆
			}
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				Result = temp1 + temp2 + Result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				Result = Result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			// System.out.print("Result frist: "+Result);
			Result = Result.replaceAll("零仟", "零");
			Result = Result.replaceAll("零佰", "零");
			Result = Result.replaceAll("零拾", "零");
			while (Result.indexOf("零零") > -1) {
				Result = Result.replaceAll("零零", "零");
			}
			Result = Result.replaceAll("零圆", "圆");
			Result = Result.replaceAll("零万", "万");
			if ("圆零角零分".equals(Result)) {
				Result = "零圆零角零分";
			}
			// System.out.print("Result second: "+Result);

		} catch (Exception se) {
			se.printStackTrace();
		}
		return Result;
	}

	public static final String[] split(String line, String separator) {
		int index = 0;
		ArrayList matchList = new ArrayList();

		int start = line.indexOf(separator, index);

		if (start == -1) {
			return new String[] { line.toString() };
		}

		while (start > -1) {
			String match = line.subSequence(index, start).toString();
			matchList.add(match);
			index = start + separator.length();
			start = line.indexOf(separator, index);
		}

		matchList.add(line.subSequence(index, line.length()).toString());

		int resultSize = matchList.size();

		while (resultSize > 0 && matchList.get(resultSize - 1).equals("")) {
			resultSize--;
		}

		String[] result = new String[resultSize];
		return (String[]) matchList.subList(0, resultSize).toArray(result);
	}

	public static final String[] splitWithoutSpace(String line, String separator) {
		LinkedList list = new LinkedList();
		if (line != null) {
			int start = 0;
			int end = 0;
			int separatorLen = separator.length();
			while ((end = line.indexOf(separator, start)) >= 0) {
				String str = line.substring(start, end).trim();
				if (str != null && !str.equals("")) {
					list.add(str);
				}
				start = end + separatorLen;
			}
			if (start < line.length()) {
				String str = line.substring(start, line.length()).trim();
				if (str != null && !str.equals("")) {
					list.add(str);
				}
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static final String replaceAll(String line, String str1, String str2) {
		StringBuffer newStr = new StringBuffer();
		int lastAppendIndex = 0;

		int start = line.indexOf(str1, lastAppendIndex);

		if (start == -1) {
			return line;
		}

		while (start > -1) {
			newStr.append(line.subSequence(lastAppendIndex, start));
			newStr.append(str2);
			lastAppendIndex = start + str1.length();
			start = line.indexOf(str1, lastAppendIndex);
		}
		newStr.append(line.subSequence(lastAppendIndex, line.length()));

		return newStr.toString();

	}

	public static String join(Iterator iterator, String separator)
			throws Exception {
		if (iterator == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(256);
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append("'" + obj + "'");
			}
			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	/**
	 * 把字符转换为钱数0.00的格式
	 * 
	 * @param temp
	 * @return
	 */
	public static String formatToMoney(String str) {
		int place = 0;
		int len = 0;
		if (str == null || "".equals(str)) {
			return "0.00";
		}
		place = str.indexOf(".");
		if (place == -1) {
			return str = str + ".00";
		}
		len = str.length();
		if ((len - place) == 2) {
			str = str + "0";
		} else if ((len - place) == 1) {
			str = str + "00";
		} else {
			str = str.substring(0, place + 3);
		}
		return str;
	}

	/**
	 * function :校验是否为货币性 time :2009-8-24
	 * */
	public static boolean isMoney(String money) {

		if (money == null || money.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
		Matcher isNum = pattern.matcher(money);
		return isNum.matches();
	}

	/*
	 * function :校验是否为空 time :2009-8-24 *
	 */
	public static boolean isEmpty(String str) {
		if (null == str || str.trim().length() == 0
				|| "null".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 首字母大写 ：通过匹配以单词边界是小写26个字母开头
	 * 
	 * @param is
	 * @return
	 */
	public static String capitalized(String word){
		
		Pattern p=Pattern.compile("([\\{|\\,])\"[a-z]");
        Matcher m=p.matcher(word);
        while(m.find()){
        	word = word.replace(m.group(),m.group().toUpperCase());
        }
		return word;
	}

	public static String convertStreamToString(InputStream is) {
		return convertStreamToString(is, null);
	}

	public static String getSafeString(String str) {
		if (str == null)
			return "";
		return str;
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is, Charset charset) {
		BufferedReader reader = null;
		if (charset == null) {
			try {
				reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			reader = new BufferedReader(new InputStreamReader(is, charset));
		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 用16进制表示给定的byte数组
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String bytes2HexString(byte[] b) {
		String hexStr = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			hexStr += hex;
		}
		return hexStr.toUpperCase();
	}

	/**
	 * 在字符串str左边补齐0直到长度等于length
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String enoughZero(String str, int len) {
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}

	public static String join(String seperator, String[] strings) {
		if (strings == null)
			return "";
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	public static String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	public static String joinWithQMarks(String seperator, String[] strings) {

		List list = Arrays.asList(strings);
		return joinWithQMarks(seperator, list.iterator());
	}

	public static String joinWithQMarks(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append("'").append(objects.next()).append("'");
		while (objects.hasNext()) {
			buf.append(seperator).append("'").append(objects.next())
					.append("'");
		}
		return buf.toString();
	}

	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	public static boolean isEqual(String o, boolean c) {

		if (o == null || "".equals(o))
			return false;
		return o.equals(String.valueOf(c).toLowerCase());

	}

	public static boolean isEqual(String o, String c) {
		if (MStringUtils.isEmpty(o)) {
			o = "";
		}
		if (MStringUtils.isEmpty(c)) {
			c = "";
		}
		return o.equals(c);
	}

	public static String toString(Object[] array) {
		int len = array.length;
		if (len == 0)
			return "";
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(", ");
		}
		return buf.append(array[len - 1]).toString();
	}

	public static String[] toArray(List list) {
		int len = list.size();
		if (len == 0)
			return null;
		String[] array = new String[len];
		for (int i = 0; i < len; i++) {
			array[i] = list.get(i).toString();
		}
		return array;
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static String truncate(String string, int length) {
		if (string.length() <= length) {
			return string;
		} else {
			return string.substring(0, length);
		}
	}

	public static String getStrValue(Map m, String name) {
		if (m == null) {
			return "";
		}
		Object t = m.get(name);
		if (t == null)
			return "";
		return (m.get(name) + "").trim();
	}

	public static String getStrValue(Map m, String name, String dft) {
		if (m == null) {
			return dft;
		}
		Object t = m.get(name);
		if (t == null)
			return dft;
		return (m.get(name) + "").trim();
	}

	public static String toUpperCase(String str) {
		return str == null ? null : str.toUpperCase();
	}

	public static String toLowerCase(String str) {
		return str == null ? null : str.toLowerCase();
	}

	public static HashMap toMap(String[] array) {

		if (array == null)
			return null;
		int len = array.length;

		if (len == 0)
			return null;

		HashMap map = new HashMap();

		for (int i = 0; i < len; i++) {
			map.put(array[i], array[i]);
		}
		return map;

	}/*--repl将被with替换 -1为替换字符串text中所有的repl--*/

	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/*--max为被替换的个数--*/
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || max == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 将两个字符串转换成数字类型 返回大的那个
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String max(String str1, String str2) {
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if (num1 > num2) {
			return str1;
		} else {
			return str2;
		}
	}

	/**
	 * 将两个字符串转换成数字类型 返回小的那个
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String min(String str1, String str2) {
		int num1 = Integer.parseInt(str1);
		int num2 = Integer.parseInt(str2);
		if (num1 > num2) {
			return str2;
		} else {
			return str1;
		}
	}

	/**
	 * 校验是否为数字
	 * */
	public static boolean isNum(String num) {

		if (num == null || num.equals(""))
			return false;

		if (num.startsWith("-")) {
			return isNum(num.substring(1));
		}

		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher isNum = pattern.matcher(num);
		return isNum.matches();
	}

	/**
	 * 校验是否由数字和26个英文字母组成的字符串
	 * */
	public static boolean isAlpha(String str) {

		if (str == null || str.equals(""))
			return false;
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 校验字符串是否匹配正则表达式 add by xiaof
	 * */
	public static boolean isMatche(String checkStr, String regex) {

		if (checkStr == null || checkStr.equals(""))
			return false;

		Pattern pattern = Pattern.compile(regex);
		Matcher isMathe = pattern.matcher(checkStr);
		return isMathe.matches();
	}

	// 浮点数
	public static boolean isFloat(String num) {
		if (isNum(num))
			return true;
		String reg = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

		if (num == null || num.equals(""))
			return false;

		if (num.startsWith("-")) {
			return isMatche(num.substring(1), reg);
		}
		return isMatche(num, reg);
	}

	// 手机号码
	public static boolean isPhoneNo(String checkStr) {
		String regex = "^1[3|4|5|8][0-9]\\d{8}$";
		return isMatche(checkStr, regex);
	}

	public static boolean isMatcheIP(String ip) {
		String patterStr = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		return isMatche(ip, patterStr);
	}

	// 防止如包含误判. 如判断"1234,222"是否包含"123",如果直接用String.indexOf()方法,会认为包含
	// 我们在源字符串和子字符串的前后都加上分隔标志,变成判断",1234,222,"中是否包含",123,",这样就不会误判了

	/**
	 * 判断包含关系
	 * @param str 源字符串
	 * @param subStr 子字符串
	 * @param splitFlag 源字符串的分隔标志
	 * @return true 包含 false 不包含
	 */
	public static boolean isIndexOf(String str, String subStr, String splitFlag) {

		if (isEmpty(str)) {
			return false;
		}

		// 子字符串为空,认为包含
		if (isEmpty(subStr)) {
			return true;
		}

		if (null == splitFlag) {
			splitFlag = "";
		}

		String tmpStr = splitFlag + str + splitFlag;
		String tmpSubStr = splitFlag + subStr + splitFlag;

		return tmpStr.contains(tmpSubStr);

	}

	public static boolean containMapKey(Map<?, Object> map, Object key) {

		if (map == null || map.isEmpty()) {
			return false;
		}

		if (!map.containsKey(key)) {
			if (!map.containsKey(key.toString().toUpperCase())) {
				if (!map.containsKey(key.toString().toLowerCase())) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Object getObjectFromMap(Map<?, Object> map, Object key) {

		if ((map == null || map.isEmpty()) && !containMapKey(map, key)) {
			return "";
		}
		Object value = map.get(key);
		if (value == null) {
			value = map.get(key.toString().toUpperCase());
		}
		if (value == null) {
			value = map.get(key.toString().toLowerCase());
		}
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getStringFromMap(Map<String, Object> map, String key) {

		if ((map == null || map.isEmpty()) && !containMapKey(map, key)) {
			return "";
		}
		Object value = map.get(key);
		if (value == null) {
			value = map.get(key.toUpperCase());
		}
		if (value == null) {
			value = map.get(key.toLowerCase());
		}
		if (value == null) {
			value = "";
		}
		return value.toString();
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static Object getObjectFromMap(Map<String, Object> map, String key) {

		if (map == null || map.isEmpty() || !(map.containsKey(key))
				|| !(map.containsKey(key.toUpperCase()))
				|| !(map.containsKey(key.toLowerCase()))) {
			return "";
		}
		Object value = map.get(key);
		if (value == null) {
			value = map.get(key.toUpperCase());
		}
		if (value == null) {
			value = map.get(key.toLowerCase());
		}

		return value;
	}

	/**
	 * 克隆String
	 */
	public static Object cloneMySelf(Object object) {
		Object cloneObj = null;
		ObjectOutputStream oo = null;
		ObjectInputStream oi = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(object);
			ByteArrayInputStream in = new ByteArrayInputStream(
					out.toByteArray());
			oi = new ObjectInputStream(in);
			cloneObj = (Object) oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (oo != null) {
				try {
					oo.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if (oi != null) {
				try {
					oi.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return cloneObj;
	}

	// 把一个HashMap拼凑成key:val|..的格式
	public static String hashMapToString(HashMap sqlGetKeyVals) {
		StringBuffer data = new StringBuffer("");
		if (sqlGetKeyVals != null)
			for (Iterator it = sqlGetKeyVals.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry map = (Map.Entry) it.next();
				String key = (String) map.getKey();
				String val = (String) map.getValue();
				data.append(key);
				data.append(":");
				data.append(val);
				data.append("|");
			}
		return data.toString();
	}

	// 把一个key:val|..格式的String拼凑成HashMap
	public static HashMap stringToHashMap(String str) {
		if (str == null || str.equals(""))
			return null;
		String[] _hashmap = str.split("\\|");
		if (_hashmap == null || _hashmap.length == 0)
			return null;
		HashMap ret = new HashMap();
		for (int i = 0; i < _hashmap.length; i++) {
			String it = _hashmap[i];
			if (it == null || it.equals(""))
				continue;
			String[] _maps = it.split(":");
			if (_maps == null || _maps.length < 1)
				continue;
			String key = _maps[0];
			String val = "";
			if (_maps.length == 2)
				val = _maps[1];
			ret.put(key, val);
		}
		return ret;
	}

	/**
	 * 判断s 是否包含 value
	 * 
	 * @param s
	 * @param value
	 * @return boolean
	 */
	public static boolean isIncluded(String[] s, String value) {
		boolean result = false;
		if (s == null || s.length == 0) {
			return false;
		}
		for (int i = 0; i < s.length; i++) {
			if (value.equals(s[i])) {
				result = true;
				return result;
			}
		}
		return result;
	}

	/**
	 * 根据随机数长度生成随机数
	 * @param pwdLen
	 * @return
	 */
	public static String genRandowNum(int pwdLen) {

		if (pwdLen <= 0) {
			return "";
		}
		int maxNumber = 1;
		int minNumber = 0;
		int randomValue = 0;

		// 生成随机数能取的最大值+1, 如1000000
		for (int i = 0; i < pwdLen; i++) {
			maxNumber = maxNumber * 10;
		}

		// 生成随机数能取的最小数-1: 如 99999;
		if (pwdLen > 1) {
			minNumber = 1;
			for (int i = 0; i < pwdLen - 1; i++) {
				minNumber = minNumber * 10;
			}
			minNumber = minNumber - 1;
		}

		while (true) {
			randomValue = (int) Math.ceil(Math.random() * maxNumber);
			if ((randomValue > minNumber) && (randomValue < maxNumber)) {
				return randomValue + "";
			}

		}

	}

	/**
	 * 例如字符串为str='1,2,3,,4,3,2,5,,,2,7'经过处理后的结果为str='1,2,3,4,5,7'
	 * 
	 * @param orginalStr 数组
	 * @param seperator 分割符
	 * @return
	 */
	public static String removeRedundancyItem(String orginalStr,
			String seperator) {
		String retStr = "";
		int i = 0;
		if (MStringUtils.isEmpty(orginalStr) || MStringUtils.isEmpty(seperator)) {
			return retStr;
		}
		String str[] = orginalStr.split(seperator);
		for (String string : str) {
			if (MStringUtils.isNotEmpty(string)) {
				if (retStr.contains(string)) {
					continue;
				} else {
					if (i != 0)
						retStr = retStr + seperator;
					retStr = retStr + string;
				}
			}
			i++;
		}
		return retStr;
	}

	/**
	 * 替换str中的{:fieldName}成KeyVals 中的键值 add by xiaof 111012
	 */
	public static String convertKeyValsStr(Map KeyVals, String str) {
		// String nameEpx = "\\u007B:([a-zA-Z0-9_\\u002E\\u002A]+)\\}";

		if (str == null)
			return "";
		// 1.不存在需要转换则直接返回
		if (!str.contains("{"))
			return str;
		if (KeyVals == null || KeyVals.isEmpty())
			return str;

		for (Iterator it = KeyVals.entrySet().iterator(); it.hasNext();) {
			Map.Entry map = (Map.Entry) it.next();
			String key = (String) map.getKey();
			Object val = map.getValue();
			if (val instanceof String) {
				String _val = (String) val;
				String newFieldName = "{:" + key + "}";
				str = str.replace(newFieldName, _val);
			}
		}
		// 过滤无效{:标签}
		str = str.replaceAll("\\{\\:[\\w]+\\}", "");
		return str;
	}

	/**
	 * 从KeyVals 中提取键名生成 关键字数据String[] add by xiaof 111025
	 */
	public static String[] getMapToKey(Map KeyVals) {
		if (KeyVals == null || KeyVals.isEmpty()) {
			return new String[0];
		}

		List keyList = new ArrayList();
		for (Iterator it = KeyVals.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			if (key instanceof String) {
				String keyStr = (String) key;
				keyList.add(keyStr);
			}

		}

		return getListToKey(keyList);
	}

	public static String[] getListToKey(List<String> keyList) {
		if (keyList.isEmpty()) {
			return new String[0];
		}
		String[] keys = new String[keyList.size()];

		for (int i = 0; i < keyList.size(); i++) {
			keys[i] = keyList.get(i);
		}
		return keys;
	}

	/** 获取对象某属性值 **/
	public static Object getObjectValue(Object item, String key) {

		String[] levelKeys = key.split("\\.");
		Object cur_item = item;
		for (String levelKey : levelKeys) {
			if (cur_item == null) {
				return "";
			}
			cur_item = getObjectValueByFieldName(cur_item, levelKey);
		}
		return cur_item;
	}

	private static Object getObjectValueByFieldName(Object item, String key) {
		if (item == null)
			return "";

		if (item instanceof Map) {
			Map itemMap = (Map) item;
			return (String) itemMap.get(key);
		}

		Field field = getDeclaredField(item, key);
		try {
			if (field != null) {
				field.setAccessible(true);
				return field.get(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return "";
	}

	public static void setObjectValue(Object item, String key, Object valObj) {

		if (item == null)
			return;

		if (item instanceof Map) {
			Map itemMap = (Map) item;
			itemMap.put(key, valObj);
			return;
		}
		Field field = getDeclaredField(item, key);
		try {
			if (field != null) {
				field.setAccessible(true);
				field.set(item, valObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return 父类中的属性对象 xiaof 111122
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;

		Class<?> clazz = object.getClass();

		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		return null;
	}

	// 生成不重复的随机数
	public static int[] unRepeatNo(int maxNum, int size) {
		int num1[] = new int[size];
		Random random = new Random();

		for (int i = 0; i < size; i++) {
			num1[i] = Math.abs(random.nextInt()) % maxNum + 1;
			for (int j = 0; j < i; j++) {
				if (num1[j] == num1[i]) {// 有重复
					i--;
				}
			}
		}
		return num1;
	}

	// 取xml属性内容 add by xiaof
	public static String toXmlAttr(String src, String headTag, String EndTag, String xmlAttr) {
		String attr = "";
		String regex = "<" + headTag + " [^>]*" + xmlAttr
				+ "=\"([^\"]+?)\"[^>]*" + EndTag + ">";
		Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
				.matcher(src);
		try {
			while (matcher.find()) {
				attr = matcher.group(1);
				if (MStringUtils.isNotEmpty(attr)) {
					return attr;
				}
			}
		} catch (Exception e) {
		}

		if (MStringUtils.isEmpty(attr)) {
			attr = "";
		}
		return attr;
	}

	public static String isEmptyDefault(String... defValue) {
		if (defValue == null) {
			return "";
		}
		for (String val : defValue) {
			if (MStringUtils.isNotEmpty(val)) {
				return val;
			}
		}

		return "";
	}

	public static String safe(String val) {
		if (val == null) {
			return "";
		}
		return val;
	}

	public static String substr(String string, int len) {
		if (string == null)
			return "";
		return string.substring(0, Math.min(string.length(), len));
	}

	public static void printInfo(String obj) {
		printInfo(obj, null);
	}

	public static void printInfo(Object obj) {
		printInfo(obj, null);
	}

	public static void printInfo(Object obj, Throwable e) {
//		String errStack = ObjectUtils.getStackTraceAsString(e);
//		logger.info("\n---->printData:" + obj + "\n\n "
//				+ (null == e ? "" : "errStack:" + errStack));
	}

	public static String leftPad(String str, int size) {
		return MStringUtils.leftPad(str, size);
	}

	public static String leftPad(String str, int size, char padChar) {
		return MStringUtils.leftPad(str, size, padChar);
	}

	public static String rightPad(String str, Integer size, char padChar) {
		return MStringUtils.rightPad(str, size, padChar);
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	private static Pattern linePattern = Pattern.compile("\r|\n|\r\n");

	public static String[] splitLines(String lines) {
		return linePattern.split(lines);
	}

	public static Map splitLinesIntoMap(String lines, String delimiter) {
		if (lines == null)
			return null;
		String[] lineArr = lines.split("\r|\n|\r\n");
		return org.springframework.util.StringUtils
				.splitArrayElementsIntoProperties(lineArr, delimiter);
	}

	/**  
     * 分转换为元.  
     * @param fen 分
     * @return 元  
     */  
    public static String fen2Yuan(final String fen) {  
        String yuan = "";  
        final int MULTIPLIER = 100;  
        Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");  
        Matcher matcher = pattern.matcher(fen);  
        if (matcher.matches()) {  
            yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();  
        } else {  
            System.out.println("参数格式不正确!");  
        }  
        return yuan;  
    }  
  
    /**  
     * 元转换为分.  
     *   
     * @param yuan 元  
     * @return 分  
     */  
    public static String yuan2Fen(final String yuan) {  
        String fen = "";  
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?{1}");  
        Matcher matcher = pattern.matcher(yuan);  
        if (matcher.matches()) {  
            try {  
                NumberFormat format = NumberFormat.getInstance();  
                Number number = format.parse(yuan);  
                double temp = number.doubleValue() * 100.0;  
                // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012  
                format.setGroupingUsed(false);  
                // 设置返回数的小数部分所允许的最大位数  
                format.setMaximumFractionDigits(0);  
                fen = format.format(temp);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }else{  
            System.out.println("参数格式不正确!");  
        }  
        return fen;  
    }  
	
	/*public static void main(String[] args) {

		MStringUtils.replaceAll("erer(erer(", "(", " ( ");

		MStringUtils.replaceAll("erer(erer(erer", "(", " ( ");

		System.out.println(MStringUtils.fen2Yuan("1"));
	}*/
	//特殊字符替换方法XMJ
	//DATE:201506031622
	public static String repalceSpecialCharacter(String specialCharacter){
		String u = specialCharacter; 
		if(u!=null){
			u = u.replace ('＜','_'); 
			u = u.replace ('<','_'); 
			u = u.replace ('＞','_'); 
			u = u.replace ('>','_');
			u = u.replace ('"','_'); 
			u = u.replace ('\'','_'); 
			u = u.replace ('%','_'); 
			u = u.replace (';','_'); 
			u = u.replace ('(','_'); 
			u = u.replace (')','_'); 
			u = u.replace ('&','_'); 
			u = u.replace ('$','_'); 
			u = u.replace ('+','_'); 
		}
		return u;
	}
	
	/**
	 * 第一个字母转为大写
	 * @param str
	 * @return
	 */
	public static String convertToUpperCase(String str){
		if(MStringUtils.isEmpty(str)) return null;
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase()) ;
	}
}

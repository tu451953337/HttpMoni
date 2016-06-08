package com.xj.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	private static List<String> list = new ArrayList<String>();

	public static String sendPost(String requestUrl,
			Map<String, String> bgParamMap, String sessionId, String enc)
			throws Exception {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		StringBuffer params = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		// 组织请求参数
		Iterator it = bgParamMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry element = (Map.Entry) it.next();
			params.append(element.getKey());
			params.append("=");
			params.append(element.getValue());
			params.append("&");
		}
		if (params.length() > 0) {
			params.deleteCharAt(params.length() - 1);
		}
		URL realUrl = new URL(requestUrl);
		// 打开和URL之间的连接
		//
		httpURLConnection = (HttpURLConnection) realUrl.openConnection();
		// httpURLConnection.setRequestProperty("Content-Type",
		// "text/plain; charset=gb2312");
		// 设置通用的请求属性
		httpURLConnection.setRequestProperty("accept", "*/*");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(params.length()));
		if (sessionId != null) {
			httpURLConnection.setRequestProperty("Cookie", sessionId);
		}
		httpURLConnection.setRequestMethod("POST");
		// 发送POST请求必须设置如下两行
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		printWriter = new PrintWriter(httpURLConnection.getOutputStream());

		// 发送请求参数
		printWriter.write(params.toString());
		// flush输出流的缓冲
		printWriter.flush();
		// 根据ResponseCode判断连接是否成功
		int responseCode = httpURLConnection.getResponseCode();
		// 定义BufferedReader输入流来读取URL的ResponseData
		bufferedReader = new BufferedReader(new InputStreamReader(
				httpURLConnection.getInputStream(), enc));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			responseResult.append(line);
		}
		return responseResult.toString();
	}

	public static String sendPost(String requestUrl, String en)
			throws Exception {
		// String requestUrl = "http://localhost/info/infoget/columninfo.do";
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		// BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		StringBuffer params = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		// 组织请求参数
		URL realUrl = new URL(requestUrl);
		// 打开和URL之间的连接
		httpURLConnection = (HttpURLConnection) realUrl.openConnection();
		// 设置通用的请求属性
		httpURLConnection.setRequestProperty("accept", "*/*");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(params.length()));
		httpURLConnection.setRequestMethod("POST");
		// 发送POST请求必须设置如下两行
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		printWriter = new PrintWriter(httpURLConnection.getOutputStream());
		// 发送请求参数
		printWriter.write(params.toString());
		// flush输出流的缓冲
		printWriter.flush();
		// 根据ResponseCode判断连接是否成功
		int responseCode = httpURLConnection.getResponseCode();
		// 定义BufferedReader输入流来读取URL的ResponseData
		bufferedReader = new BufferedReader(new InputStreamReader(
				httpURLConnection.getInputStream(), en));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			responseResult.append(line);
		}
		return responseResult.toString();
	}

	public static String sendSSLPost(String requestUrl, String en,
			StringBuffer params, String cookie) throws Exception {
		// String requestUrl = "http://localhost/info/infoget/columninfo.do";
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		// BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();

		HttpURLConnection httpURLConnection = null;
		// 组织请求参数
		URL realUrl = new URL(requestUrl);
		// 打开和URL之间的连接
		httpURLConnection = (HttpURLConnection) realUrl.openConnection();
		// 设置通用的请求属性
		httpURLConnection.setRequestProperty("accept", "*/*");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(params.length()));
		httpURLConnection.setRequestProperty("Cookie", cookie);
		httpURLConnection.setRequestMethod("POST");
		/*
		 * if(requestUrl.contains("https")){ SslUtils.ignoreSsl(); }
		 */
		// 发送POST请求必须设置如下两行
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		printWriter = new PrintWriter(httpURLConnection.getOutputStream());
		// 发送请求参数
		printWriter.write(params.toString());
		// flush输出流的缓冲
		printWriter.flush();
		// 根据ResponseCode判断连接是否成功
		int responseCode = httpURLConnection.getResponseCode();
		// 定义BufferedReader输入流来读取URL的ResponseData
		bufferedReader = new BufferedReader(new InputStreamReader(
				httpURLConnection.getInputStream(), en));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			responseResult.append(line);
		}
		return responseResult.toString();
	}

	public static String senGet(String requestUrl, String en) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(false);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.connect();
		// 发送执行请求
		// 接收返回请求
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), en));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	/*
	 * public static void main(String[] args) { String a =
	 * "http://www.szcredit.com.cn/web/GSZJGSPT/QyxyDetail.aspx?rid=1b4306ac14684fdbbfd84b7c42ec196d"
	 * ; String data = sendPost(a); System.out.println(data); }
	 */
	/*
	 * public static void main(String[] args) throws Exception { String a =
	 * "http://www.whjg.gov.cn:8083/jgksyy/kcxx.do?method=listKcSykltj"; String
	 * data = sendSSLPost(a,"UTF-8"); int start =
	 * data.indexOf("<div id=\"con4\""); int end = data.indexOf(
	 * "<script language=\"JavaScript\" type=\"text/javascript\">vio_down()</script>"
	 * ); String temp = data.substring(start, end);
	 * 
	 * Pattern p = Pattern.compile("\\s*|\t|\r|\n"); Matcher m =
	 * p.matcher(temp); temp = m.replaceAll("");
	 * 
	 * System.out.println(temp);
	 * 
	 * int start_1 = temp.indexOf("<trclass=\"list_body_tr_"); int end_1 =
	 * temp.lastIndexOf("class=\"detail_head\"")-17;
	 * 
	 * 
	 * System.out.println(start + "#" + end); System.out.println(start_1 + "#" +
	 * end_1); //System.out.println(temp.substring(start_1, end_1));
	 * 
	 * temp = temp.substring(start_1, end_1); System.out.println(temp);
	 * 
	 * HttpUtils.indexStr(temp);
	 * 
	 * for(String str : list){ int s = str.lastIndexOf("<tdalign=\"center\">") +
	 * 18; int e = str.lastIndexOf("</tr>") - 5; String v = str.substring(s, e);
	 * int num = Integer.parseInt(v); System.out.println("当前剩余预约数：" + num);
	 * if(num > 0){ System.out.println("可进行预约啦！"); return; } }
	 * 
	 * }
	 */

	public static void indexStr(String str) {
		int startCount = 88;
		int endCount = 5;
		int start = str.indexOf("星期一") - startCount;
		int end = str.indexOf("</tr>", start) + endCount;

		if (start > -1 && end > -1) {
			String aa = str.substring(start, end);
			System.out.println(aa);
			list.add(aa);
			if (end < str.length()) {
				str = str.substring(end + 5, str.length());
				indexStr(str);
			}

		}
	}

	/*
	 * public static void indexStr(String str){ int start = str.indexOf("<tr");
	 * int end = str.indexOf("</tr>"); if(start != -1 && end != -1){ String aa =
	 * str.substring(start, end); System.out.println(aa); if(end <
	 * str.length()){ str = str.substring(end+5, str.length()); indexStr(str); }
	 * 
	 * } }
	 */
}

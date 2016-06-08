package com.xj.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Test58xueche extends Thread {
	public static void main(String[] args) throws Exception {

		new Test58xueche().start();
	}
	
	static int count = 0;
	static int IntervalTime = 3;
	@Override
	public void run() {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        System.out.println("===================监控启动， 每 " + IntervalTime + "分钟进行一次监控===================");
        try{
            while(true){
                count++;
                System.out.println("第" + count + "次监控：" + formatter.format(new Date()));
                query("2016-06-18");
    			query("2016-06-19");
                Thread.sleep(IntervalTime * 60 * 1000);//睡眠1000
            }
        }catch(Exception e){e.printStackTrace();}
		
	}


	static String execute(String url, String cookie) throws Exception {
		String requestUrl = url;
		URL url1 = new URL(
				requestUrl);
		HttpURLConnection connection1 = (HttpURLConnection) url1
				.openConnection();
		connection1.setRequestProperty("Cookie", cookie);// 给服务器送登录后的cookie
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				connection1.getInputStream()));

		StringBuilder sb = new StringBuilder();
		String line1 = br1.readLine();
		
		while (line1 != null) {
			sb.append(line1);

			line1 = br1.readLine();
			
		}
		return sb.toString();
	}
	
	static void query(String date) throws Exception {
		
		String cookie = "xc_city_id=414; xc_city_name=%E9%95%BF%E6%B2%99; Hm_lvt_b0b12c44c8e649c9c42e2f62686dc89a=1462786585,1463039859,1464141531,1464918493; city_location_time=1465278397849; xueche_uid=13683866385DABD52F224CAC9C2A0C27";
		String url = "http://api.xueche.com/subscribe/scheduleforapp?callback=jsonp3&coachid=357076659&"
				+ "subdate="+date+"&canmany=1&phonedes=13683866385DABD52F224CAC9C2A0C27&cityid=414&rdowirads=7692821";
		
		String data = execute(url, cookie);
		data = data.substring(data.indexOf("(")+1, data.lastIndexOf(")"));
		
		Map<String, Object> map = JSON.parseObject(data, Map.class);
		
		System.out.println(map);
		Map<String, Object> result = (Map<String, Object>) map.get("result");
		List<Map<String, Object>> schedulelist = (List<Map<String, Object>>) result.get("schedulelist");
		for(Map<String, Object> schedule : schedulelist) {
			Boolean b = (Boolean)schedule.get("enable");
			if(b) {
				Map<String, Object> param = (Map<String, Object>)schedule.get("param");
				String subdate = (String) param.get("subdate");
				subdate = subdate.replace(" ", "%20");
				url = "http://api.xueche.com/subscribe/order?callback=jsonp10&userphone=18503049916&remark=&subdate="+subdate+"&timelength=4&canmany=-1&phonedes=13683866385DABD52F224CAC9C2A0C27&cityid=414&rdowirads=4965746 HTTP/1.1";
				execute(url, cookie);
			}
		}
		
		date = date.replace(" ", "%20");
	}
}

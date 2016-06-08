package com.xj.moni;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xj.util.HttpUtils;

public class Moni58xueche extends Thread {
	//监控间隔时间(单位分钟)
    private static int IntervalTime = 3;
    
    //每次程序启动监控次数计数
    private int count = 0;
    
	public static void main(String[] args) {
		new Moni58xueche().start();
	}
	
	public void run(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        System.out.println("===================监控启动，当 每 " + IntervalTime + "分钟进行一次监控===================");
        try{
            while(true){
                count++;
                System.out.println("第" + count + "次监控：" + formatter.format(new Date()));
                startMonitor2();
                Thread.sleep(IntervalTime * 60 * 1000);//睡眠1000
            }
        }catch(InterruptedException e){e.printStackTrace();}
    }
	
	public static void startMonitor2(){
        try {
        	StringBuffer params = new StringBuffer();
        	String cookie = "xc_city_id=414; xc_city_name=%E9%95%BF%E6%B2%99; Hm_lvt_b0b12c44c8e649c9c42e2f62686dc89a=1462786585,1463039859,1464141531,1464918493; city_location_time=1465278397849; xueche_uid=13683866385DABD52F224CAC9C2A0C27";
        	String data = HttpUtils.sendSSLPost("http://m.xueche.com/yy/order/subscribe/scheduleforapp?callback=jsonp4&coachid=357076659&subdate=2016-06-10&canmany=1&phonedes=13683866385DABD52F224CAC9C2A0C27&cityid=414&rdowirads=86965940", "UTF-8", params, cookie);
            @SuppressWarnings("unchecked")
			List<Map<String, Object>> mapList = JSON.parseObject(data, List.class);
            String emailContent = "";
            for(Map<String, Object> map : mapList) {
            	System.out.println(map);
            }
            
            if(!"".equals(emailContent)){
//                sendEmail(emailContent);
                System.out.println("可以预约了！"+emailContent);
                emailContent = "";
            }else{
                System.out.println("无预约名额！");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

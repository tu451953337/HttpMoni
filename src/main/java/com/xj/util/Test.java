package com.xj.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;


public class Test extends Thread{
    //监控的url地址
    private static String URL = "http://www.hncsjj.gov.cn:8085/CsZzYy/cszzyy/queryPlanByIds.action";
    
    //抓取规则
    private static String START_RULE_1 = "<div id=\"con4\"";
    private static String END_RULE_1 = "<script language=\"JavaScript\" type=\"text/javascript\">vio_down()</script>";
    
    private static String START_RULE_2 = "<trclass=\"list_body_tr_";
    private static String END_RULE_2 = "class=\"detail_head\"";
    private static int END_RULE_2_COMPLETE_NUM = -17;
    
    private static String START_RULE_3 = "<tdalign=\"center\">";
    private static String END_RULE_3 = "</tr>";
    private static int START_RULE_3_COMPLETE_NUM = 18;
    private static int END_RULE_3_COMPLETE_NUM = -5;
    
    private static String START_RULE_4 = "<thscope=\"row\"align=\"center\">";
    private static String END_RULE_4 = "</th>";
    private static int START_RULE_4_COMPLETE_NUM = 29;
    
    //解析每行tr时，首尾补全字符长度
    private static int START_TR_COMPLETE_NUM = -88;
    private static int END_TR_COMPLETE_NUM = 5;
    
    //定义星期常量
    private static String MONDAY = "星期一";
    private static String TUESDAY = "星期二";
    private static String WEDNESDAY = "星期三";
    private static String THURSDAY = "星期四";
    private static String FRIDAY = "星期五";
    private static String SATURDAY = "星期六";
    private static String SUNDAY = "星期日";
    
    //邮件提醒内容
    private static String emailContent = "";
    
    //存放获取的数据集合
    private static List<String> dataList = new ArrayList<String>();
    
    //监控间隔时间(单位分钟)
    private static int IntervalTime = 3;
    
    //当前监控的星期
    private static String currentWeek = SUNDAY;
    
    //每次程序启动监控次数计数
    private int count = 0;
    
    public void run(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        System.out.println("===================监控启动，当前监控的约考时间为：" + currentWeek + "， 每 " + IntervalTime + "分钟进行一次监控===================");
        try{
            while(true){
                count++;
                System.out.println("第" + count + "次监控：" + formatter.format(new Date()));
                startMonitor2();
                Thread.sleep(IntervalTime * 60 * 1000);//睡眠1000
            }
        }catch(InterruptedException e){e.printStackTrace();}
    }
    
    public static void main(String[] args) {
        Test test = new Test();
        test.run();
    }
    
    public static void startMonitor(){
        try {
        	StringBuffer params = new StringBuffer();
            String htmlStr  = HttpUtils.sendSSLPost(URL ,"UTF-8", params, null);
            
            //进行第一道抓取规则处理
            int start_1 = htmlStr.indexOf(START_RULE_1);
            int end_1 = htmlStr.indexOf(END_RULE_1);
            htmlStr = htmlStr.substring(start_1, end_1);
            
            //对换行、空格等字符进行处理
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(htmlStr);
            htmlStr = m.replaceAll("");
            
            //进行第二道抓取规则处理
            int start_2 = htmlStr.indexOf(START_RULE_2);
            int end_2 = htmlStr.lastIndexOf(END_RULE_2) + END_RULE_2_COMPLETE_NUM;
            htmlStr = htmlStr.substring(start_2, end_2);
            
            //进行数据处理
            getData(currentWeek, htmlStr);
            
            for(String str : dataList){
                int start = str.lastIndexOf(START_RULE_3) + START_RULE_3_COMPLETE_NUM;
                int end = str.lastIndexOf(END_RULE_3) + END_RULE_3_COMPLETE_NUM;
                String val = str.substring(start, end);
                int num = Integer.parseInt(val);
                if(num > 0){
                    String date = str.substring(str.indexOf(START_RULE_4) + START_RULE_4_COMPLETE_NUM, str.indexOf(END_RULE_4));
                    emailContent += date + "  科目四有名额，可进行预约啦！当前剩余预约名额：" + num + "个。<br/>";
                    System.out.println("content:" + str);
                    //System.out.println(emailContent);
                    //return;
                }
            }
            dataList.clear();
            
            
            if(emailContent != ""){
                sendEmail(emailContent);
                System.out.println(emailContent);
                emailContent = "";
            }else{
                System.out.println("无预约名额！");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void startMonitor2(){
        try {
        	StringBuffer params = new StringBuffer();
        	String cookie = "JSESSIONID=4E6EE5471F6A390761A8A2C2BEE7183D.jvm4; CNZZDATA1000244059=2064336502-1465173561-%7C1465173561";
        	String data = HttpUtils.sendSSLPost("http://www.hncsjj.gov.cn:8085/CsZzYy/cszzyy/queryPlan.action", "UTF-8", params, cookie);
        	@SuppressWarnings("unchecked")
			List<Map<String, Object>> dataList = JSON.parseObject(data, List.class);
        	String planIds = "";
        	for(Map<String, Object> map : dataList) {
            	System.out.println(map);
            	if(map.get("planName").toString().indexOf("左家垅科目二考场") > -1 ) {
            		//[{"planIds":"67080057","planName":"星沙科目二考场"},{"planIds":"67080055","planName":"青竹湖科目二考场"},{"planIds":"67080230@67080229@67080235@67080234","planName":"左家垅科目二考场"}]
            		planIds = map.get("planIds").toString();
            		break;
            	}
            }
        	
        	
    		params.append("planIds="+planIds);
            String htmlStr  = HttpUtils.sendSSLPost(URL ,"UTF-8", params, cookie);
            @SuppressWarnings("unchecked")
			List<Map<String, Object>> mapList = JSON.parseObject(htmlStr, List.class);
            String emailContent = "";
            for(Map<String, Object> map : mapList) {
            	System.out.println(map);
            	if(map.get("kscx").toString().indexOf("新捷达") > -1 ) {
            		emailContent += map.toString();
            	}
            }
            
            if(!"".equals(emailContent)){
                sendEmail(emailContent);
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
    
    
    /** 
     * 获得处理后的数据 
    */
    public static void getData(String keyWord, String str){
        int start = str.indexOf(keyWord) + START_TR_COMPLETE_NUM;
        int end = str.indexOf("</tr>", start) + END_TR_COMPLETE_NUM;
        if(start > -1 && end > -1){
            String temp = str.substring(start, end);
            dataList.add(temp);
            if(end < str.length()){
                str = str.substring(end+5, str.length());
                getData(keyWord, str);
            }
        }
    }
    
    
    /** 
     * 发送邮件
    */
    public static void sendEmail(String content) throws Exception{
        String account = "tu451953337@sina.com";
        String account_name = "驾照预约提醒";
        String host = "smtp.sina.com";
        String password = "kurrent.cn";
        String mailFrom = account;
        String toFrom = "451953337@qq.com";
        //String toFrom = "97573122@qq.com";
        
        MailUtil mailUtil = new MailUtil(host,toFrom,mailFrom,account_name,"科目二加考预约提醒邮件",account,password);
        mailUtil.sendHtmlMail(content, null, true, false);
    }

}

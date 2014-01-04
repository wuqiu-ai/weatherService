package com.feixing.message;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class Weather {

	private static Logger log = Logger.getLogger(Weather.class);
    private Map<String,String> areaMap = new HashMap<String, String>();
    private Map<String,String> weatherMap = new HashMap<String, String>();
    private String aliciaWeatherDate = "";//alicia天气信息
    
    public Weather(){
    	//地区初始化
    	areaMap.put("1", "http://m.weather.com.cn/data/101210101.html");//杭州天气URL
    	areaMap.put("2", "http://m.weather.com.cn/data/101210501.html");//绍兴天气URL
    	//获取天气
    	weatherMap.clear();
    	weatherMap.put("1", Weather.getWeatherByUrl(areaMap.get("1")));
    	weatherMap.put("2", Weather.getWeatherByUrl(areaMap.get("2")));
    	aliciaWeatherDate = getAliciaWeather();
    	log.info("获取天气成功");
    }
    
    /**
     * 
    	 * 
    	 *@方法名称:获取天气信息
    	 *@输    入:
    	 *@输    出：
    	 *@作    者:庞培杰
    	 *@创建日期:2013-4-23
    	 *@方法描述:
    	 * @param 
    	 * @return String
     */
    public static String getWeatherByUrl(String url){
    	String result = null;
		String content = getURLContent(url,"utf-8");
		if(!"".equals(content)){
			JSONObject jsonObject = JSONObject.fromObject( content );
			JSONObject obj = JSONObject.fromObject( jsonObject.get("weatherinfo") );
			String diqu = obj.get("city").toString();//地区：杭州
			String shijian = obj.get("date_y").toString();//时间
			String weath = obj.get("weather1").toString();//今天天气：阵雨转中雨
			String wendu = obj.get("temp1").toString();//今天温度：29℃~23℃
			String fenshu = obj.get("wind1").toString();//今天风速描述
			String chuanyi = obj.get("index_d").toString();//今天穿衣穿衣指数
			String weath1 = obj.get("weather2").toString();//明天天气：阵雨转中雨
			String wendu1 = obj.get("temp2").toString();//明天温度：29℃~23℃
			String fenshu1 = obj.get("wind2").toString();//明天风速描述
			result = shijian.substring(5, shijian.length()) +diqu +"，天气：" + weath +"，温度："+wendu+"，风速："+fenshu+"，穿衣指数："+chuanyi ;//今天天气
			result += "预计明天天气："+weath1+",温度："+wendu1+",风速："+fenshu1 ;//预计明天天气
		}
		return result;
    }
    
    /**
     * 获取alicia天气信息
     * @param url
     * @return
     */
    public static String getAliciaWeather(){
    	String url = "http://m.weather.com.cn/data/101210101.html";
    	String result = null;
		String content = getURLContent(url,"utf-8");
		if(!"".equals(content)){
			JSONObject jsonObject = JSONObject.fromObject( content );
			JSONObject obj = JSONObject.fromObject( jsonObject.get("weatherinfo") );
			String diqu = obj.get("city").toString();//地区：杭州
			String shijian = obj.get("date_y").toString();//时间
			String weath1 = obj.get("weather2").toString();//明天天气：阵雨转中雨
			String wendu1 = obj.get("temp2").toString();//明天温度：29℃~23℃
			String fenshu1 = obj.get("wind2").toString();//明天风速描述
			result = "今天是" + shijian + "-" +diqu  ;//今天天气
			result += ",预计明天天气："+weath1+",温度："+wendu1+",风速："+fenshu1 +" 穿衣指数："+obj.get("index48_d").toString()+" 紫外线强度："+obj.get("index48_uv").toString();//预计明天天气
		}
		return result;
    }
	
	/**
	 * 
	 *@方法名称:
	 *@输    入:
	 *@输    出：
	 *@作    者:庞培杰
	 *@创建日期:2013-4-21
	 *@方法描述:
	 * @param 
	 * @return void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Weather weather = new Weather();
		System.out.print("---");
	}

	/**
	 * 
		 * 
		 *@方法名称:
		 *@输    入:
		 *@输    出：
		 *@作    者:庞培杰
		 *@创建日期:2013-3-27
		 *@方法描述:抓取页面信息，用于天气信息
		 * @param 
		 * @return String
	 */
	public static String getURLContent(String url, String encoding) {
		  StringBuffer content = new StringBuffer();
		  try {
			   URL u = new URL(url);
			   InputStream in = new BufferedInputStream(u.openStream());
			   InputStreamReader theHTML = new InputStreamReader(in, encoding);
			   int c;
			   while ((c = theHTML.read()) != -1) {
				   content.append((char) c);
			   }
		  }catch (Exception e) {
			   log.error("获取天气失败，"+e.getMessage());
		  }
		  return content.toString();
	}

	public Map<String, String> getWeatherMap() {
		return weatherMap;
	}

	public void setWeatherMap(Map<String, String> weatherMap) {
		this.weatherMap = weatherMap;
	}

	public String getAliciaWeatherDate() {
		return aliciaWeatherDate;
	}

	public void setAliciaWeatherDate(String aliciaWeatherDate) {
		this.aliciaWeatherDate = aliciaWeatherDate;
	}
	
	
}

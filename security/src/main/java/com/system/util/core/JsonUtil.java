package com.system.util.core;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonUtil {
	
	/**
	 * fastjson 改造，增加如果为null的话，就变成""
	 * @param object
	 * @param features
	 * @return
	 */
	public static String toJSONString(Object object, 
			SerializerFeature ...features) {
		SerializeWriter out = new SerializeWriter();
		String s;
		JSONSerializer serializer = new JSONSerializer(out);
		SerializerFeature arr$[] = features;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			SerializerFeature feature = arr$[i$];
			serializer.config(feature, true);
		}

		serializer.getValueFilters().add(new ValueFilter() {
			public Object process(Object obj, String s, Object value) {
				if(null!=value) {
					if(value instanceof java.util.Date) {
						return String.format("%1$tF %1tT", value);
					}
					return value;
				}else {
					return "";
				}
			}
		});
		serializer.write(object);
		s = out.toString();
		out.close();
		return s;
	}

	 /**  
     * @param object  
     *             任意对象  
     * @return java.lang.String  
     */     
   public static String objectToJson(Object object) {      
        StringBuilder json = new StringBuilder();      
       if (object == null) {      
            json.append("\"\"");      
        } else if (object instanceof String || object instanceof Integer) {    
            json.append("\"").append(object.toString()).append("\"");     
        } else {      
            json.append(beanToJson(object));      
        }      
       return json.toString();      
    }      
    
   /**  
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串  
     *  
     * @param bean  
     *             bean对象  
     * @return String  
     */     
   public static String beanToJson(Object bean) {      
        StringBuilder json = new StringBuilder();      
        json.append("{");      
        PropertyDescriptor[] props = null;      
       try {      
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)      
                    .getPropertyDescriptors();      
        } catch (IntrospectionException e) {      
        }      
       if (props != null) {      
           for (int i = 0; i < props.length; i++) {      
               try {     
                    String name = objectToJson(props[i].getName());      
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));     
                    json.append(name);      
                    json.append(":");      
                    json.append(value);      
                    json.append(",");     
                } catch (Exception e) {      
                }      
            }      
            json.setCharAt(json.length() - 1, '}');      
        } else {      
            json.append("}");      
        }      
       return json.toString();      
    }      
    
   /**  
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串  
     *  
     * @param list  
     *             列表对象  
     * @return java.lang.String  
     */     
   public static String listToJson(List<?> list) {      
        StringBuilder json = new StringBuilder();      
        json.append("[");      
       if (list != null && list.size() > 0) {      
           for (Object obj : list) {      
                json.append(objectToJson(obj));      
                json.append(",");      
            }      
            json.setCharAt(json.length() - 1, ']');      
        } else {      
            json.append("]");      
        }      
       return json.toString();      
    }   

	
}

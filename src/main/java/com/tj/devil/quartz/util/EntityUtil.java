package com.tj.devil.quartz.util;

import com.jfinal.plugin.activerecord.Record;

/**
 * 根据自定义属性设置返回参数个数
 * @author jeanzhang
 *
 */
public class EntityUtil {
	/**
	 * 根据字段设置值
	 * @param <E>
	 * @param entity 实体类
	 * @param property 参数名
	 * @return Obj
	 */
	public static <E> Object getTer(E entity, String property){
		if(null== entity || isEmpty(property)){
			return "";
		}
		Object entityTemp = entity ;
		for (String propertyArr : property.trim().split("\\.")) {
			entityTemp = invokeReturn(entityTemp, "get"+toUpperFirst(propertyArr)) ;
			if(null==entityTemp){
				entityTemp= "";
			}
			if("payType".equals(propertyArr)){
				if("0".equals(entityTemp.toString())){
					entityTemp = "微信支付";
				}else if("1".equals(entityTemp.toString())){
					entityTemp = "支付宝支付";
				}else if("2".equals(entityTemp.toString())){
					entityTemp = "银联支付";
				}else if("3".equals(entityTemp.toString())){
					entityTemp = "线下支付";
				}
			}
		}
		return entityTemp;
	}

	public static Object getRecord(Record entity, String property){
		if(null== entity || isEmpty(property)){
			return "";
		}
		return entity.get(property);
	}

	/**
	 * 获取对应字段的值
	 * @param <E>
	 * @param entity
	 * @param methodName
	 * @return
	 */
	private static <E> Object invokeReturn(E entity,String methodName){
		return invoke(entity, methodName, null, null) ;
	}
	@SuppressWarnings("unchecked")
	private static <E> Object invoke(E entity,String methodName,Class[] valueTypes,Object[] values){
		try{
			return entity.getClass().getMethod(methodName, null==valueTypes?new Class[]{}:valueTypes).invoke(entity, null==values?new Object[]{}:values);
		}catch(Exception e){
			return null ;
		}
	}
	/**
	 * 修改首字母为大写
	 * @param str
	 * @return
	 */
	private static String toUpperFirst(String str){
		return isEmpty(str)?str:str.replaceFirst(str.substring(0,1), str.substring(0,1).toUpperCase()) ;
	}
	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	private static boolean isEmpty(String str){
		return null==str||"".equals(str.trim()) ;
	}


}

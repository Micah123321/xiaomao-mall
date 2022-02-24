package com.xiaomao6.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Micah
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * 设置数据
	 * @param data 数据
	 * @return 本身
	 */
	public R setData(Object data){
		put("data",data);
		return this;
	}

	/**
	 * 获取数据
	 * @param typeReference 要被转换的类型
	 * @param <T> 想要拿到的类型
	 * @return 转换好的数据
	 */
	public <T> T getData(TypeReference<T> typeReference){
		Object data = get("data");
		String s = JSON.toJSONString(data);
		return JSON.parseObject(s, typeReference);
	}

	public R() {
		put("code", 0);
		put("msg", "success");
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	/**
	 * 检测对应b,然后返回对应的R
	 * @param b bool
	 * @param code 错误码
	 * @param msg 错误信息
	 * @return 封装好的R
	 */
	public static R check(boolean b, Integer code,String msg){
		return b?R.ok():R.error(code,msg);
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode(){
		return (Integer) super.get("code");
	}

	public Boolean isSuccess(){
		return getCode()==0;
	}
}

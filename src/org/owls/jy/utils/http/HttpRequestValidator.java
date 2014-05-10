package org.owls.jy.utils.http;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class is not applied singleton pattern. Can be created by new operation.
 * @author juneyoungoh
 *
 */
public class HttpRequestValidator {
	private Map<String, String> storage;
	private String tmpStr="";
	private HttpServletRequest req;
	
	//====== 생성자 
	
	public HttpRequestValidator(HttpServletRequest req) {
		this.req = req;
		storage = new LinkedHashMap<String, String>();
	}
	
	//====== 파라미터 추가 
	
	/**
	 * add list of parameters
	 * @param strings - list of parameter names
	 * @return
	 */
	public HttpRequestValidator add(String ... params) {
		for(String str : params){
			storage.put(str, req.getParameter(str));
		}
		return this;
	}
	
	/**
	 * add a parameter
	 * @param param
	 * @return
	 */
	public HttpRequestValidator add(String param) {
		this.tmpStr = param;
		storage.put(param, req.getParameter(param));
		return this;
	}
	
	//====== 현재 등록된 전체 파라미터를 맵으로 올림 
	
	/**
	 * register all parameters. null value can be acceptable 
	 * @param defaultValue
	 */
	public void setDefaultForAll(String defaultValue) {
		if(storage.size() > 0) {
			for(String paramName : storage.keySet()) {
				storage.put(paramName, 
						(req.getParameter(paramName) == null) ? 
								defaultValue : req.getParameter(paramName));
			}
		}
	}
	
	//====== 복수 파라미터의 기본값 설정 
	/**
	 * set multiple default values for parameters
	 * @param defaults
	 */
	public void setDefaultMultiple(String ... defaults) {
		if(storage.size() == defaults.length){
			int i = 0;
			for(String param : storage.keySet()) {
				storage.put(param, 
						(storage.get(param) == null || storage.get(param).length() < 1) ? 
								defaults[i] : storage.get(param));
				i++;
			}
		}else{
			System.err.println("paramters and defaults are unmatched.");
		}
	}
	
	//====== 개별 등록된 파라미터의 기본값을 컨트롤 
	/**
	 * set a defult value.
	 * ** this method should be right after add()
	 * @param defaultStr
	 * @return
	 */
	public HttpRequestValidator setDefault(String defaultStr) {
		storage.put(tmpStr, 
				(req.getParameter(tmpStr) == null) ? 
						defaultStr : req.getParameter(tmpStr));
		return this;
	}
	 
	/**
	 * set a defult value.
	 * ** this method no need to be right after add()
	 * @param defaultStr
	 * @return
	 */
	public HttpRequestValidator setDefault(String key, String defaultStr) {
		storage.put(key, 
				(req.getParameter(key) == null) ? 
						defaultStr : req.getParameter(key));
		return this;
	}
	
	//====== 등록된 파라미터 중 null 값이 있는지 확인 
	/**
	 * Check if has 'null' among registered parameters 
	 * @return
	 */
	public boolean hasNull () {
		for(String param : storage.keySet()){
			if(storage.get(param) == null)
				return true;
		}
		return false;
	}
	
	//====== 등록된 파라미터 중 공백 문자열이 있는지 확인 
	/**
	 * Check if has empty String among registered parameters 
	 * @return
	 */
	public boolean hasEmptyStr() {
		for(String param : storage.keySet()){
			if(storage.get(param).equals(""))
				return true;
		}
		return false;
	}
	
	//====== null 이나 공백 문자열 모두 확인 
	public boolean hasNullOrEmptyStr() {
		for(String param : storage.keySet()){
			if(storage.get(param) == null || storage.get(param).equals(""))
				return true;
		}
		return false;
	}
	
	/**
	 * Get changed value form validator
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return storage.get(key);
	}
	
	/**
	 * Get All elements registered
	 * @return
	 */
	public Map<String, String> get() {
		return storage;
	}
};
package com.customer.utils;

public class JsonResult {
	public static final Integer CODE_OK = 1;             //成功
	public static final Integer CODE_FAILURE = 0;        //失败
	
	private int code=1;
	private String message="success";
	private Object data;
	private String remark="" ;
	
	public JsonResult(){
		super();
	}
	
	public JsonResult(int code,String message,Object data,String remark){	
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.remark = remark;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}



	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public void setData(Object data) {
//		//过滤json串属性值为null的属性
//		if (data instanceof JSONObject){
//			data = filterNull((JSONObject)data);
//		}else if (data instanceof JSONArray) {
//			JSONArray array = (JSONArray)data;
//			for (int i = 0; i < array.size(); i++) {
//				array.set(i,filterNull(array.getJSONObject(i)));
//			}
//		}
//		this.data = data;
//	}
//	public static JSONObject filterNull(JSONObject memberJo){
//		for (Iterator iterator = memberJo.keys(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			if ("null".equals(memberJo.get(key).toString())) {
//				memberJo.remove(key);
//				filterNull(memberJo);
//				break;
//			}else if (memberJo.get(key) instanceof JSONObject) {
//				filterNull(memberJo.getJSONObject(key));
//			}else if (memberJo.get(key) instanceof JSONArray) {
//				JSONArray array = memberJo.getJSONArray(key);
//				for (int i = 0; i < memberJo.getJSONArray(key).size(); i++) {
//					array.set(i,filterNull(array.getJSONObject(i)));
//				}
//			}
//		}
//		return memberJo;
//	}
}

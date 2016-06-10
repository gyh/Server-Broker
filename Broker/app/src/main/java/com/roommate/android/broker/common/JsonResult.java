package com.roommate.android.broker.common;

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
}

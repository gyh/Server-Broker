package com.customer.model;

import java.util.Date;

public class User {
    private Long id;

    private String mobile;

    private String password;

  //  private Date regTime;

    private Integer authorLevel;

    private Integer memberLevel;

    private String nickname;
    
    private String username;
    
    private String address;
    
    
    

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }



   /* public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }*/

    public Integer getAuthorLevel() {
        return authorLevel;
    }

    public void setAuthorLevel(Integer authorLevel) {
        this.authorLevel = authorLevel;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }
}
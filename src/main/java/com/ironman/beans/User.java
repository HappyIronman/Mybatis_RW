package com.ironman.beans;

import java.util.Date;

/**
 * @Author: ZJW
 * @Description:
 * @Date: Created in 11:35 2017/11/28 0028
 **/
public class User {
	private Long id;
	private String username;
	private String psw;
	private String addr;
	private int age;
	private Date createDate=new Date();

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPsw(){
		return psw;
	}

	public void setPsw(String psw){
		this.psw = psw;
	}

	public String getAddr(){
		return addr;
	}

	public void setAddr(String addr){
		this.addr = addr;
	}

	public int getAge(){
		return age;
	}

	public void setAge(int age){
		this.age = age;
	}

	public Date getCreateDate(){
		return createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Override
	public String toString(){
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", psw='" + psw + '\'' +
				", addr='" + addr + '\'' +
				", age=" + age +
				", createDate=" + createDate +
				'}';
	}
}

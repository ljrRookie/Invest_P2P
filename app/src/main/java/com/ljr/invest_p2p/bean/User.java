package com.ljr.invest_p2p.bean;

public class User {
	private int id;// 编号
	private String name;// 姓名
	private String password;// 密码
	private String phone;// 手机号
	private String imageurl;// 头像地址
	private boolean iscredit;// 是否公安部认证

	public User() {
		super();
	}

	public User(int id, String name, String password, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}

	public User(int id, String name, String password, String phone,
			String imageurl) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.imageurl = imageurl;
	}

	public User(int id, String name, String password, String phone,
			String imageurl, boolean iscredit) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.imageurl = imageurl;
		this.iscredit = iscredit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public boolean isCredit() {
		return iscredit;
	}

	public void setCredit(boolean iscredit) {
		this.iscredit = iscredit;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", phone=" + phone + ", imageurl=" + imageurl + ", iscredit="
				+ iscredit + "]";
	}
	
}

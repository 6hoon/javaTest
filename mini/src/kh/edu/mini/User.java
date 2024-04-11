package kh.edu.mini;

import java.io.Serializable;

public class User implements Serializable {

	private static int uCount = 1; // 관리자를 제외한 유저 생성될 때 증가 -> 현재 유저수

	private String id;
	private String pw;
	private String phone;
	private String address;
	private Bag bag;
	private int count;
	
	

	public User() {
		super();
	}

	public User(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
		this.count = 0;
	}

	public static void setuCount(int uCount) {
		User.uCount = uCount;
	}

	public User(String id, String pw, String phone, String address) {
		super();
		this.id = id;
		this.pw = pw;
		this.phone = phone;
		this.address = address;
		this.bag = new Bag();
		this.count = uCount++;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}

	public static int getuCount() {
		return uCount;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		if (this.count == -1) {
			return "관리자 [ ID=" + id + ", PW= " + pw + " ]";
		} else {

			return "User [ ID=" + id + ", PW=" + pw + ", 번호:" + phone + ", 주소:" + address + ", 유저식별번호: " + count + " ]";

		}
	}
}
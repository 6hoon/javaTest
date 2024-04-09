package kh.edu.mini;

import java.io.Serializable;

public class User implements Serializable{
	private static int uCount = 0;
	
	private String id;
	private String pw;
	private String phone;
	private String address;
	private Bag bag;
	private int count;
	
	
	public User(String id, String pw, int count) {
		super();
		this.id = id;
		this.pw = pw;
		this.count = -1;
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
		this.bag = null;
		this.count = uCount;
		uCount++;
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
		if(this.id.equals("admin")) {
			return "관리자";
		}
		return "User [id=" + id + ", pw=" + pw + ", phone=" + phone + ", address=" + address + ", bag=" + bag
				+ ", count=" + count + "]";
	}

		
	
}
package kh.edu.mini;

import java.io.Serializable;

public class Cloth implements Serializable{

	private static int sCount = 0;
	private static int pCount = 0;
	private String type;
	private String code;
	private String name;
	private int price;
	
	public Cloth(String type, String name, int price) {
		super();
		this.type = type;
		if(type.toLowerCase().equals("pants")) {
			this.code = "P"+(pCount+1);
			pCount++;
		}else {
			this.code = "S"+(sCount+1);
			sCount++;
		}
		this.name = name;
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public static int getsCount() {
		return sCount;
	}

	public static int getpCount() {
		return pCount;
	}

	@Override
	public String toString() {
		return "Cloth [type=" + type + ", code=" + code + ", name=" + name + ", price=" + price + "]";
	}
	
	
}
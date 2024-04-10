package kh.edu.mini;

import java.io.Serializable;

public class Cloth implements Serializable {
	
	private static int[] coList = {0,0};	// 옷이 생성될 때 증가 // 현재까지 생성된 [ 바지 / 셔츠]
	private String type;
	private String code;
	private String name;
	private int stock;	// 재고 : 생성 시 1로 초기화
	private int price;

	
	public Cloth() {
	}

	public Cloth(String type, String name, int price) {
		if (type.toLowerCase().equals("p")) {
			this.type = "pants";
			this.code = "P" + (coList[0]++);
		} else if(type.toLowerCase().equals("s")) {
			this.type = "shirts";
			this.code = "S" + (coList[1]++);
		} else {
			System.out.println("p / s 로 입력바랍니다");
			return;
		}
		this.name = name;
		this.stock = 1;
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int[] getCoList() {
		return coList;
	}

	public static void setCoList(int[] coList) {
		Cloth.coList = coList;
	}

	@Override
	public String toString() {
		if (this.type.equals("pants")) {
			return "바지: " + code + ",상품 이름: " + name + ", 가격: " + price + ", 남은 수량: " + stock;
		} else {
			return "셔츠: " + code + ",상품 이름: " + name + ", 가격: " + price + ", 남은 수량: " + stock;
		}
	}

	public String clothToClient() {
		if (this.type.equals("pants")) {
			return "바지: " + code + ",상품 이름: " + name + ", 가격: " + price;
		} else {
			return "셔츠: " + code + ",상품 이름: " + name + ", 가격: " + price;
		}
	}
}
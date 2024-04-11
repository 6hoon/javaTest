package kh.edu.mini;

import java.io.Serializable;
import java.util.ArrayList;

public class Content implements Serializable {
	private Cloth cloth;
	private int quantity;
	private int price;

	public Content() {
		super();
		this.cloth = new Cloth();
		this.quantity = 0;
		this.price = 0;
	}

	public Content(String code, int quantity) {
		super();
		this.cloth = DressShop.cList.stream().filter(t -> t.getCode().equals(code)).toList().get(0);
		this.quantity = quantity;
		DressShop.cList.stream().filter(t-> t.getCode().equals(code)).forEach(t->this.price=t.getPrice()*quantity);
	}
	
	public void calPrice() {
		this.price = this.cloth.getPrice()*this.quantity;
	}

	public Cloth getCloth() {
		return cloth;
	}

	public void setCloth(Cloth cloth) {
		this.cloth = cloth;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return cloth.getName() + ", 수량: " + quantity + ", 합계: " + price;
	}
	
	
}

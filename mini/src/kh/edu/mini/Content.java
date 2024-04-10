package kh.edu.mini;

import java.io.Serializable;
import java.util.ArrayList;

public class Content implements Serializable {
	private Cloth cloth;
	private int quantity;

	public Content() {
		super();
	}

	public Content(String code, int quantity) {
		super();
		this.cloth = DressShop.cList.stream().filter(t -> t.getCode().equals(code)).toList().get(0);
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return cloth.getName() + ", 수량" + quantity;
	}
	
	
}

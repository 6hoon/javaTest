package kh.edu.mini;

import java.io.Serializable;
import java.util.ArrayList;

public class Bag implements Serializable {

	private ArrayList<Content> content;
	private int totalPrice;

	public Bag() {
		super();
	}

	public ArrayList<Content> getContent() {
		return content;
	}

	public void setContent(ArrayList<Content> content) {
		this.content = content;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice() {
		for (int i = 0; i < content.size(); i++) {
			int j = i;
			DressShop.cList.stream().filter(t -> t.getCode().equals(content.get(j).getCloth().getCode()))
					.map(t -> totalPrice += (t.getPrice() * content.get(j).getQuantity()));
		}
	}

	@Override
	public String toString() {
		return "Bag [content=" + content + ", totalPrice=" + totalPrice + "]";
	}


}
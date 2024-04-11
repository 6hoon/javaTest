package kh.edu.mini;

import java.io.Serializable;
import java.util.ArrayList;

public class Bag implements Serializable {

	private ArrayList<Content> content;
	private int totalPrice;

	public Bag() {
		super();
		content = new ArrayList<Content>();
		this.content.add(new Content());
		this.totalPrice = 0;
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

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void calTotalPrice() {
		totalPrice = 0;
		content.stream().forEach(t -> totalPrice += t.getPrice());
	}

	@Override
	public String toString() {
		content.stream().forEach(t -> System.out.println(t));
		return "장바구니 총 합계: " + totalPrice;
	}

}
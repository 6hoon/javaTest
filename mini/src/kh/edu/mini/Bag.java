package kh.edu.mini;

import java.util.ArrayList;

public class Bag {
	private ArrayList<Cloth> clothList;
	private ArrayList<Integer> quantity;
	private int totalPrice;
	
	public ArrayList<Cloth> getClothList() {
		return clothList;
	}
	public void setClothList(ArrayList<Cloth> clothList) {
		this.clothList = clothList;
	}
	public ArrayList<Integer> getQuantity() {
		return quantity;
	}
	public void setQuantity(ArrayList<Integer> quantity) {
		this.quantity = quantity;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
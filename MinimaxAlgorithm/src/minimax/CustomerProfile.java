package minimax;

import java.util.ArrayList;

public class CustomerProfile {
	
	private ArrayList<Attribute> scoreAttributes;
	private int number_customer;

	public CustomerProfile(ArrayList<Attribute> scoreAttributes) {
		super();
		this.scoreAttributes = scoreAttributes;
	}

	public ArrayList<Attribute> getScoreAttributes() {
		return scoreAttributes;
	}

	public void setScoreAttributes(ArrayList<Attribute> scoreAttributes) {
		this.scoreAttributes = scoreAttributes;
	}
	
	public int getNumberCustomer(){
		return number_customer;
	}

}
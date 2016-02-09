package minimax;

import java.util.ArrayList;

public class Attribute {
	
	public String name;
	public int MIN;
	public int MAX;
	private ArrayList<Boolean> availableValues;
	private ArrayList<Integer> scoreValues;
	
	public Attribute(String name, int mIN, int mAX) {
		super();
		this.name = name;
		MIN = mIN;
		MAX = mAX;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMIN() {
		return MIN;
	}

	public void setMIN(int mIN) {
		MIN = mIN;
	}

	public int getMAX() {
		return MAX;
	}
	public void setMAX(int mAX) {
		MAX = mAX;
	}

	public ArrayList<Boolean> getAvailableValues() {
		return availableValues;
	}

	public void setAvailableValues(ArrayList<Boolean> values) {
		this.availableValues = values;
	}

	public ArrayList<Integer> getScoreValues() {
		return scoreValues;
	}

	public void setScoreValues(ArrayList<Integer> scoreValues) {
		this.scoreValues = scoreValues;
	}

}

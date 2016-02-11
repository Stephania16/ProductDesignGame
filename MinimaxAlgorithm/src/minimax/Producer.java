package minimax;

import java.util.ArrayList;

public class Producer {
	
	public ArrayList<Attribute> AvailableAttribute;
	public ArrayList<CustomerProfile> CustomerGathered;
	public Product product;	

	public Producer() {
		
	}

	public Producer(ArrayList<Attribute> availableAttribute, Product product, ArrayList<CustomerProfile> customerGathered) {
		super();
		AvailableAttribute = availableAttribute;
		this.product = product;
		CustomerGathered = customerGathered; 
	}

	public ArrayList<Attribute> getAvailableAttribute() {
		return AvailableAttribute;
	}

	public void setAvailableAttribute(ArrayList<Attribute> availableAttribute) {
		AvailableAttribute = availableAttribute;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public ArrayList<CustomerProfile> getCustomerGathered() {
		return CustomerGathered;
	}

	public void setCustomerGathered(ArrayList<CustomerProfile> customerGathered) {
		CustomerGathered = customerGathered;
	}
	
}

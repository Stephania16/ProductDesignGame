package minimax;

import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Cloneable{
	
	public HashMap<Attribute, Integer> attributeValue;
	public ArrayList<Integer> ValuesPopuProduct;
	
	public Product() {
		super();
	}

	public Product(HashMap<Attribute, Integer> product) {
		super();
		attributeValue = product;
	}

	public HashMap<Attribute, Integer> getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(HashMap<Attribute, Integer> product) {
		attributeValue = product;
	}
	
	public ArrayList<Integer> getValuesPopuProduct()
	{
		return ValuesPopuProduct;
	}
	
	public void setValuesPopuProduct(ArrayList<Integer> valuesPopu) {
		this.ValuesPopuProduct = valuesPopu;
	}
	
	/**Creates a deep copy of Product*/
	public Product clone(){
		Product product = new Product(this.attributeValue);
		return product;
	}
}

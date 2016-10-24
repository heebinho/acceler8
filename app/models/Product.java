package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints.*;

public class Product {
	
	@Required
	public String ean;
	
	@Required
	public String name;
	public String description;
	
	private static List<Product> products;
	
	static{
		//mocking some data
		products = new ArrayList<>();
		products.add(new Product("1111111111111","Clip 1","The best one"));
		products.add(new Product("1111111111112","Clip 2","The cheapest one"));
		products.add(new Product("1111111111113","Clip 3","The most beautiful one"));
	}
	
	public Product(){}
	
	public Product(String ean, String name, String description){
		this.ean = ean;
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", ean, name);
	}
	
	public static List<Product> findAll(){
		return new ArrayList<>(products);
	}
	
	public static Product findByEan(String ean){
		for (Product candidate : products) {
			if(candidate.ean.equals(ean)){
				return candidate;
			}
		}
		return null;
	}
	
	public static List<Product> findByName(String name){
		final List<Product> results = new ArrayList<Product>();
		for (Product candidate : products) {
			if(candidate.name.toLowerCase().contains(name.toLowerCase())){
				results.add(candidate);
			}
		}
		return results;
	}
	
	public static boolean remove(Product product){
		return products.remove(product);
	}
	
	public void save(){
		products.remove(findByEan(ean));
		products.add(this);
	}
}

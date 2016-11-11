package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.product.*;

import java.util.List;

import javax.inject.Inject;

import models.*;

/**
 * @author heebinho
 *
 */
public class ProductController extends Controller {
	
	@Inject FormFactory formFactory;	
	
	public Result index(){
		List<Product> products = Product.findAll();
		return ok(list.render(products));
	}
	
	public Result newProduct(){
		Form<Product> productForm = formFactory.form(Product.class);
		return ok(detail.render(productForm));	
	}
	
	public Result details(String ean){
		final Product product = Product.findByEan(ean);
		
		if (product == null) {
			return notFound(String.format("Product %s does not exist.", ean));
		}
		Form<Product> productForm = formFactory.form(Product.class);
		return ok(detail.render(productForm.fill(product)));
	}
	
	public Result save(){
		Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
		if(productForm.hasErrors()){
			flash("error", "Please correct the form below.");
			return badRequest(detail.render(productForm));
		}
		Product product  = productForm.get();
		product.save();
		flash("success", String.format("Saved product %s", product));
		
		return redirect(routes.ProductController.index());
	}
	
	public Result delete(String ean) {
		final Product product = Product.findByEan(ean);
		if(product == null) {
			return notFound(String.format("Product %s does not exists.", ean));
		}
		Product.remove(product);
		return redirect(routes.ProductController.index());
	}
	
}

package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Cart;

public class CartController extends Controller {
	private Vector<Cart> cartList;
	private Vector<Cart> selectedCart;
	private Cart cart;
	public static CartController controller = null;

	private String errorMessage;

	public static CartController getInstance() {
		if (controller == null) {
			controller = new CartController();
		}
		return controller;

	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public CartController(Vector<Cart> cartList, Vector<Cart> selectedCart) {
		super();
		this.cartList = cartList;
		this.selectedCart = selectedCart;
	}

	private CartController() {
		cart = new Cart();
		cartList = new Vector<>();
	}

	public Vector<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(Vector<Cart> cartList) {
		this.cartList = cartList;
	}

	public Vector<Cart> getSelectedCart() {
		return selectedCart;
	}

	public void setSelectedCart(Vector<Cart> selectedCart) {
		this.selectedCart = selectedCart;
	}

	@Override
	public View view() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Model> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cart addToCart(String userId, String productId, String productQuantity) {
		Integer userID = 0;
		try {
			userID = Integer.parseInt(userId);
		} catch (Exception e) {
			errorMessage = "user Id must be numeric!";
			return null;
		}

		Integer productID = 0;
		try {
			productID = Integer.parseInt(productId);
		} catch (Exception e) {
			errorMessage = "product Id must be numeric!";
			return null;
		}

		Integer qty = 0;
		try {
			qty = Integer.parseInt(productQuantity);
		} catch (Exception e) {
			errorMessage = "product Quantity must be numeric!";
			return null;
		}

		if (qty == 0 || qty == null) {
			errorMessage = "product Quantity cannot be empty!";
			return null;
		}
//			else if (disc < 15000) {
//				must be less than product stock VALIDATION
//				errorMessage = "product Quantity must be at least 15000!";
//				return null;
//			}

		Cart pd = new Cart(userID, productID, qty);
		Cart cart = pd.create();
		if (cart == null) {
			errorMessage = "Add to Cart Failed!";
		}else {
			cartList.add(cart);
		}
		return cart;
	}
	
	public Cart selectCart() {
		selectedCart.add(cart);
		return cart;
	}
	
	public void processSelectedCart() {
		for (Cart cart : selectedCart) {
			//pindah ke transaction
			cart.delete();
		}
		selectedCart.clear();
	}
	
	public void removesSelectedCart() {
		for (Cart cart : selectedCart) {			
			cart.delete();
		}
		selectedCart.clear();
	}
	
	public Cart updateCart(String userId, String productId, String productQuantity) {
		Integer userID = 0;
		try {
			userID = Integer.parseInt(userId);
		} catch (Exception e) {
			errorMessage = "user Id must be numeric!";
			return null;
		}

		Integer productID = 0;
		try {
			productID = Integer.parseInt(productId);
		} catch (Exception e) {
			errorMessage = "product Id must be numeric!";
			return null;
		}

		Integer qty = 0;
		try {
			qty = Integer.parseInt(productQuantity);
		} catch (Exception e) {
			errorMessage = "product Quantity must be numeric!";
			return null;
		}

		if (qty == 0 || qty == null) {
			errorMessage = "product Quantity cannot be empty!";
			return null;
		}
//			else if (disc < 15000) {
//				must be less than product stock VALIDATION
//				errorMessage = "product Quantity must be at least 15000!";
//				return null;
//			}

		Cart pd = new Cart(userID, productID, qty);
		Cart cart = pd.update();
		if (cart == null) {
			errorMessage = "Update Cart Failed!";
		}
		return cart;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

}

package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Cart;
import model.Product;
import view.ManageCartMenuView;
import view.PaymentMenuView;

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

	private CartController(Vector<Cart> cartList, Vector<Cart> selectedCart) {
		super();
		this.cartList = cartList;
		this.selectedCart = selectedCart;
	}

	private CartController() {
		cart = new Cart();
		cartList = new Vector<>();
		Vector<Model> carts = cart.getAll();
		if (carts != null) {
			for (Model model : carts) {
				if (((Cart) model).getUserId() == UserController.getInstance().getActiveUser().getUserId()) {
					cartList.add((Cart) model);
				}
			}
		}
		selectedCart = new Vector<>();
	}

	public void viewManageCartMenu() {
		view().showForm();
	}

	public void viewPaymentMenu() {
		new PaymentMenuView().showForm();
	}

	@Override
	public View view() {
		// TODO Auto-generated method stub
		return new ManageCartMenuView();
	}

	@Override
	public Vector<Model> getAll() {

		// TODO Auto-generated method stub
		return cart.getAll();
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

		if (productID == 0) {
			errorMessage = "Choose Product First!";
			return null;
		} else if (productQuantity.isEmpty()) {
			errorMessage = "Quantity cannot be empty!";
			return null;
		}

		Integer qty = 0;
		try {
			qty = Integer.parseInt(productQuantity);
		} catch (Exception e) {
			errorMessage = "Quantity must be numeric!";
			return null;
		}

		Product p = ProductController.getInstance().getOneProduct(productID);
		if (p == null) {
			errorMessage = "Product Not Exist!";
			return null;
		} else if (qty == 0 || qty == null) {
			errorMessage = "Quantity cannot be empty!";
			return null;
		} else if (qty > p.getProductStock()) {
			errorMessage = "Quantity must be less than product stock!";
			return null;
		}

		for (Cart cart : cartList) {
			if (cart.getUserId() == userID && cart.getProductId() == productID) {
				Cart c = updateCart(userID.toString(), productID.toString(),
						((Integer) (qty + cart.getProductQuantity())).toString());
				return c;
			}
		}

		Cart pd = new Cart(userID, productID, qty);
		Cart cart = pd.create();
		if (cart == null) {
			errorMessage = "Add to Cart Failed!";
		} else {
			cartList.add(cart);
		}
		return cart;
	}

	public Cart selectCart() {
		if (cart.getProductId() == 0 || cart.getProductQuantity() == 0) {
			errorMessage = "Choose Product First!";
			return null;
		}

		for (Cart c : selectedCart) {
			if (c.getProductId() == cart.getProductId() && c.getProductQuantity() == cart.getProductQuantity()
					&& c.getUserId() == cart.getUserId()) {
				errorMessage = "This Product Already Selected!";
				return null;
			}
		}
		selectedCart.add(cart);
		return cart;
	}

	public void processSelectedCart() {
		viewPaymentMenu();
	}

	public void removesSelectedCart() {
		for (Cart cart : selectedCart) {
			cart.delete();
		}
		selectedCart.clear();
		cartList.clear();
		Vector<Model> carts = cart.getAll();
		if (carts != null) {
			for (Model model : carts) {
				if (((Cart) model).getUserId() == UserController.getInstance().getActiveUser().getUserId()) {
					cartList.add((Cart) model);
				}
			}
		}
	}

	public Cart updateCart(String userId, String productId, String productQuantity) {
		if (userId.isEmpty()) {
			errorMessage = "Login First!";
			return null;
		} else if (productId.isEmpty()) {
			errorMessage = "Choose Product First!";
			return null;
		} else if (productQuantity.isEmpty()) {
			errorMessage = "Quantity cannot be empty!";
			return null;
		}

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
			errorMessage = "Quantity must be numeric!";
			return null;
		}

		if (qty == 0) {
			errorMessage = "Quantity cannot be empty!";
			return null;
		}
		Product p = ProductController.getInstance().getOneProduct(productID);
		if (p == null) {
			errorMessage = "Product Not Exist!";
			return null;
		} else if (qty > p.getProductStock()) {
			errorMessage = "Quantity must be less than product stock!";
			return null;
		}

		Cart pd = new Cart(userID, productID, qty);
		Cart c = null;
		for (Cart cart : cartList) {
			if (cart.getUserId() == userID && cart.getProductId() == productID) {
				cart.setProductQuantity(qty);
				c = pd.update();
				System.out.println(c.getProductQuantity());
			}
		}

		if (c == null) {
			errorMessage = "Cart Not Found!";
		}
		return c;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Vector<Cart> getCartList() {

		return cartList;
	}

	public Vector<Cart> getSelectedCart() {
		return selectedCart;
	}

}

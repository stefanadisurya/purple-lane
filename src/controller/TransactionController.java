package controller;

import java.util.Calendar;
import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Cart;
import model.Promo;
import model.Transaction;
import model.TransactionDetail;
import view.TransactionHistoryMenu;

public class TransactionController extends Controller {

	private Transaction activeTransaction;
	private TransactionDetail activeDetailTransaction;

	private Vector<Transaction> transactionList;
	private Vector<TransactionDetail> detailTransactionList;
	private static TransactionController controller;
	private String errorMessage;

	public TransactionController() {
		activeTransaction = new Transaction();
		activeDetailTransaction = new TransactionDetail();
		transactionList = new Vector<>();
		detailTransactionList = new Vector<>();
	}

	public static TransactionController getInstance() {
		return controller = (controller == null) ? new TransactionController() : controller;
	}

	public void viewTransactionHistoryMenu() {
		view().showForm();
	}

	public void viewTransactionReportMenu() {
		// Return TransactionReport
	}

	public Vector<Transaction> getTransactionReport(Integer Month, Integer Year) {
		Vector<Transaction> list = activeTransaction.getTransactionReport(Month, Year);
		if (list.size() == 0)
			return null;
		return list;
	}

	public Vector<Transaction> getTransactionHistory(Integer userId) {
		transactionList = activeTransaction.getTransactionHistory(userId);
		if (transactionList.isEmpty())
			return null;
		return transactionList;
	}

	public Vector<TransactionDetail> getTransactionDetail(Integer transactionId) {
		detailTransactionList = activeDetailTransaction.getAllTransactionDetail(transactionId);
		if (detailTransactionList.size() == 0)
			return null;
		return detailTransactionList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Transaction processPayment(String paymentType, String cardNumber) {
		if (paymentType == null) {
			errorMessage = "Choose Payment Type First!";
			return null;
		} else if (cardNumber.isEmpty() || cardNumber.equals("0")) {
			errorMessage = "Card Number cannot be empty!";
			return null;
		} else if (cardNumber.length() < 16) {
			errorMessage = "Card Number must be at least 16!";
			return null;
		} else {
			Long cn = (long) 0;
			try {
				cn = Long.parseLong(cardNumber);
			} catch (Exception e) {
				errorMessage = "Card Number must be numeric!";
				return null;
			}

			if (cn == 0) {
				errorMessage = "Card Number cannot be empty!";
				return null;
			}

			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			activeTransaction = new Transaction(null, date, paymentType, cardNumber, activeTransaction.getPromoCode(),
					UserController.getInstance().getActiveUser().getUserId());
			activeTransaction = activeTransaction.create();

			if (activeTransaction == null) {
				errorMessage = "Payment Process Failed!";
			}
			return activeTransaction;
		}
	}

	public void processTransactions(Transaction tran) {
		Vector<Cart> carts = CartController.getInstance().getSelectedCart();
		for (Cart cart : carts) {
			TransactionDetail td = new TransactionDetail(tran.getTransactionId(), cart.getProductId(),
					cart.getProductQuantity());
			td.create();
			ProductController.getInstance().getOneProduct(cart.getProductId());
			ProductController.getInstance().reduceStock(cart.getProductQuantity());
		}
		CartController.getInstance().removesSelectedCart();

	}

	public Transaction usePromoCode(String promoCode) {
		Promo promo = PromoController.getInstance().getOnePromo(promoCode);
		if (promo == null) {
			errorMessage = PromoController.getInstance().getErrorMessage();
			return null;
		}
		activeTransaction = activeTransaction.usePromoCode(promoCode);
		return activeTransaction;
	}

	public Cart addToCart() {
		// aneh kenapa ada ini disini
		return null;
	}

	@Override
	public View view() {
		return new TransactionHistoryMenu();
	}

	@Override
	public Vector<Model> getAll() {
		return activeTransaction.getAll();
	}
}

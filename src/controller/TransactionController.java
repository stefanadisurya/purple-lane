package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Cart;
import model.Transaction;
import model.TransactionDetail;
import view.TransactionHistoryMenu;

public class TransactionController extends Controller {
	
	private Transaction activeTransaction;
	private TransactionDetail activeDetailTransaction;
	
	private Vector<Transaction> transactionList;
	private Vector<TransactionDetail> detailTransactionList;
	private static TransactionController controller;
	
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
		if(list.size() == 0) return null;
		return list;
	}
	
	public Vector<Transaction> getTransactionHistory(Integer userId) {
		transactionList = activeTransaction.getTransactionHistory(userId);
		if(transactionList.isEmpty()) return null;
		return transactionList;
	}
	
	public Vector<TransactionDetail> getTransactionDetail(Integer transactionId) {
		detailTransactionList = activeDetailTransaction.getAllTransactionDetail(transactionId);
		if(detailTransactionList.size() == 0) return null;
		return detailTransactionList;
	}
	
	public Transaction processPayment(String paymentType, String cardNumber) {
		return null;
	}

	public void usePromoCode() {
		// mungkin valid dicari di promo code
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

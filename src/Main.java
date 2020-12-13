
import controller.AdminController;
import controller.AuthController;
import controller.LoginController;
import controller.ProductController;
import controller.PromoController;
import controller.RegisterController;

import view.CustomerHomeView;
import view.HireStaffView;
import view.LoginView;
import view.RegisterView;


public class Main {

	public Main() {
//		AdminController.getInstance().view().showForm();
//		RegisterController.getInstance().view().showForm();
//		LoginController.getInstance().view().showForm();
		AuthController.getInstance().view().showForm();
		

//		new RegisterView().showForm();
//		new LoginView().showForm();
//		new HireStaffView().showForm();

		//Albert Promo View Testing
//		PromoController.getInstance().viewManagePromoMenu();
	}

	public static void main(String[] args) {
		new Main();
	}

}

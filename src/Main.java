
import controller.AdminController;
import controller.AuthController;
import controller.LoginController;
import controller.PromoController;
import controller.RegisterController;
import view.RegisterView;

public class Main {

	public Main() {
//		AdminController.getInstance().view().showForm();
//		RegisterController.getInstance().view().showForm();
//		LoginController.getInstance().view().showForm();
//		AuthController.getInstance().view().showForm();
		
		new RegisterView().showForm();
		
		//Albert Promo View Testing
//		PromoController.getInstance().view().showForm();
	}

	public static void main(String[] args) {
		new Main();
	}

}


import controller.LoginController;


public class Main {

	public Main() {
//		AdminController.getInstance().view().showForm();
//		RegisterController.getInstance().view().showForm();
		LoginController.getInstance().view().showForm();
//		AuthController.getInstance().view().showForm();
		
//		new RegisterView().showForm();
//		new LoginView().showForm();
//		new HireStaffView().showForm();

	}

	public static void main(String[] args) {
		new Main();
	}

}

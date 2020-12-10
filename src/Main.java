import controller.AdminController;
import controller.AuthController;
import controller.LoginController;
import controller.RegisterController;

public class Main {

	public Main() {
//		AdminController.getInstance().view().showForm();
//		RegisterController.getInstance().view().showForm();
		LoginController.getInstance().view().showForm();
	}

	public static void main(String[] args) {
		new Main();
	}

}

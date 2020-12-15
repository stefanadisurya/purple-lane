
import controller.LoginController;

public class Main {

	public Main() {
		LoginController.getInstance().view().showForm();

	}

	public static void main(String[] args) {
		new Main();
	}

}

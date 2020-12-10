
package core.view;

import javax.swing.JFrame;

public abstract class View extends JFrame {

	protected int width;
	protected int height;

	public View() {
		initialize();
		initializeComponent();
	}

	public void showForm() {
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public abstract void initialize();

	public abstract void initializeComponent();

}

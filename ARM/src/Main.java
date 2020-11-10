import Views.LoginView;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("ARM");

		LoginView loginView = new LoginView();
		mainFrame.add(loginView.getMainPanel());

		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//mainFrame.setUndecorated(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}

package Views;

import ViewModels.MenuViewModel;

import javax.swing.*;

public class MenuView {
	MenuViewModel menuViewModel = new MenuViewModel();
	private JPanel rootPane;
	private JTextField textField1;
	private JButton searchButton;
	private JButton confirmButton;
	private JPanel billPane;
	private JPanel itemsPane;

	MenuView() {

	}

	public JPanel getRootPane() {
		return rootPane;
	}
}

package Views;

import Models.User;

import javax.swing.*;
import java.awt.*;

public class EmployeesView {
	private User currentUser;
	private JPanel rootPane;

	public EmployeesView(User user) {
		this.currentUser = user;
	}

	public JPanel getRootPane() {
		return rootPane;
	}

}

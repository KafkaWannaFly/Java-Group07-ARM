package Views;

import Models.User;

import javax.swing.*;
import java.awt.*;

public class StatisticView {
	private User currentUser;

	private JPanel rootPane;

	public StatisticView(User user) {
		this.currentUser = user;
	}

	public JPanel getRootPane() {
		return rootPane;
	}

}

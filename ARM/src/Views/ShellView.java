package Views;

import Models.User;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Function;

public class ShellView {
	private JPanel rootPane;
	private JPanel categoryPane;
	private JLabel menuLabel;
	private JLabel membershipLabel;
	private JLabel statisticLabel;
	private JLabel employeesLabel;
	private JLabel userAccountLabel;
	private JPanel subViewPane;

	private User currentUser;
	private MenuView menuView;
	private MembershipView membershipView;
	private StatisticView statisticView;
	private EmployeesView employeesView;
	private UserAccountView userAccountView;

	private ArrayList<JLabel> categoryLabels = new ArrayList<>();

	private Function<Void, Void> onDOMChanged;
	private Function<JLabel, Void> onCategoryLabelSelected;

	private Color defaultFontColor = new Color(0, 0, 0);
	private Color defaultBackgroundColor = new Color(255, 255, 255);

	private Color selectedFontColor = new Color(255, 255, 255);
	private Color selectedBackgroundColor = new Color(5, 131, 242);

	public ShellView() {

	}

	public ShellView(User user) {
		try {
			currentUser = user;
			menuView = new MenuView(user);
			membershipView = new MembershipView();

			if(!user.isManager()) {
				employeesLabel.setEnabled(false);
				statisticLabel.setEnabled(false);
			}
			employeesView = new EmployeesView(user);
			statisticView = new StatisticView(user);
			userAccountView = new UserAccountView(user);

			// Side bar navigation
			Component[] components = categoryPane.getComponents();
			for (int i = 0; i < components.length; i++) {
				JLabel label = (JLabel) components[i];
				if (label != null) {
					categoryLabels.add(label);
				}
			}

			// MenuView will be landing page
			menuLabel.setBackground(selectedBackgroundColor);
			menuLabel.setForeground(selectedFontColor);
			subViewPane.add(menuView.getRootPane());

			this.setCategoryLabelsMouseListener();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	private void setCategoryLabelsMouseListener() {
		onCategoryLabelSelected = new Function<JLabel, Void>() {
			@Override
			public Void apply(JLabel jLabel) {
				for (JLabel label : categoryLabels) {
					if (label != jLabel) {
						label.setBackground(defaultBackgroundColor);
						label.setForeground(defaultFontColor);
					}
					else // Selected Label
					{
						label.setBackground(selectedBackgroundColor);
						label.setForeground(selectedFontColor);

						if (subViewPane.getComponents().length != 0) {
							subViewPane.remove(0); // Remove the only component if it has
						}
//						subViewPane.invalidate();

						String tittle = label.getText();
						switch (tittle) {

							case "Menu": {
								if (menuView != null)
									subViewPane.add(menuView.getRootPane());
								break;
							}
							case "Membership": {
								if (membershipView != null)
									subViewPane.add(membershipView.getRootPane());
								break;
							}
							case "Statistic": {
								if (statisticView != null)
									subViewPane.add(statisticView.getRootPane());
								break;
							}
							case "Employees": {
								if (employeesView != null)
									subViewPane.add(employeesView.getRootPane());
								break;
							}
							case "User Account": {
								if (userAccountView != null)
									subViewPane.add(userAccountView.getRootPane());
								break;
							}
							default: {

							}
						}

//						subViewPane.validate();
						if (onDOMChanged != null) {
							onDOMChanged.apply(null);
						}
					}
				}
				return null;
			}
		};

		for (JLabel label : categoryLabels) {
			label.addMouseListener(new MouseInputAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					onCategoryLabelSelected.apply((JLabel) e.getSource());
				}
			});
		}
	}

	public void setOnDOMChanged(Function<Void, Void> onDOMChanged) {
		this.onDOMChanged = onDOMChanged;

		menuView.setOnDOMChanged(onDOMChanged);
	}

	public JPanel getRootPane() {
		return rootPane;
	}

}

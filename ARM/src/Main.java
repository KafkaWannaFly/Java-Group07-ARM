import Models.User;
import Views.LoginView;
import Views.MenuView;
import Views.ShellView;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatIntelliJLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		App app = new App();
		app.setTitle("ARM");

		LoginView loginView = new LoginView();
		app.add(loginView.getMainPanel());

		loginView.setOnNavigate(new Function<Void, Void>() {
			@Override
			public Void apply(Void unused) {
				app.refresh();
				return null;
			}
		});

		loginView.setOnLoginSuccess(new Function<User, Void>() {
			@Override
			public Void apply(User user) {
				try {
					System.out.println("Login success: " + user);
					app.remove(loginView.getMainPanel());

					ShellView shellView = new ShellView(user);
					shellView.setOnDOMChanged(new Function<Void, Void>() {
						@Override
						public Void apply(Void unused) {
							app.refresh();
							return null;
						}
					});

					app.add(shellView.getRootPane());
					app.refresh();
				} catch (Exception exception) {
					exception.printStackTrace();
				}

				return null;
			}
		});

		// Maximize window when start the program
		app.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//mainFrame.setUndecorated(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);

		app.refresh();
	}
}

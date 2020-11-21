import Views.LoginView;
import Views.SignUpView;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("ARM");

		LoginView loginView = new LoginView();

//		DatePicker datePicker = new DatePicker();
//		signUpView.getDobPanel().add(datePicker);


		mainFrame.add(loginView.getMainPanel());

		loginView.onNavigate = new Function<Void, Void>() {
			@Override
			public Void apply(Void unused) {
				mainFrame.setExtendedState(JFrame.NORMAL);
				mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				return null;
			}
		};

		// Resize window when user press 'Restore Down'
		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				if(mainFrame.getExtendedState() == JFrame.NORMAL) {
					mainFrame.pack();
				}
			}
		});

		// Maximize window when start the program
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//mainFrame.setUndecorated(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}

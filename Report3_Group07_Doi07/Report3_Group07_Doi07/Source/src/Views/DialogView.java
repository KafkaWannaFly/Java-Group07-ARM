package Views;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DialogView {
	private JPanel root;
	private JLabel title;
	private JPanel body;
	private JPanel contentPanel;
	private JPanel buttonsPanel;
	private JLabel contentLabel;

	static public void showInfoDialog(String title, String message) {
		JFrame frame = new JFrame();
		final JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);


		final JDialog dialog = new JDialog(frame, title, true);
		optionPane.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						String prop = e.getPropertyName();

						if (dialog.isVisible()
								    && (e.getSource() == optionPane)
								    && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
							//If you were going to check something
							//before closing the window, you'd do
							//it here.
							dialog.setVisible(false);
						}
					}
				});
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(
				JDialog.HIDE_ON_CLOSE);

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - dialog.getWidth()) / 2;
		final int y = (screenSize.height - dialog.getHeight()) / 2;
		dialog.setLocation(x, y);
		dialog.pack();
		dialog.setVisible(true);
	}

}

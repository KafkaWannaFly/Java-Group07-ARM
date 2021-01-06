import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class App extends JFrame {
	App() {
		this.setRestoreButtonMouseListener();
	}
	App(String tittle) {
		this.setRestoreButtonMouseListener();

		this.setTitle(tittle);

	}

	public void refresh() {
		setExtendedState(JFrame.NORMAL);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void setRestoreButtonMouseListener() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				if(getExtendedState() == JFrame.NORMAL) {
					pack();
				}
			}
		});
	}
}

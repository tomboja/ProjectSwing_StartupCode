package librarysystem;

import javax.swing.*;

import business.SystemController;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SearchMemberWindow extends JFrame implements LibWindow, MessageableWindow {

	public static final SearchMemberWindow INSTANCE = new SearchMemberWindow();
	private boolean isInitialized = false;
	private SystemController controller = new SystemController();

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel titlePanel;

	private JTextField memberIdField;
	JButton searchButton;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	SearchMemberWindow() {
		init();
	}

	@Override
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTitlePanel();
		defineTopPanel();
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(topPanel, BorderLayout.CENTER);

		getContentPane().add(mainPanel);
		isInitialized(true);
	}

	private void defineTitlePanel() {
		titlePanel = new JPanel();
		JLabel addBookCopyLabel = new JLabel("Search Member");
		Util.adjustLabelFont(addBookCopyLabel, Util.DARK_BLUE, true);
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.add(addBookCopyLabel);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel isbnLabel = new JLabel("Memebr Id: ");
		memberIdField = new JTextField(10);


		searchButton = new JButton("Search");
		searchButton.addActionListener(evt -> {
			actionListner(evt);
		});

		topPanel.add(isbnLabel);
		topPanel.add(memberIdField);
		topPanel.add(searchButton);
	}

	private void actionListner(ActionEvent evt) {
		String id = memberIdField.getText().trim();
		if (!id.isEmpty()) {
			controller.searchMember(id);
		} else {
			displayError("Isbn and boock copy can not be empty");
		}
		
	}


	public void updateData() {
		// nothing to do

	}

	private static final long serialVersionUID = -7540120623224806105L;

}
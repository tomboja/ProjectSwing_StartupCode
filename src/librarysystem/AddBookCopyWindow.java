package librarysystem;

import javax.swing.*;

import business.SystemController;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AddBookCopyWindow extends JFrame implements LibWindow, MessageableWindow{

	public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
	private boolean isInitialized = false;
	private SystemController controller = new SystemController();

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel titlePanel;
	private JPanel somePanel;

	private JTextField isbnField;
	private JTextField copyNumField;
	JButton addButton;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	AddBookCopyWindow() {
		init();
	}

	@Override
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTitlePanel();
		defineTopPanel();
		defineBottomPanel();
		defineSomePanel();
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(somePanel, BorderLayout.CENTER);

		getContentPane().add(mainPanel);
		isInitialized(true);
	}

	private void defineTitlePanel() {
		titlePanel = new JPanel();
		JLabel addBookCopyLabel = new JLabel("Add Book Copy");
		Util.adjustLabelFont(addBookCopyLabel, Util.DARK_BLUE, true);
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.add(addBookCopyLabel);

	}
	
	private void defineSomePanel() {
		somePanel = new JPanel();
		somePanel.setLayout(new BorderLayout());
		somePanel.add(topPanel, BorderLayout.NORTH);
		somePanel.add(bottomPanel, BorderLayout.CENTER);
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
		JLabel isbnLabel = new JLabel("ISBN");
		// Util.adjustLabelFont(isbnLabel, Util.DARK_BLUE, true);
		isbnField = new JTextField(10);

		JLabel copyNum = new JLabel("Copy Number");
		// Util.adjustLabelFont(copyNum, Util.DARK_BLUE, true);
		copyNumField = new JTextField(10);

		topPanel.add(isbnLabel);
		topPanel.add(isbnField);

		topPanel.add(copyNum);
		topPanel.add(copyNumField);

	}

	public void defineBottomPanel() {
		bottomPanel = new JPanel();
		addButton = new JButton("Add Copy");
		bottomPanel.add(addButton, BorderLayout.CENTER);
		addButton.addActionListener(evt -> {
			actionListner(evt);
		});
	}

	private void actionListner(ActionEvent evt) {
		String isbn = isbnField.getText().trim();
		String copyNum = copyNumField.getText().trim();
		if (!isbn.isEmpty() && !copyNum.isEmpty()) {
			controller.addBookCopy(isbn, copyNum);
		} else {
			displayError("Isbn and boock copy can not be empty");
		}
		
	}


	public void updateData() {
		// nothing to do

	}

	private static final long serialVersionUID = -7540120623224806105L;

}
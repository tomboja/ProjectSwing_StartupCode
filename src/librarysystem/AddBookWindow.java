package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Address;
import business.Author;
import business.SystemController;

public class AddBookWindow extends JFrame implements ActionListener, MessageableWindow {

	private static final long serialVersionUID = 9162574496617610180L;
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;

	private JTextField firstNameTextFeild, lastNameTextFeild, isbnTextField, titleTextFeild, maxCheckOutTextField, numOfCopyTextField, credetialTextFeild, phoneTextFeild;
	private JButton addButton;
	private Address address = new Address("st 1", "Failrfield", "IA", "001122");
	
	public JPanel getMainPanel() {
		return mainPanel;
	}

	public AddBookWindow() {
		initializeWindow();
		mainPanel = new JPanel();
		setTitle("Add New Book");
		handleWindowClosing();
		defineTopPanel();
		defineMiddlePanel();
		defineBottomPanel();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.WEST);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		// setSize(660, 500);
	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());		
		JLabel isbnLable = new JLabel("ISBN");
		isbnTextField = new JTextField(15);
		JPanel isbnPanel = createTextPanel(isbnLable, isbnTextField);

		JLabel maxCheckOutLable = new JLabel("Max Checkout Length");
		maxCheckOutTextField = new JTextField(15);
		JPanel maxPanel = createTextPanel(maxCheckOutLable, maxCheckOutTextField);
		
		JLabel titleLable = new JLabel("Title");
		titleTextFeild = new JTextField(15);
		JPanel titelPanel = createTextPanel(titleLable, titleTextFeild);
		
		JLabel numCopyLable = new JLabel("Number Of Copies");
		numOfCopyTextField = new JTextField(15);
		JPanel numCopyPanel = createTextPanel(numCopyLable, numOfCopyTextField);
		
		JPanel top = new JPanel();
		JPanel middle = new JPanel();
		JPanel bottom = new JPanel();

		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		middle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		top.add(isbnPanel);
		middle.add(maxPanel);
		bottom.add(titelPanel);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(top, BorderLayout.NORTH);
		textPanel.add(middle, BorderLayout.CENTER);
		textPanel.add(bottom, BorderLayout.SOUTH);

		topPanel.add(textPanel, BorderLayout.NORTH);
	    topPanel.add(numCopyPanel, BorderLayout.CENTER);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		JLabel firstNameLabel = new JLabel("Author First Name");
		firstNameTextFeild = new JTextField(15);
		JPanel authNamePanel = createTextPanel(firstNameLabel, firstNameTextFeild);
		JLabel lastNameLabel = new JLabel("Author Last Name");
		lastNameTextFeild = new JTextField(15);
		JPanel lastNamePanel = createTextPanel(lastNameLabel, lastNameTextFeild);
		JLabel credetialLable = new JLabel("Credetial");
		credetialTextFeild = new JTextField(15);
		JPanel credentialPanel = createTextPanel(credetialLable, credetialTextFeild);		
		JLabel phoneLabel = new JLabel("Phone number");
		phoneTextFeild = new JTextField(15);
		JPanel phonePanel = createTextPanel(phoneLabel, phoneTextFeild);		
		JPanel top = new JPanel();
		JPanel middle = new JPanel();
		JPanel bottom = new JPanel();

		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		middle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		top.add(authNamePanel);
		middle.add(lastNamePanel);
		bottom.add(credentialPanel);
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(top, BorderLayout.NORTH);
		textPanel.add(middle, BorderLayout.CENTER);
		textPanel.add(bottom, BorderLayout.SOUTH);
		middlePanel.add(textPanel, BorderLayout.NORTH);
		middlePanel.add(phonePanel, BorderLayout.CENTER);
	}

	private void defineBottomPanel() {

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addButton = new JButton("Add Book");
		bottomPanel.add(addButton);
		addSubmitButtonListener(addButton);
	}

	private void addSubmitButtonListener(JButton btn) {
		btn.addActionListener(evt -> {
			String isbn = isbnTextField.getText().trim();
			String checkoutLen = maxCheckOutTextField.getText().trim();
			String title = titleTextFeild.getText().trim();
			String numberOfCopies = numOfCopyTextField.getText().trim();
			
			String fname = firstNameTextFeild.getText().trim();
			String lname = lastNameTextFeild.getText().trim();
			String creds = credetialTextFeild.getText().trim();
			String phoneNum = phoneTextFeild.getText().trim();
			Author auth = null;
			Boolean error = false;
			Boolean success = false;
			SystemController controller = new SystemController();
			try {
				int copies = Integer.parseInt(numberOfCopies);
				int phoneNUmber = Integer.parseInt(phoneNum);
				if (!fname.isEmpty() && !lname.isEmpty() && !creds.isEmpty() && !phoneNum.isEmpty()) {
					auth = new Author(fname, lname, title, address, phoneNum);
				} else {
					error = true;
					displayError("Author information can not be empty.");
				} 
				
				if (!isbn.isEmpty() 
						&& !checkoutLen.isEmpty() 
						&& !title.isEmpty() 
						&& !numberOfCopies.isEmpty() && !error) {
					success = controller.addBook(isbn, title, checkoutLen, numberOfCopies, auth);
				} else {
					displayError("Book information cannot be empty.");
				}
				if (success && !error) {
					displayInfo("Book added successfully.");
					isbnTextField.setText("");
					titleTextFeild.setText("");
					maxCheckOutTextField.setText("");
					numOfCopyTextField.setText("");
					firstNameTextFeild.setText("");
					lastNameTextFeild.setText("");
					credetialTextFeild.setText("");
					phoneTextFeild.setText("");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(AddBookWindow.this, "Please check and insert correct data.");
			}
			
		});
		
	}

	private static JPanel createTextPanel(JLabel lab, JTextField textField) {

		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		top.add(lab);
		bottom.add(textField);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(top, BorderLayout.NORTH);
		textPanel.add(bottom, BorderLayout.CENTER);
		return textPanel;
	}

	private void initializeWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 300);
		handleWindowClosing();
		setResizable(false);
	}

	public static Font makeSmallFont(Font f) {
		return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
	}

	private void handleWindowClosing() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				dispose();
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}



}

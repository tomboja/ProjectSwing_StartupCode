package librarysystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddMemberWindow extends JFrame implements LibWindow, MessageableWindow {

	public static final AddMemberWindow INSTANCE = new AddMemberWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;

	private JTextField nameTextFeild, streetTextField, cityTextField, stateTextField, zipTextField, phoneTexFiel,
			idTextField, lastNameTextFeild;
	private JButton button;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	/* This class is a singleton */
	private AddMemberWindow() {
		init();
	}

	public void init() {
		initializeWindow();
		mainPanel = new JPanel();
		defineTopPanel();
		defineMiddlePanel();
		defineBottomPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized(true);
		pack();
		// setSize(660, 500);
	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel idLable = new JLabel("Member Id");
		idTextField = new JTextField(10);
		JPanel idPanel = createTextPanel(idLable, idTextField);

		JLabel labelName = new JLabel("First Name");
		nameTextFeild = new JTextField(10);
		JPanel namePanel = createTextPanel(labelName, nameTextFeild);

		JLabel labelLastName = new JLabel("Last Name");
		lastNameTextFeild = new JTextField(10);
		JPanel lastNamePanel = createTextPanel(labelLastName, lastNameTextFeild);

		topPanel.add(idPanel);
		topPanel.add(namePanel);
		topPanel.add(lastNamePanel);
	}

	private void defineBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		button = new JButton("Submit");
		addSubmitButtonListener(button);

		bottomPanel.add(button);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel streetLabel = new JLabel("Street");
		streetTextField = new JTextField(10);
		JPanel streetPanel = createTextPanel(streetLabel, streetTextField);

		JLabel cityLabel = new JLabel("City");
		cityTextField = new JTextField(10);
		JPanel cityPanel = createTextPanel(cityLabel, cityTextField);

		JLabel stateLabel = new JLabel("State");
		stateTextField = new JTextField(10);
		JPanel statePanel = createTextPanel(stateLabel, stateTextField);

		JLabel zipLabel = new JLabel("Zip");
		zipTextField = new JTextField(10);
		JPanel zipPanel = createTextPanel(zipLabel, zipTextField);

		JLabel phoneLabel = new JLabel("Phone Number");
		phoneTexFiel = new JTextField(10);
		JPanel phonePanel = createTextPanel(phoneLabel, phoneTexFiel);

		middlePanel.add(streetPanel);
		middlePanel.add(cityPanel);
		middlePanel.add(statePanel);
		middlePanel.add(zipPanel);
		middlePanel.add(phonePanel);

	}

	private static JPanel createTextPanel(JLabel lab, JTextField textField) {

		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		lab.setFont(Util.makeSmallFont(lab.getFont()));
		top.add(lab);
		bottom.add(textField);

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(top, BorderLayout.NORTH);
		textPanel.add(bottom, BorderLayout.CENTER);
		return textPanel;
	}

	private void handleWindowClosing() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				dispose();
				// other clean-up
				System.exit(0);
			}
		});
	}

	private void initializeWindow() {
		setTitle("Add Member");
		setSize(500, 1000);
		handleWindowClosing();
		centerFrameOnDesktop(this);
		setResizable(false);
	}

	public static void centerFrameOnDesktop(Component f) {
		final int SHIFT_AMOUNT = 0;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2) - SHIFT_AMOUNT, (height - frameHeight) / 3);
	}

	private void addSubmitButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String street = streetTextField.getText().trim();
			String city = cityTextField.getText().trim();
			String state = stateTextField.getText().trim();
			String zip = zipTextField.getText().trim();

			String memberId = idTextField.getText().trim();
			String fname = nameTextFeild.getText().trim();
			String lname = lastNameTextFeild.getText().trim();
			String tel = phoneTexFiel.getText().trim();
			String addressValid = "";
			String memeberDataValid = "";
			try {
				RuleSet rules = RuleSetFactory.getRuleSet(AddMemberWindow.this);
				rules.applyRules(AddMemberWindow.this);
			} catch (RuleException e) {
				JOptionPane.showMessageDialog(AddMemberWindow.this, e.getMessage());
			}
			LibrarySystem.INSTANCE.ci.addMember(street, city, state, zip, memberId, fname, lname, tel);
			this.displayInfo("Member Added Successfully");
			JOptionPane.showMessageDialog(this, "Member Added Successfully", "Success", 1);

			clearData();
		}

		);
	}

	public void clearData() {
		streetTextField.setText("");
		cityTextField.setText("");
		stateTextField.setText("");
		zipTextField.setText("");
		idTextField.setText("");
		nameTextFeild.setText("");
		lastNameTextFeild.setText("");
		phoneTexFiel.setText("");
	}

	public String getNameTextFeild() {
		return nameTextFeild.getText();
	}

	public String getStreetTextField() {
		return streetTextField.getText();
	}

	public String getCityTextField() {
		return cityTextField.getText();
	}

	public String getStateTextField() {
		return stateTextField.getText();
	}

	public String getZipTextField() {
		return zipTextField.getText();
	}

	public String getPhoneTexField() {
		return phoneTexFiel.getText();
	}

	public String getmemberTextField() {
		return idTextField.getText();
	}

	public String getLastname() {
		return lastNameTextFeild.getText();
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub

	}

	private static final long serialVersionUID = 1L;
}

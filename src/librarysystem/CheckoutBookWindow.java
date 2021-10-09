package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.SystemController;

public class CheckoutBookWindow extends JFrame {

	private static final long serialVersionUID = -2258101982225887725L;
	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private JPanel middleTop;
	private JPanel somePanel;
	private JLabel isbnlabel;
	private JLabel memberidlabel;
	private JTextField isbntextField;
	private JTextField memberTextfeild;
	private JButton btn;
	private JPanel lastPanel;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	// Singleton class
	private CheckoutBookWindow() {
		init();
	}

	private void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineBottomPanel();
		defineSomePanel();
		defineTablePanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(somePanel, BorderLayout.CENTER);
		mainPanel.add(lastPanel, BorderLayout.SOUTH);
	}

	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private CustomTableModel model;
	private JTable table;
	// table data and config
	private final String[] DEFAULT_COLUMN_HEADERS = { "Book ISBN", "Book Title", "Checkout Date" };
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TABLE_WIDTH = (int) (0.70 * SCREEN_WIDTH);
	private static final int DEFAULT_TABLE_HEIGHT = (int) (0.43 * SCREEN_HEIGHT);
	// these numbers specify relative widths of the columns --
	// they must add up to 1
	private final float[] COL_WIDTH_PROPORTIONS = { 0.35f, 0.35f, 0.3f };

	private void defineTablePanel() {
		tablePanel = new JPanel();
		scrollPane = new JScrollPane();
		lastPanel = new JPanel();
		createTableAndTablePane();
		tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tablePanel.add(scrollPane);
		lastPanel.add(tablePanel);
		lastPanel.setVisible(false);
	}

	private void updateModel() {
		List<String[]> theData = new ArrayList<String[]>();
		updateModel(theData);
	}

	public void updateModel(List<String[]> list) {
		if (model == null) {
			model = new CustomTableModel();
		}
		model.setTableValues(list);
	}

	private void createCustomColumns(JTable table, int width, float[] proportions, String[] headers) {
		table.setAutoCreateColumnsFromModel(false);
		int num = headers.length;
		for (int i = 0; i < num; ++i) {
			TableColumn column = new TableColumn(i);
			column.setHeaderValue(headers[i]);
			column.setMinWidth(Math.round(proportions[i] * width));
			table.addColumn(column);
		}
	}

	private void createTableAndTablePane() {
		updateModel();
		table = new JTable(model);
		createCustomColumns(table, TABLE_WIDTH, COL_WIDTH_PROPORTIONS, DEFAULT_COLUMN_HEADERS);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
		scrollPane.getViewport().add(table);

	}

	private void defineSomePanel() {
		somePanel = new JPanel();
		somePanel.setLayout(new BorderLayout());
		somePanel.add(middlePanel, BorderLayout.NORTH);
		somePanel.add(bottomPanel, BorderLayout.CENTER);
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel checkoutTitle = new JLabel("Checkout book");
		Util.adjustLabelFont(checkoutTitle, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(checkoutTitle);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		defineMiddleBottom();
		middlePanel.add(middleTop);

	}

	private void defineMiddleBottom() {
		middleTop = new JPanel();
		middleTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		isbnlabel = new JLabel("ISBN");
		memberidlabel = new JLabel("Member Id");
		isbntextField = new JTextField();
		memberTextfeild = new JTextField();
		memberTextfeild.setPreferredSize(new Dimension(160, 40));
		isbntextField.setPreferredSize(new Dimension(160, 40));
		middleTop.add(isbnlabel);
		middleTop.add(isbntextField);
		middleTop.add(memberidlabel);
		middleTop.add(memberTextfeild);
	}

	private void defineBottomPanel() {
		btn = new JButton("Checkout book");
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		bottomPanel.add(btn);
		btn.addActionListener(evt -> {
			actionListner(evt);
		});
	}

	private void actionListner(ActionEvent evt) {
		 Boolean memberIdFound =false;
		 Boolean isbnIsFound =false;
		String isbn = isbntextField.getText().trim();
		String memberId = memberTextfeild.getText().trim();
		
		
		
		try {
			RuleSet rules=RuleSetFactory.getRuleSet(CheckoutBookWindow.this);
			rules.applyRules(CheckoutBookWindow.this);
			isbnIsFound = ci.bookISBNFound(isbn, memberId);
		    memberIdFound = ci.memberIdFound(memberId);
			 
		} catch (RuleException e){
			JOptionPane.showMessageDialog(CheckoutBookWindow.this, e.getMessage());
		}
		
		
		

		List<String[]> record = null;
		if (isbnIsFound && memberIdFound) {
			record = ci.checkoutBook(isbn, memberId);
			setRecordsData(record);
			lastPanel.setVisible(true);
			memberTextfeild.setText("");
			isbntextField.setText("");
		}
	}

	List<String[]> chechoutRecords = new ArrayList<String[]>();

	private void setRecordsData(List<String[]> record) {
		model.setTableValues(record);
		table.updateUI();

	}
	
	public String getmemberid(){
		return memberTextfeild.getText();
	}
	public String getisbn(){
		return isbntextField.getText();
	}
	
}

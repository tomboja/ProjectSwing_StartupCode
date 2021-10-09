package librarysystem;

import javax.swing.*;
import javax.swing.table.TableColumn;

import business.SystemController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CheckBookCopyStatusWindow extends JFrame implements LibWindow, MessageableWindow {

	public static final CheckBookCopyStatusWindow INSTANCE = new CheckBookCopyStatusWindow();
	private boolean isInitialized = false;
	private SystemController controller = new SystemController();

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel titlePanel;
	private JPanel tablePanel;
	private JScrollPane scrollPane;
	private CustomTableModel model;
	private JTable table;
	private JPanel lastPanel;

	private JTextField memberIdField;
	JButton searchButton;

	public JPanel getMainPanel() {
		return mainPanel;
	}

	CheckBookCopyStatusWindow() {
		init();
	}

	@Override
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTitlePanel();
		defineTopPanel();
		defineTablePanel();
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(topPanel, BorderLayout.CENTER);
		mainPanel.add(lastPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized(true);
	}

	private void defineTitlePanel() {
		titlePanel = new JPanel();
		JLabel addBookCopyLabel = new JLabel("Check book status");
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
		JLabel isbnLabel = new JLabel("Book Id: ");
		memberIdField = new JTextField(15);

		searchButton = new JButton("Check status");
		searchButton.addActionListener(evt -> {
			actionListner(evt);
		});

		topPanel.add(isbnLabel);
		topPanel.add(memberIdField);
		topPanel.add(searchButton);
	}

	// table data and config
	private final String[] DEFAULT_COLUMN_HEADERS = { "Book Title", "Member Id", "Copy Number", "Due Date" };
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TABLE_WIDTH = (int) (0.70 * SCREEN_WIDTH);
	private static final int DEFAULT_TABLE_HEIGHT = (int) (0.43 * SCREEN_HEIGHT);
	// these numbers specify relative widths of the columns --
	// they must add up to 1
	private final float[] COL_WIDTH_PROPORTIONS = { 0.3f, 0.2f, 0.2f, 0.3f };

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


	private void actionListner(ActionEvent evt) {
		String id = memberIdField.getText().trim();
		if (!id.isEmpty()) {
			List<String[]> data = controller.searchBook(id);
			model.setTableValues(data);
			table.updateUI();
			lastPanel.setVisible(true);
			memberIdField.setText("");
		} else {
			displayError("Book Id can not be empty");
		}

	}

	public void updateData() {
		// nothing to do

	}

	private static final long serialVersionUID = -7540120623224806105L;

}

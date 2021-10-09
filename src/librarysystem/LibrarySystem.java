package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import business.ControllerInterface;
import business.SystemController;

public class LibrarySystem extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static JTextArea statusBar = new JTextArea("Welcome Library System!");
	LibrarySystem libSystem;
	LoginPanelWindow lp;
	AllBookIdsWindow allbookid = AllBookIdsWindow.INSTANCE;
	AllMemberIdsWindow allMemberId = AllMemberIdsWindow.INSTANCE;

	String[] links;
	JList<ListItem> linkList;
	JPanel cards;

	// list items
	ListItem loginLogout = new ListItem("Login/Logout", true);
	ListItem addMember = new ListItem("Add Member", false);
	ListItem searchMemeber = new ListItem("Search Member", false);
	ListItem checkOutBook = new ListItem("Checkout Book", false);
	ListItem addBook = new ListItem("Add Book", false);
	ListItem addBookCopy = new ListItem("Add Book Copy", false);
	ListItem checkbookStatus = new ListItem("Check Status of Book Copy", false);
	ListItem allMemberIds = new ListItem("All Member IDs", false);
	ListItem allBookIds = new ListItem("All Book IDs", false);

	ListItem[] librarianItem = { loginLogout, checkbookStatus, searchMemeber, checkOutBook, allBookIds, allMemberIds };

	ListItem[] adminItem = { loginLogout, addMember, addBook, searchMemeber, allBookIds, allMemberIds, addBookCopy };
	ListItem[] bothItem = { addBook, addMember, searchMemeber, allBookIds, allMemberIds, checkbookStatus, checkOutBook,
			addBookCopy };

	public void setLibSystem(LibrarySystem sys) {
		libSystem = sys;
	}

	private LibrarySystem() {
		Util.adjustLabelFont2(statusBar, Util.DARK_BLUE, true);
		setSize(600, 450);

		createLinkLabels();
		createMainPanels();
		CardLayout cl = (CardLayout) (cards.getLayout());
		linkList.addListSelectionListener(event -> {
			String value = linkList.getSelectedValue().getItemName();
			boolean allowed = linkList.getSelectedValue().highlight();
			System.out.println(value + " " + allowed);

			// System.out.println("selected = " + value);
			// cl = (CardLayout)(cards.getLayout());
			statusBar.setText("");
			if (!allowed) {
				System.out.println("Value>>> " + value);
				value = loginLogout.getItemName();
				linkList.setSelectedIndex(0);
			}
			if (value.equals("All Book IDs"))
				allbookid.updateBookIds();
			if (value.equals("All Member IDs"))
				allMemberId.updateMemberIds();
			cl.show(cards, value);
		});
		// set up split panes
		JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
		innerPane.setDividerLocation(180);
		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, statusBar);
		outerPane.setDividerLocation(350);
		add(outerPane, BorderLayout.CENTER);
		lp.setLibSystem(this);
	}

	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
	JMenu options;
	JMenuItem login;
	String pathToImage;
	private boolean isInitialized = false;

	private static LibWindow[] allWindows = { LibrarySystem.INSTANCE, LoginPanelWindow.INSTANCE, AllMemberIdsWindow.INSTANCE,
			AllBookIdsWindow.INSTANCE};

	public static void hideAllWindows() {

		for (LibWindow frame : allWindows) {
			frame.setVisible(false);

		}
	}

	public ListItem[] libListItems() {
		return librarianItem;
	}

	public ListItem[] adminItems() {
		return adminItem;
	}

	public JList<ListItem> getLinkList() {
		return linkList;
	}

	public JTextArea getStatusBar() {
		return statusBar;
	}

	public void createLinkLabels() {
		DefaultListModel<ListItem> model = new DefaultListModel<>();
		model.addElement(loginLogout);
		model.addElement(addMember);
		model.addElement(searchMemeber);
		model.addElement(checkOutBook);
		model.addElement(addBook);
		model.addElement(addBookCopy);
		model.addElement(checkbookStatus);
		model.addElement(allMemberIds);
		model.addElement(allBookIds);

		linkList = new JList<ListItem>(model);
		linkList.setCellRenderer(new DefaultListCellRenderer() {

			@SuppressWarnings("rawtypes")
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof ListItem) {
					ListItem nextItem = (ListItem) value;
					setText(nextItem.getItemName());
					if (nextItem.highlight()) {
						setForeground(Util.LINK_AVAILABLE);
					} else {
						setForeground(Util.LINK_NOT_AVAILABLE);
					}
					if (isSelected) {
						setForeground(Color.BLACK);
						setBackground(new Color(236, 243, 245));
						// setBackground(Color.WHITE);
					}
				}
				return c;
			}

		});
	}

	private void createMainPanels() {

		AllBookIdsWindow abip;
		AllMemberIdsWindow amiw;
		CheckoutBookWindow cubw;
		AddBookCopyWindow abcw;
		SearchMemberWindow smw;
		CheckBookCopyStatusWindow cbs;
		
		// login
		lp = new LoginPanelWindow();
		JPanel loginPanel = lp.getMainPanel();

		// add book
		AddBookWindow abw = new AddBookWindow();
		JPanel addBookPanel = abw.getMainPanel();

		// add member
		AddMemberWindow amw = AddMemberWindow.INSTANCE;
		JPanel addMemberPanel = amw.getMainPanel();

		// all book IDs
		abip = AllBookIdsWindow.INSTANCE;
		JPanel allBookIdsPanel = abip.getMainPanel();

		// all member IDs
		amiw = AllMemberIdsWindow.INSTANCE;
		JPanel allMemebrIdsPanel = amiw.getMainPanel();

		// checkout panel
		cubw = CheckoutBookWindow.INSTANCE;
		JPanel checkoutBook = cubw.getMainPanel();

		// checkout panel
		abcw = AddBookCopyWindow.INSTANCE;
		JPanel addBookC = abcw.getMainPanel();

		// search member panel
		smw = SearchMemberWindow.INSTANCE;
		JPanel searchMemberPanel = smw.getMainPanel();
		
		//Check Book status
		cbs = CheckBookCopyStatusWindow.INSTANCE;
		JPanel bookStatus = cbs.getMainPanel();

		System.out.println("Test " + addBookPanel);
		cards = new JPanel(new CardLayout());
		cards.add(loginPanel, loginLogout.getItemName());
		cards.add(addBookPanel, addBook.getItemName());
		cards.add(allBookIdsPanel, allBookIds.getItemName());
		cards.add(allMemebrIdsPanel, allMemberIds.getItemName());
		cards.add(addMemberPanel, addMember.getItemName());
		cards.add(checkoutBook, checkOutBook.getItemName());
		cards.add(addBookC, addBookCopy.getItemName());
		cards.add(searchMemberPanel, searchMemeber.getItemName());
		cards.add(bookStatus, checkbookStatus.getItemName());

//        cards.add(allTitlesPanel,  addMember.getItemName());
//        cards.add(addBookPanel, addBook.getItemName());

	}

	public void init() {
//    	formatContentPane();
//    	setPathToImage();
//    	insertSplashImage();
//		
//		createMenus();
		// pack();
		setSize(660, 500);
		isInitialized = true;
	}

//    private void formatContentPane() {
//		mainPanel = new JPanel();
//		mainPanel.setLayout(new GridLayout(1,1));
//		getContentPane().add(mainPanel);	
//	}
//    
//    private void setPathToImage() {
//    	String currDirectory = System.getProperty("user.dir");
//    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
//    }
//    
//    private void insertSplashImage() {
//        ImageIcon image = new ImageIcon(pathToImage);
//		mainPanel.add(new JLabel(image));	
//    }
//    private void createMenus() {
//    	menuBar = new JMenuBar();
//		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
//		addMenuItems();
//		setJMenuBar(menuBar);		
//    }
//    
//    private void addMenuItems() {
//       options = new JMenu("Options");  
// 	   menuBar.add(options);
// 	   login = new JMenuItem("Login");
// 	   login.addActionListener(new LoginListener());
// 	   allBookIds = new JMenuItem("All Book Ids");
// 	   allBookIds.addActionListener(new AllBookIdsListener());
// 	   allMemberIds = new JMenuItem("All Member Ids");
// 	   allMemberIds.addActionListener(new AllMemberIdsListener());
// 	   options.add(login);
// 	   options.add(allBookIds);
// 	   options.add(allMemberIds);
//    }

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginPanelWindow.INSTANCE.init();
			// new LoginPanel();
			Util.centerFrameOnDesktop((Component) LoginPanelWindow.INSTANCE);
			LoginPanelWindow.INSTANCE.setVisible(true);

		}

	}

	class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			// AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class AllMemberIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);

			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			// AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);

		}

	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

}

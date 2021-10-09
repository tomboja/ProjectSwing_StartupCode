package business;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.MessageableWindow;

public class SystemController implements ControllerInterface, MessageableWindow {
	public static Auth currentAuth = null;
	DataAccess da = new DataAccessFacade();

	public void login(String id, String password) throws LoginException {
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			displayError("Login failed!");
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			displayError("Login failed!");
			throw new LoginException("Password incorrect");

		}
		displayInfo("Login SuccessFull!");

		currentAuth = map.get(id).getAuthorization();

	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMember(String street, String city, String state, String zip, String memberId, String fname,
			String lname, String tel) {
		Address address = new Address(street, city, state, zip);
		LibraryMember libMem = new LibraryMember(memberId, fname, lname, tel, address);
		da.saveNewMember(libMem);
	}

	String memeberId = null;

	@Override
	public Boolean memberIdFound(String id) {
		memeberId = id;
		HashMap<String, LibraryMember> map = da.readMemberMap();
		if (!map.containsKey(memeberId)) {
			displayError("Member id not found!");
			return false;
		}
		return true;
	}

	@Override
	public Boolean bookISBNFound(String isbn, String memebrId) {
		HashMap<String, Book> map = da.readBooksMap();
		// map.forEach((key, value) -> System.out.println(key));

		if (!map.containsKey(isbn)) {
			displayError("ISBN not found!");
			return false;
		} else if (!map.get(isbn).isAvailable()) {
			displayError("Book Copy is not found");
			return false;
		}
		return true;
	}

	private LocalDate getBookDueDate(int dueDate) {
		LocalDate date = LocalDate.now();
		int currentMonth = date.getMonthValue();
		YearMonth yearMonthObject = YearMonth.of(date.getYear(), currentMonth);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		int day = dueDate;
		int month = date.getMonthValue();
		int year = date.getYear();
		if (dueDate > daysInMonth) {
			day = dueDate - daysInMonth;
			month = currentMonth + 1;
			if (month > 12) {
				year = date.getYear() + 1;
				month = month - 12;
			}
		}
		return LocalDate.of(year, month, day);

	}

	@Override
	public List<String[]> checkoutBook(String isbn, String memberId) {
		HashMap<String, Book> map = da.readBooksMap();
		BookCopy bookCopy = map.get(isbn).getNextAvailableCopy();
		Book book = map.get(isbn);
		LocalDate date = LocalDate.now();
		int checkoutLength = book.getMaxCheckoutLength();
		int dueDateLength = date.getDayOfMonth() + checkoutLength;
		LocalDate bookDueDate = getBookDueDate(dueDateLength);
		LibraryMember member = da.readMemberMap().get(memeberId);

		// do checkout book
		HashMap<String, CheckoutRecord> recordList = member.checkout(bookCopy, date, bookDueDate);
		List<CheckoutRecordEntry> entry = recordList.get(memberId).getEntryList();

		// update member
		da.updateMemberMap(member, memberId);
		// update book
		da.updateBookMap(book, isbn);

		displayInfo("Book with isbn " + isbn + " is checkedout by member id: " + memberId);

		List<String[]> data = new ArrayList<>();
		for (int i = 0; i < entry.size(); i++) {
			CheckoutRecordEntry c = entry.get(i);
			data.add(new String[] { c.getBookCopy().getBook().getIsbn(), c.getBookCopy().getBook().getTitle(),
					c.getDueDate().toString() });
		}
		return data;
	}

	public void addBookCopy(String isbn, String copyNum) {
		// do checkout book
		HashMap<String, Book> books = da.readBooksMap();
		if (books.containsKey(isbn)) {
			int num = Integer.parseInt(copyNum);
			Book book = books.get(isbn);
			if (book != null) {
				for (int i = 0; i < num; i++) {
					book.addCopy();
				}
				da.updateBookMap(book, isbn);
				displayInfo("Book copy with " + isbn + " and number of copies " + copyNum + " succefully added");
			}
		} else {
			// Uses add new book feature
			displayError("Book ISBN does not exist. Please use Add new book functionality");
		}
	}

	public void searchMember(String id) {
		HashMap<String, LibraryMember> member = da.readMemberMap();
		if (member.containsKey(id)) {
			List<CheckoutRecordEntry> entry = member.get(id).getCheckoutRecord().getEntryList();
			if (entry != null) {
				System.out.println("-------------------- MEMBER ENTRY LIST ------------------");
				System.out.println("--MemberId-------Due Date----------Book Title------------");
				for (CheckoutRecordEntry ent : entry) {
					System.out.println("|      " + id + "       | " + ent.getDueDate() + "     |    "
							+ ent.getBookCopy().getBook().getTitle() + "|");
				}
			}
			displayInfo("Checkout record for member " + id + " is printed in console.");
		} else {
			displayError("Member does not exist. Please add member and checkout book for the member.");
		}
	}

	public List<String[]> searchBook(String id) {
		List<String[]> dueDateInfo = new ArrayList<String[]>();
		String[] dueDateData = new String[4];
		HashMap<String, LibraryMember> member = da.readMemberMap();
		List<LibraryMember> members = new ArrayList<>();
		for (Map.Entry me : member.entrySet()) {
			members.add((LibraryMember) me.getValue());
		}

		for (LibraryMember m : members) {
			CheckoutRecord rec = m.getCheckoutRecord();
			if (rec != null) {
				List<CheckoutRecordEntry> l = rec.getEntryList();
				for (CheckoutRecordEntry entry : l) {
					String bookId = entry.getBookCopy().getBook().getIsbn();
					if (id.equals(bookId)) {
						LocalDate dueDate = entry.getDueDate();
						if (dueDate.isAfter(LocalDate.now())) {
							dueDateData[0] = entry.getBookCopy().getBook().getTitle();
							dueDateData[1] = m.getMemberId();
							dueDateData[2] = String.valueOf(entry.getBookCopy().getCopyNum());
							dueDateData[3] = entry.getDueDate().toString();
						}
						dueDateInfo.add(dueDateData);
					}
				}
			}

		}
		return dueDateInfo;

	}

	public Boolean addBook(String isbn, String title, String checkoutLen, String numberOfCopies, Author auth) {
		int num = Integer.parseInt(numberOfCopies);
		List<Author> authors = new ArrayList<>();
		authors.add(auth);
		Book book = new Book(isbn, title, num, authors);
		book.addCopy();
		return true;
	}
}

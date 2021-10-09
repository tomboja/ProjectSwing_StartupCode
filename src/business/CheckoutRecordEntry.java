package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = 6110690276635962829L;
	private LocalDate dueDate;
	private LocalDate checkoutDate;
	private CheckoutRecord checkoutrecord;
	private BookCopy bookCopy;

	public CheckoutRecordEntry(BookCopy bookCopy, LocalDate checkoutDate, LocalDate duedate) {
		this.dueDate = duedate;
		this.checkoutDate = checkoutDate;
		this.bookCopy = bookCopy;
		//this.checkoutrecord = checkrecord;
	}

	/**
	 * @return the dueDate
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * @return the checkoutDate
	 */
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	/**
	 * @return the checkoutrecord
	 */
	public CheckoutRecord getCheckoutrecord() {
		return checkoutrecord;
	}

	/**
	 * @return the bookCopy
	 */
	public BookCopy getBookCopy() {
		return bookCopy;
	}

}

package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;	
		checkoutRecord = new CheckoutRecord(this);
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}
	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}


	public HashMap<String, CheckoutRecord> checkout(BookCopy bookCopy, LocalDate date, LocalDate bookDueDate) {
		bookCopy.changeAvailability();
		CheckoutRecordEntry entry = new CheckoutRecordEntry(bookCopy, date, bookDueDate);
		checkoutRecord.addCheckoutEntry(entry);
		bookCopy.changeAvailability();
		DataAccessFacade da = new DataAccessFacade();
		da.loadCheckoutEntryMap(checkoutRecord);
		return da.readCheckoutRecordMap();
	}
	
	
	private static final long serialVersionUID = -2226197306790714013L;
}

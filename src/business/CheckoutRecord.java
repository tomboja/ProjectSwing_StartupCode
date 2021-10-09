package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {

	private static final long serialVersionUID = 6110690276685962859L;
	List<CheckoutRecordEntry> entryList;
	LibraryMember member;

	// package level constructor
	CheckoutRecord(LibraryMember member) {
		this.member = member;
		entryList = new ArrayList<>();
	}

	/**
	 * @return the entryList
	 */
	public List<CheckoutRecordEntry> getEntryList() {
		return entryList;
	}

	/**
	 * @return the member
	 */
	public LibraryMember getMember() {
		return member;
	}

	public void addCheckoutEntry(CheckoutRecordEntry entry) {
		entryList.add(entry);
		
	}
}

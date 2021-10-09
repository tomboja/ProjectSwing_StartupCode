package business;

import java.awt.Component;

import librarysystem.AddBookWindow;
import librarysystem.AddMemberWindow;

public class ValidationUtils {

	public static String checkNonEmptyField(LibraryMember s) {
		String firstName = s.getFirstName().trim();
		String lastName = s.getLastName().trim();
		String phoneNumber = s.getTelephone().trim();
		String memberId = s.getMemberId().trim();
//		
//		if (firstName.length() != 0 && lastName.length() != 0 && phoneNumber.length() != 0 && memberId.length() != 0) {
//			// Search member if it is already available
//			String id = da.searchMember(memberId);
//			if (!id.equals(memberId)) {
//				da.saveNewMember(libraryMember);
//			} else {
//				// throw
//			}
//		}
		return null;
	}

	public static String validateAddress(String street, String city, String state, String zip)
			throws NumberFormatException {
		try {
			Integer.parseInt(zip);
		} catch (Exception e) {
			throw new NumberFormatException("Zip code can only be numeric");
		}
		if (street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
			return "data can not be empty";
		}
		return null;
	}

	public static String validateMemberData(String memberId, String fname, String lname, String tel)
			throws NumberFormatException {
		try {
			Integer.parseInt(memberId);
			Integer.parseInt(tel);
		} catch (Exception e) {
			throw new NumberFormatException("member id and telephone can only be numbers");
		}
		if (memberId.isEmpty() || fname.isEmpty() || lname.isEmpty() || tel.isEmpty()) {
			return "data can not be empty";
		}
		return null;
	}

}

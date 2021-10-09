package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addMember(String street, String city, String state, String zip, String memberId, String fname,
			String lname, String tel);
	public Boolean bookISBNFound(String isbn,String memebrid);
	public Boolean memberIdFound(String memberId);
	public List<String[]> checkoutBook(String isbn,String memberId);
	public void addBookCopy(String isbn, String copyNum);
	public List<String[]> searchBook(String id);
	
}

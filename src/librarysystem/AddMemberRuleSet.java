package librarysystem;

import java.awt.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import librarysystem.AddMemberWindow;


/**
 * Rules:
 * 1. All fields must be nonempty
 * 2. State should be 2 letters upper case
 * 3. Zip code should be 5 digit numbers
 * 4. Phone number must be in a format
 */

public class AddMemberRuleSet implements RuleSet {
    private AddMemberWindow addMemberWindow;

    @Override
    public void applyRules(Component ob) throws RuleException {
        addMemberWindow = (AddMemberWindow) ob;
        
        nonemptyRule();
        validSateRule();
        zipCodeDigitRule();
        validPhoneNumberRule();
    }

    private void nonemptyRule() throws RuleException {
        if (addMemberWindow.getmemberTextField().isEmpty() || addMemberWindow.getNameTextFeild().trim().isEmpty() || addMemberWindow.getLastname().trim().isEmpty() ||
                addMemberWindow.getStreetTextField().trim().isEmpty() || addMemberWindow.getCityTextField().isEmpty() || addMemberWindow.getStateTextField().isEmpty() ||
         addMemberWindow.getZipTextField().isEmpty() || addMemberWindow.getPhoneTexField().isEmpty()) {
            throw new RuleException("All fields must be non-empty!");
        }
    }

    private void zipCodeDigitRule () throws RuleException {
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(addMemberWindow.getZipTextField());
        if(!matcher.matches()) throw new RuleException("Zip code must be a 5 digit number!");
    }

    private void validSateRule() throws RuleException {
        Pattern pattern = Pattern
                .compile("AL|AK|AR|AZ|CA|CO|CT|DC|DE|FL|GA|HI|IA|ID|IL|IN|KS|KY|LA|MA|MD|ME|MI|MN|MO|MS|MT|NC|ND|NE|NH|NJ|NM|NV|NY|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VA|VT|WA|WI|WV|WY|al|ak|ar|az|ca|co|ct|dc|de|fl|ga|hi|ia|id|il|in|ks|ky|la|ma|md|me|mi|mn|mo|ms|mt|nc|nd|ne|nh|nj|nm|nv|ny|oh|ok|or|pa|ri|sc|sd|tn|tx|ut|va|vt|wa|wi|wv|wy");
        Matcher matcher = pattern.matcher(addMemberWindow.getStateTextField());
        if(!matcher.matches()) throw new RuleException("Invalid state!");
    }

    private void validPhoneNumberRule() throws RuleException {

        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(addMemberWindow.getPhoneTexField());
        if(!matcher.matches()) throw new RuleException("Phone number must be a 10 digit number!");
    }

}

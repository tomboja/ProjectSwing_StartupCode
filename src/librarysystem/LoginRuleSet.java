package librarysystem;

import java.awt.Component;

/**
* Rules:
* 1. All fields must be nonempty
*/
public class LoginRuleSet implements RuleSet {

   private LoginPanelWindow loginWindow;

   @Override
   public void applyRules(Component ob) throws RuleException {
       loginWindow = (LoginPanelWindow)ob;
       nonemptyRule();
   }

   private void nonemptyRule() throws RuleException {
       if (loginWindow.getusername().trim().isEmpty() ||
               loginWindow.getpassword().trim().isEmpty()) {
           throw new RuleException("All fields must be non-empty!");
      }
   }



}
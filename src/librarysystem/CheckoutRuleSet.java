package librarysystem;



import java.awt.*;

/**
 * Rules:
 * 1. All fields must be nonempty
 */
public class CheckoutRuleSet implements RuleSet {
    private CheckoutBookWindow checkOutBookWindow;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkOutBookWindow = (CheckoutBookWindow)ob;
    }

    private void nonemptyRule() throws RuleException {
        if (checkOutBookWindow.getisbn().trim().isEmpty() ||
                checkOutBookWindow.getmemberid().trim().isEmpty()) {
            throw new RuleException("All fields must be non-empty!");
       }
    }
}

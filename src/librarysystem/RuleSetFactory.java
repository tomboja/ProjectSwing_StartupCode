package librarysystem;

import librarysystem.AddMemberWindow;

import java.awt.*;
import java.util.HashMap;

final public class RuleSetFactory {
    private RuleSetFactory(){}
    static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
    static {
        map.put(AddMemberWindow.class, new AddMemberRuleSet());
        map.put(LoginPanelWindow.class, new LoginRuleSet());
        map.put(CheckoutBookWindow.class, new CheckoutRuleSet());
    }
    public static RuleSet getRuleSet(Component c) {
        Class<? extends Component> cl = c.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException(
                    "No RuleSet found for this Component");
        }
        return map.get(cl);
    }
}

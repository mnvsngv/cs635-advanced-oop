package email;

import java.util.HashMap;
import java.util.Map;


/*
 * Class to encapsulate getting the email ID of a mobile operator for sending
 * SMS texts.
 */
public class OperatorEmailProvider {

    private static final Map<String, String> OPERATOR_MAP = new HashMap<>();


    static {
        OPERATOR_MAP.put("tmobile", "tmomail.net");
        OPERATOR_MAP.put("att", "mms.att.net");
        OPERATOR_MAP.put("verizon", "vzwpix.com");
        OPERATOR_MAP.put("sprint", "pm.sprint.com");
    }


    public static String getEmailFor(String operator) {
        return OPERATOR_MAP.get(operator);
    }
}

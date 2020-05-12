package net.comorevi.np.sma.command;

import net.comorevi.np.sma.util.MailData;

import java.util.LinkedHashMap;
import java.util.Map;

public class SendMailQueue {
    public static Map<String, MailData> queue = new LinkedHashMap<>();
}

package net.comorevi.np.sma.resource;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.util.LanguageUtil;

import java.io.File;

public class MessageHandler {
    private final Config message;
    private static final MessageHandler instance = new MessageHandler();

    private MessageHandler() {
        ServerMailPlugin.getInstance().saveResource("lang/" + LanguageUtil.getPluginLang() + "/lang.properties", "lang.properties", false);
        File file = new File("./plugins/ServerMail", "lang.properties");
        message = new Config(file, Config.PROPERTIES);
    }

    public String translateString(String key) {
        if (!message.exists(key)) {
            return TextFormat.RED + message.getString("servermail.plugin.warn.message.not.found", "The message is not found. Try regenerate lang.properties file.");
        } else {
            return message.getString(key);
        }
    }

    public String translateString(String key, String... values) {
        if (!message.exists(key)) {
            return TextFormat.RED + message.getString("servermail.plugin.warn.message.not.found", "The message is not found. Try regenerate lang.properties file.");
        } else {
            String str = message.getString(key);
            if (values.length == 1) {
                return str.replace("{%0}", values[0]);
            } else {
                for (int i = 0; i < values.length; i++) {
                    str = str.replace("{%"+i+"}", values[i]);
                }
                return str;
            }
        }
    }

    public Config getMessage() {
        return message;
    }

    public static MessageHandler getInstance() {
        return instance;
    }
}

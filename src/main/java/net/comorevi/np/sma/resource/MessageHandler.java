package net.comorevi.np.sma.resource;

import cn.nukkit.Server;
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
        message = new Config(new File("./plugins/ServerMailAPI", "lang.properties"), Config.PROPERTIES);
    }

    public String translateString(String key) {
        if (!message.exists(key)) {
            return TextFormat.RED + "The message is not found.";
        } else {
            return message.getString(key);
        }
    }

    public String translateString(String key, String... values) {
        if (!message.exists(key)) {
            return TextFormat.RED+"The message is not found";
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

    public static MessageHandler getInstance() {
        return instance;
    }
}

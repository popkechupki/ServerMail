package net.comorevi.np.sma.util;

import cn.nukkit.Server;

import java.util.LinkedList;
import java.util.List;

public class LanguageUtil {
    private static final LinkedList<String> langList = new LinkedList<String>(){{add("eng");add("jpn");}};

    public static List<String> getAvailableLangList() {
        return langList;
    }

    public static String getPluginLang() {
        return langList.contains(Server.getInstance().getLanguage().getLang()) ? Server.getInstance().getLanguage().getLang() : "eng";
    }
}

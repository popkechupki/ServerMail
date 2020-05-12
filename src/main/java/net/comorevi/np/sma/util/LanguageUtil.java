package net.comorevi.np.sma.util;

import cn.nukkit.Server;

import java.util.List;

public class LanguageUtil {
    private static final List<String> langList = List.of("eng", "jpn");

    public static List<String> getAvailableLangList() {
        return langList;
    }

    public static String getPluginLang() {
        return langList.contains(Server.getInstance().getLanguage().getLang()) ? Server.getInstance().getLanguage().getLang() : "eng";
    }
}

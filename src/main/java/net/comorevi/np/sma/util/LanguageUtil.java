package net.comorevi.np.sma.util;

import cn.nukkit.Server;

import java.util.Arrays;
import java.util.List;

public class LanguageUtil {
    private static String[] availableLanguages = {"eng", "jpn", "kor"};

    public static List<String> getAvailableLangList() {
        return Arrays.asList(availableLanguages);
    }

    public static String getPluginLang() {
        return getAvailableLangList().contains(Server.getInstance().getLanguage().getLang()) ? Server.getInstance().getLanguage().getLang() : "eng";
    }
}

package net.comorevi.np.sma.resource;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.util.LanguageUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigHandler {
    private static final int CONFIG_VERSION = 1;
    private Config config;
    private static final ConfigHandler instance = new ConfigHandler();

    private ConfigHandler() {
        ServerMailPlugin.getInstance().saveResource("lang/" + LanguageUtil.getPluginLang() + "/config.yml", "config.yml", false);
        config = new Config(new File("./plugins/ServerMail", "config.yml"), Config.YAML);
        if (config.getInt("version") < CONFIG_VERSION) {
            Map<String, Object> configMap = new LinkedHashMap<>();
            config.getKeys().forEach(key -> configMap.put(key, config.get(key)));
            ServerMailPlugin.getInstance().saveResource("config.yml", true);
            configMap.keySet().forEach(key -> config.set(key, configMap.get(key)));
            configMap.clear();
            config.set("version", CONFIG_VERSION);
            Server.getInstance().getLogger().warning(ServerMailPlugin.prefix + "Configuration file has updated.");
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(String key, Object value) {
        config.set(key, value);
        config.save();
    }

    public static ConfigHandler getInstance() {
        return instance;
    }
}

package net.comorevi.np.sma.resource;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.util.LanguageUtil;

import java.io.File;

public class ConfigHandler {
    private static final int CONFIG_VERSION = 1;
    private Config config;
    private static final ConfigHandler instance = new ConfigHandler();

    private ConfigHandler() {
        ServerMailPlugin.getInstance().saveResource("lang/" + LanguageUtil.getPluginLang() + "/config.yml", "config.yml", false);
        config = new Config(new File("./plugins/ServerMailAPI", "config.yml"), Config.YAML);
        if (config.getInt("version") < CONFIG_VERSION) {
            Server.getInstance().getLogger().warning(ServerMailPlugin.prefix + "Please delete old config file.");
            Server.getInstance().getPluginManager().disablePlugin(ServerMailPlugin.getInstance());
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

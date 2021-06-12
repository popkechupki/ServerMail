package net.comorevi.np.sma.resource;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.util.LanguageUtil;

import java.io.File;

public class ConfigHandler {
    private static final int CONFIG_VERSION = 2;
    private final Config config;
    private static final ConfigHandler instance = new ConfigHandler();
    private boolean isReady = true;

    private ConfigHandler() {
        ServerMailPlugin.getInstance().saveResource("lang/" + LanguageUtil.getPluginLang() + "/config.yml", "config.yml", false);
        File configFile= new File("./plugins/ServerMail", "config.yml");
        config = new Config(configFile, Config.YAML);
        if (config.getInt("version") < CONFIG_VERSION) {
            configFile.renameTo(new File("./plugins/ServerMail", "old_config.yml"));
            ServerMailPlugin.getInstance().saveResource("lang/" + LanguageUtil.getPluginLang() + "/config.yml", "config.yml", true);
            Server.getInstance().getLogger().warning(config.getString("prefix") + TextFormat.RED + TextFormat.BOLD + "The configuration file has been updated. The old file is stored as old_config.yml.");
            Server.getInstance().getLogger().warning(config.getString("prefix") + TextFormat.RED + TextFormat.BOLD + "Stop the server and edit the config.yml file.");
            isReady = false;
        }
    }

    public Config getConfig() {
        return config;
    }

    public boolean isReady() {
        return isReady;
    }

    public static ConfigHandler getInstance() {
        return instance;
    }
}

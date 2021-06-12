package net.comorevi.np.sma;

import cn.nukkit.plugin.PluginBase;
import net.comorevi.np.sma.command.DeleteMailCommand;
import net.comorevi.np.sma.command.ListMailCommand;
import net.comorevi.np.sma.command.ReadMailCommand;
import net.comorevi.np.sma.command.SendMailCommand;
import net.comorevi.np.sma.resource.ConfigHandler;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.DataProvider;
import net.comorevi.np.sma.event.MailNotifier;
import net.comorevi.np.sma.util.DatabaseType;

public class ServerMailPlugin extends PluginBase {
    public static String prefix;
    private static ServerMailPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        prefix = ConfigHandler.getInstance().getConfig().getString("prefix");
        DatabaseType dbType = ConfigHandler.getInstance().getConfig().getInt("SQLType", 0) == 0 ? DatabaseType.SQLITE : DatabaseType.MYSQL;
        DataProvider.getInstance().connectSQL(dbType);
        getServer().getPluginManager().registerEvents(new MailNotifier(), this);
        getServer().getCommandMap().register("sendmail", new SendMailCommand("sendmail", MessageHandler.getInstance().translateString("servermail.command.send.description"), MessageHandler.getInstance().translateString("servermail.command.send.usage")));
        getServer().getCommandMap().register("readmail", new ReadMailCommand("readmail", MessageHandler.getInstance().translateString("servermail.command.read.description"), MessageHandler.getInstance().translateString("servermail.command.read.usage")));
        getServer().getCommandMap().register("listmail", new ListMailCommand("listmail", MessageHandler.getInstance().translateString("servermail.command.list.description"), MessageHandler.getInstance().translateString("servermail.command.list.usage")));
        getServer().getCommandMap().register("deletemail", new DeleteMailCommand("deletemail", MessageHandler.getInstance().translateString("servermail.command.delete.description"), MessageHandler.getInstance().translateString("servermail.command.delete.usage")));
        if (ServerMailAPI.getConfigUtil().isReady()) {
            getServer().getLogger().info(prefix + ServerMailAPI.getMessageUtil().translateString("servermail.plugin.info.sqltype", dbType.getStr()));
        } else {
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        DataProvider.getInstance().disconnectSQL();
    }

    public static ServerMailPlugin getInstance() {
        return instance;
    }
}

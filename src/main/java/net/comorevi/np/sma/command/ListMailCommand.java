package net.comorevi.np.sma.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import net.comorevi.np.sma.ServerMailAPI;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.resource.ConfigHandler;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.MailData;

import java.util.List;

// listmail <page>
public class ListMailCommand extends Command {
    public ListMailCommand(String name, String description, String usageMessage) {
        super(name, description, usageMessage);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.only.in.game"));
            return true;
        }
        if (!commandSender.hasPermission("servermailapi.command.list")) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission"));
            return true;
        }
        if (!ServerMailAPI.getInstance().existsMailData(commandSender.getName())) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.mail.not.found"));
            return true;
        }

        int page = strings.length == 0 ? 1 : Integer.parseInt(strings[0]);
        List<MailData> mailBox = ServerMailAPI.getInstance().getMailBox(commandSender.getName());
        int pages = mailBox.size() < ConfigHandler.getInstance().getConfig().getInt("mailList") ? 1 : mailBox.size() % ConfigHandler.getInstance().getConfig().getInt("mailList") == 0 ? mailBox.size() / ConfigHandler.getInstance().getConfig().getInt("mailList") : (mailBox.size() / ConfigHandler.getInstance().getConfig().getInt("mailList")) + 1;
        if (page <= 1 || pages < page) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.mail.list", String.valueOf(1), String.valueOf(pages)));
            mailBox.subList(0, Math.min(mailBox.size(), ConfigHandler.getInstance().getConfig().getInt("mailList"))).forEach(mailData -> {
                commandSender.sendMessage((mailData.read ? "§r" : "§6") + ">" + mailData.databaseId + ": " + mailData.subject + " (from " + mailData.sender + ")");
            });
        } else {
            int index = (Integer.parseInt(strings[0]) - 1) * ConfigHandler.getInstance().getConfig().getInt("mailList");
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.mail.list", strings[0], String.valueOf(pages)));
            mailBox.subList(index, index + (Integer.parseInt(strings[0]) == pages ? (mailBox.size() % ConfigHandler.getInstance().getConfig().getInt("mailList") == 0 ? ConfigHandler.getInstance().getConfig().getInt("mailList") : mailBox.size() % ConfigHandler.getInstance().getConfig().getInt("mailList")) : ConfigHandler.getInstance().getConfig().getInt("mailList"))).forEach(mailData -> {
                commandSender.sendMessage((mailData.read ? "§r" : "§6") + ">" + mailData.databaseId + ": " + mailData.subject + " (from " + mailData.sender + ")");
            });
        }
        return true;
    }
}

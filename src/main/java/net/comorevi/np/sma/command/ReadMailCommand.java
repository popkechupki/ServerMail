package net.comorevi.np.sma.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import net.comorevi.np.sma.ServerMailAPI;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.MailData;

// readmail <id>
public class ReadMailCommand extends Command {
    public ReadMailCommand(String name, String description, String usageMessage) {
        super(name, description, usageMessage);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.only.in.game"));
            return true;
        }
        if (!commandSender.hasPermission("servermailapi.command.read")) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission"));
            return true;
        }
        if (strings.length < 1) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.few.args"));
            return false;
        }
        if (!(ServerMailAPI.getInstance().existsMailData(Integer.parseInt(strings[0])) && ServerMailAPI.getInstance().getMailData(Integer.parseInt(strings[0])).target.equals(commandSender.getName()))) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission.read"));
            return true;
        }

        MailData mailData = ServerMailAPI.getInstance().getMailData(Integer.parseInt(strings[0]));
        commandSender.sendMessage(ServerMailPlugin.prefix + TextFormat.GOLD + mailData.subject + TextFormat.RESET + "\n>" + mailData.content + TextFormat.GRAY + "\nID: " + mailData.databaseId + " FROM: " + mailData.sender);
        mailData.read = true;
        ServerMailAPI.getInstance().setMailData(Integer.parseInt(strings[0]), mailData);
        return true;
    }
}

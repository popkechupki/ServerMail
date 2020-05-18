package net.comorevi.np.sma.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import net.comorevi.np.sma.ServerMailAPI;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.resource.MessageHandler;

// deletemail <id>
public class DeleteMailCommand extends Command {
    public DeleteMailCommand(String name, String description, String usageMessage) {
        super(name, description, usageMessage);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.only.in.game"));
            return true;
        }
        if (!commandSender.hasPermission("servermailapi.command.delete")) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission"));
            return true;
        }
        if (strings.length < 1) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.few.args"));
            return false;
        }
        if (!ServerMailAPI.getInstance().existsMailData(Integer.parseInt(strings[0]))) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.mail.not.found"));
            return true;
        }
        if (!ServerMailAPI.getInstance().getMailData(Integer.parseInt(strings[0])).target.equals(commandSender.getName())) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission"));
            return true;
        }

        ServerMailAPI.getInstance().deleteMail(Integer.parseInt(strings[0]));
        commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.delete"));
        return false;
    }
}

package net.comorevi.np.sma.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.comorevi.np.sma.ServerMailAPI;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.resource.ConfigHandler;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.MailData;

// sendmail <target> <subject> <content>
public class SendMailCommand extends Command {
    public SendMailCommand(String name, String description, String usageMessage) {
        super(name, description, usageMessage, new String[]{"send"});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.hasPermission("servermailapi.command.send")) {
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.permission"));
            return true;
        }
        if (strings.length < 3) {
            String sender = commandSender instanceof Player ? commandSender.getName() : ConfigHandler.getInstance().getConfig().getString("consoleCommandSenderName");
            if (SendMailQueue.queue.containsKey(sender)) {
                ServerMailAPI.getInstance().sendMail(SendMailQueue.queue.get(sender));
                commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.send", SendMailQueue.queue.get(sender).target));
                SendMailQueue.queue.remove(sender);
                return true;
            }
            commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.command.few.args"));
            return false;
        }

        String sender = commandSender instanceof Player ? commandSender.getName() : ConfigHandler.getInstance().getConfig().getString("consoleCommandSenderName");
        StringBuilder message = new StringBuilder();
        for (int i = 2; i < strings.length; i++) {
            if (i == 2) {
                message.append(strings[i]);
            } else {
                message.append(" ").append(strings[i]);
            }
        }
        MailData mailData = new MailData(strings[1], message.toString(), sender, strings[0]);
        SendMailQueue.queue.put(sender, mailData);
        commandSender.sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.send.confirm", mailData.target));
        return true;
    }
}

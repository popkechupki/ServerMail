package net.comorevi.np.sma;

import cn.nukkit.Player;
import cn.nukkit.Server;
import net.comorevi.np.sma.event.plugin.PlayerMailReceiveEvent;
import net.comorevi.np.sma.resource.ConfigHandler;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.DataProvider;
import net.comorevi.np.sma.util.MailData;

import java.util.List;

public class ServerMailAPI {
    private static final ServerMailAPI instance = new ServerMailAPI();
    private static final ConfigHandler configUtil = ConfigHandler.getInstance();
    private static final MessageHandler messageUtil = MessageHandler.getInstance();

    public boolean existsMailData(int id) {
        return DataProvider.getInstance().existsMail(id);
    }

    public boolean existsMailData(String target) {
        return DataProvider.getInstance().existsMail(target);
    }

    public void sendMail(String subject, String content, String sender, String target) {
        Player player = Server.getInstance().getPlayer(target);
        if (player != null) {
            PlayerMailReceiveEvent e = new PlayerMailReceiveEvent(player);
            Server.getInstance().getPluginManager().callEvent(e);
            if (!e.isCancelled()) {
                DataProvider.getInstance().addMail(new MailData(subject, content, sender, target));
            }
        } else {
            DataProvider.getInstance().addMail(new MailData(subject, content, sender, target));
        }
    }

    public void sendMail(MailData mailData) {
        sendMail(mailData.subject, mailData.content, mailData.sender, mailData.target);
    }

    public void deleteMail(int id) {
        DataProvider.getInstance().removeMail(id);
    }

    public MailData getMailData(int id) {
        return DataProvider.getInstance().getMail(id);
    }

    public void setMailData(int id, MailData mailData) {
        DataProvider.getInstance().setMail(id, mailData);
    }

    public List<MailData> getMailBox(String target) {
        return DataProvider.getInstance().getMailBox(target);
    }

    public static ServerMailAPI getInstance() {
        return instance;
    }

    public static ConfigHandler getConfigUtil() {
        return configUtil;
    }

    public static MessageHandler getMessageUtil() {
        return messageUtil;
    }
}

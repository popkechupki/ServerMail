package net.comorevi.np.sma.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import net.comorevi.np.sma.ServerMailAPI;
import net.comorevi.np.sma.ServerMailPlugin;
import net.comorevi.np.sma.event.plugin.PlayerMailReceiveEvent;
import net.comorevi.np.sma.resource.ConfigHandler;
import net.comorevi.np.sma.resource.MessageHandler;
import net.comorevi.np.sma.util.MailData;

public class MailNotifier implements Listener {
    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof SetLocalPlayerAsInitializedPacket && ConfigHandler.getInstance().getConfig().getBoolean("noticeWhenPlayerJoin")) {
            int unread = 0;
            for (MailData mailData : ServerMailAPI.getInstance().getMailBox(event.getPlayer().getName())) {
                if (!mailData.read) unread += 1;
            }
            if (unread != 0) event.getPlayer().sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.event.join", String.valueOf(unread)));
        }
    }

    @EventHandler
    public void onPlayerMailReceive(PlayerMailReceiveEvent event) {
        if (ConfigHandler.getInstance().getConfig().getBoolean("noticeWhenPlayerReceive")) {
            event.getPlayer().sendMessage(ServerMailPlugin.prefix + MessageHandler.getInstance().translateString("servermail.event.receive"));
        }
    }
}

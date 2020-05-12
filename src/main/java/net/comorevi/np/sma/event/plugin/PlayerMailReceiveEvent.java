package net.comorevi.np.sma.event.plugin;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerMailReceiveEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerMailReceiveEvent(Player player) {
        this.eventName = "PlayerMailReceiveEvent";
        this.player = player;
    }
}

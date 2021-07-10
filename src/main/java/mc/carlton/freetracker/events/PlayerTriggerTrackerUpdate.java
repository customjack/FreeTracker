package mc.carlton.freetracker.events;

import mc.carlton.freetracker.tracking.Tracker;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTriggerTrackerUpdate extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player player;
    Tracker tracker;

    public PlayerTriggerTrackerUpdate(Player player, Tracker tracker){
        this.player = player;
        this.tracker = tracker;
    }

    public Player getPlayer() {
        return player;
    }

    public Tracker getTracker() {
        return tracker;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

package mc.carlton.freetracker.events;

import mc.carlton.freetracker.tracking.Tracker;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class TrackingItemMoved extends Event {
    private static final HandlerList handlers = new HandlerList();
    ItemStack itemStack;
    Tracker tracker;

    public TrackingItemMoved(Tracker tracker, ItemStack itemStack){
        this.itemStack = itemStack;
        this.tracker = tracker;
    }

    public ItemStack getItemStack() {
        return itemStack;
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

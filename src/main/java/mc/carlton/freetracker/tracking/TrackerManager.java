package mc.carlton.freetracker.tracking;

import mc.carlton.freetracker.events.PlayerTriggerTrackerCycle;
import mc.carlton.freetracker.events.PlayerTriggerTrackerUpdate;
import mc.carlton.freetracker.utilities.UtilityMethods;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TrackerManager implements Listener {
    private final static PersistentDataType DATA_TYPE = PersistentDataType.STRING;
    private static final String DISPLAY_ON_HOLD_PATH = "trackerMechanics.displayOnHold";

    private static HashMap<NamespacedKey,Tracker> trackers;
    private static boolean areTrackersInitialized;
    private static HashSet<Player> playersHoldingTracker;

    public TrackerManager() {
        if (!areTrackersInitialized) {
            initializeTrackers();
        }
    }

    private void initializeTrackers() {
        this.trackers = new HashMap<>();
        this.playersHoldingTracker = new HashSet<>();
        this.areTrackersInitialized = true;
    }

    public void addTracker(ItemStack trackerItem, NamespacedKey key) {
        trackerItem.getItemMeta().getPersistentDataContainer().set(key, DATA_TYPE,"freeTracker-null");
        Tracker tracker = new Tracker(trackerItem);
        trackers.put(key,tracker);
    }

    private void removeTracker(NamespacedKey key) {
        if (trackers.containsKey(key)) {
            Tracker tracker = trackers.get(key);
            tracker.removeTarget();
            tracker.getTrackingItem().getItemMeta().getPersistentDataContainer().remove(key);
            trackers.remove(key);
        }
    }

    public void removeTracker(ItemStack item) {
        NamespacedKey key = getKeyFromItemStack(item);
        if (key != null) {
            removeTracker(key);
        }
    }

    public boolean isItemTracker(ItemStack itemStack) {
        for (NamespacedKey key : trackers.keySet()) {
            if (itemStack.getItemMeta().getPersistentDataContainer().has(key,DATA_TYPE)) {
                return true;
            }
        }
        return false;
    }

    public Tracker getTrackerFromItemStack(ItemStack itemStack) {
        NamespacedKey key = getKeyFromItemStack(itemStack);
        return (key != null) ? trackers.get(key) : null;
    }

    private NamespacedKey getKeyFromItemStack(ItemStack itemStack) {
        for (NamespacedKey key : trackers.keySet()) {
            if (itemStack.getItemMeta().getPersistentDataContainer().has(key,DATA_TYPE)) {
                return key;
            }
        }
        return null;
    }

    public void addPlayerHoldingTracker(Player p) {
        playersHoldingTracker.add(p);
    }

    public void removePlayerHoldingTracker(Player p) {
        playersHoldingTracker.remove(p);
    }

    public void updateTrackerInformation() {

    }

    public void updateTrackerNeedles() {
        for (NamespacedKey key : trackers.keySet()) {
        }
    }

    @EventHandler
    public void updateTrackerEvent(PlayerTriggerTrackerUpdate e) {
        Player player = e.getPlayer();
        Tracker tracker = e.getTracker();
        tracker.determineNewTarget(player,false);
        tracker.updateTrackerInformation(player);
    }

    @EventHandler
    public void cycleTrackerEvent(PlayerTriggerTrackerCycle e) {
        // Changes target and updates
        Player player = e.getPlayer();
        Tracker tracker = e.getTracker();
        tracker.determineNewTarget(player,true);
        tracker.updateTrackerInformation(player);
    }




}

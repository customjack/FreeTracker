package mc.carlton.freetracker.tracking;

import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class TrackerManager {
    private static HashSet<Tracker> trackers;
    private boolean areTrackersInitialized;

    public TrackerManager() {
        if (!areTrackersInitialized) {
            initializeTrackers();
        }
    }

    private void initializeTrackers() {
        this.trackers = new HashSet<>();
        this.areTrackersInitialized = true;
    }

    public boolean isItemATracker(ItemStack item) {
        if (getTrackerItemStacks().contains(item)) {
            return  true;
        } else {
            return false;
        }
    }

    private HashSet<ItemStack> getTrackerItemStacks() {
        HashSet<ItemStack> trackerItems = new HashSet<>();
        for (Tracker tracker : trackers) {
            trackerItems.add(tracker.getTrackingItem());
        }
        return trackerItems;
    }


}

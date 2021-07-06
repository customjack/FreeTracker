package mc.carlton.freetracker.tracking;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tracker {
    private ItemStack trackingItem;
    private Player target;
    private double distance;
    private double target_x;
    private double target_y;
    private double target_z;
    private String targetName;

    public Tracker(ItemStack trackingItem) {
        this.trackingItem = trackingItem;
    }

    public ItemStack getTrackingItem() {
        return trackingItem;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void updateTargetInformation(Player trackingPlayer) {
        if (target != null) {
            if (target.isOnline()) {
                if (target.getWorld().equals(trackingPlayer.getWorld())) {
                    this.target_x = target.getLocation().getX();
                    this.target_y = target.getLocation().getY();
                    this.target_z = target.getLocation().getZ();
                    this.distance = trackingPlayer.getLocation().distance(target.getLocation());
                    this.targetName = target.getDisplayName();
                }
            }
        }
    }

    public void

}

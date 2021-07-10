package mc.carlton.freetracker.tracking;

import mc.carlton.freetracker.Freetracker;
import mc.carlton.freetracker.lang.LanguageManager;
import mc.carlton.freetracker.utilities.ActionBarMessage;
import mc.carlton.freetracker.utilities.UtilityMethods;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;

public class Tracker {
    private static final String DISPLAY_INFORMATION_PATH = "displayedInformation.displayPlayerInformationText";
    private static final String DISPLAY_INFORMATION_DISTANCE_PATH = "displayedInformation.displayDistance";
    private static final String DISPLAY_INFORMATION_NAME_PATH = "displayedInformation.displayTargetName";
    private static final String DISPLAY_INFORMATION_COORDINATES_PATH = "displayedInformation.displayTargetCoordinates";

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

    public void setTrackingItem(ItemStack trackingItem) {
        this.trackingItem = trackingItem;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    public void determineNewTarget(Player trackingPlayer, boolean isCycle) {
        Location playerLocation = trackingPlayer.getLocation();
        ArrayList<Player> organizedList = (isCycle) ? UtilityMethods.getPlayersSortedByName(playerLocation) : UtilityMethods.getPlayersSortedByName(playerLocation);
        if (organizedList.contains(trackingPlayer)) {
            organizedList.remove(trackingPlayer); //removes user from the list
        }
        if (target != null && organizedList.contains(target)) {
            if (!organizedList.isEmpty()) {
                int currentIndex = organizedList.indexOf(target);
                setTarget(UtilityMethods.perioidicGetNext(organizedList,currentIndex));
            }
        } else {
            if (!organizedList.isEmpty()) {
                setTarget(organizedList.get(0));
            }
        }
    }

    public void updateTrackerInformation(Player trackingPlayer) {
        updateTargetInformation(trackingPlayer);
        updateTrackerNeedle(trackingPlayer);
        updateTrackerMessage(trackingPlayer);
    }

    public void updateTargetInformation(Player trackingPlayer) {
        if (targetInGame(trackingPlayer)) {
            this.target_x = target.getLocation().getX();
            this.target_y = target.getLocation().getY();
            this.target_z = target.getLocation().getZ();
            this.distance = trackingPlayer.getLocation().distance(target.getLocation());
            this.targetName = target.getDisplayName();
        }
    }

    private boolean targetInGame(Player trackingPlayer) {
        if (target != null) {
            if (target.isOnline()) {
                if (target.getWorld().equals(trackingPlayer.getWorld())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateTrackerMessage(Player trackingPlayer) {
        if (target == null) { //No target found, for whatever reason
            LanguageManager lang = new LanguageManager();
            String message = ChatColor.RED + lang.getString("noTarget");
            new ActionBarMessage(trackingPlayer).sendMessage(message);
        }
        else if (targetInGame(trackingPlayer) && Freetracker.getPlugin(Freetracker.class).getConfig().getBoolean(DISPLAY_INFORMATION_PATH)) {
            String message = getTrackingMessage();
            new ActionBarMessage(trackingPlayer).sendMessage(message);
        }
    }

    public void updateTrackerNeedle(Player trackingPlayer) {
        Material trackerItemType = trackingItem.getType();
        if (targetInGame(trackingPlayer) && trackerItemType == Material.COMPASS) {
            CompassMeta compassMeta = (CompassMeta) trackingItem.getItemMeta();
            compassMeta.setLodestone(target.getLocation());
        }
    }

    private String getTrackingMessage() {
        String message = "";
        FileConfiguration config = Freetracker.getPlugin(Freetracker.class).getConfig();
        LanguageManager lang = new LanguageManager();
        if (config.getBoolean(DISPLAY_INFORMATION_NAME_PATH)) {
            message += ChatColor.WHITE + lang.getString("name") + ": " + ChatColor.GOLD + targetName + " ";
        }
        if (config.getBoolean(DISPLAY_INFORMATION_DISTANCE_PATH)) {
            message += ChatColor.WHITE + lang.getString("distance") + ": " + ChatColor.GOLD + distance + " ";
        }
        if (config.getBoolean(DISPLAY_INFORMATION_COORDINATES_PATH)) {
            message += ChatColor.WHITE + lang.getString("coordinates") + ": " + ChatColor.GOLD + "(" + target_x + "," + target_y + "," + target_z + ")";
        }
        return message;
    }

    public void removeTarget() {
        Material trackerItemType = trackingItem.getType();
        if (trackerItemType == Material.COMPASS) {
            CompassMeta compassMeta = (CompassMeta) trackingItem.getItemMeta();
            compassMeta.setLodestone(null);
        }
    }
}

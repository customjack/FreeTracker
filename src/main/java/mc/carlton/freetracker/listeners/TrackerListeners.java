package mc.carlton.freetracker.listeners;

import mc.carlton.freetracker.Freetracker;
import mc.carlton.freetracker.events.PlayerTriggerTrackerCycle;
import mc.carlton.freetracker.events.PlayerTriggerTrackerUpdate;
import mc.carlton.freetracker.tracking.Tracker;
import mc.carlton.freetracker.tracking.TrackerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.List;

public class TrackerListeners implements Listener {
    private static final List<Action> CLICK_TYPES = Arrays.asList(new Action[]{Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK,Action.LEFT_CLICK_AIR,Action.LEFT_CLICK_BLOCK});
    private static final List<Action> LEFT_CLICK_TYPES = Arrays.asList(new Action[]{Action.LEFT_CLICK_AIR,Action.LEFT_CLICK_BLOCK});
    private static final List<Action> RIGHT_CLICK_TYPES = Arrays.asList(new Action[]{Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK});

    private static final String UPDATE_ON_RIGHT_CLICK_PATH = "trackerMechanics.updateOnRightClick";
    private static final String UPDATE_ON_LEFT_CLICK_PATH = "trackerMechanics.updateOnLeftclick";
    private static final String CYCLE_ON_RIGHT_CLICK_PATH = "trackerMechanics.cycleOnRightClick";
    private static final String CYCLE_ON_LEFT_CLICK_PATH = "trackerMechanics.cycleOnLeftClick";

    @EventHandler
    public void clickTracker(PlayerInteractEvent e) {
        Action a = e.getAction();
        FileConfiguration config = Freetracker.getPlugin(Freetracker.class).getConfig();
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        if (CLICK_TYPES.contains(a)) {
            Player player = e.getPlayer();
            ItemStack clickedItem = player.getInventory().getItemInMainHand();
            if (clickedItem == null) {
                return;
            }

            TrackerManager trackerManager = new TrackerManager();
            Tracker tracker = trackerManager.getTrackerFromItemStack(clickedItem);

            if (tracker != null) {

                if (LEFT_CLICK_TYPES.contains(a)) {
                    boolean updateOnLeftClick = config.getBoolean(UPDATE_ON_LEFT_CLICK_PATH);
                    boolean cycleOnLeftClick = config.getBoolean(CYCLE_ON_LEFT_CLICK_PATH);
                    if (cycleOnLeftClick) {
                        pluginManager.callEvent(new PlayerTriggerTrackerCycle(player,tracker));//Calls Event for triggering a tracker Cycle
                    } else if (updateOnLeftClick) {
                        pluginManager.callEvent(new PlayerTriggerTrackerUpdate(player,tracker));//Calls Event for triggering a tracker Cycle
                    }
                }
                else if (RIGHT_CLICK_TYPES.contains(a)) {
                    boolean updateOnRightClick = config.getBoolean(UPDATE_ON_RIGHT_CLICK_PATH);
                    boolean cycleOnRightClick = config.getBoolean(CYCLE_ON_RIGHT_CLICK_PATH);
                    if (cycleOnRightClick) {
                        pluginManager.callEvent(new PlayerTriggerTrackerCycle(player,tracker));//Calls Event for triggering a tracker Cycle
                    } else if (updateOnRightClick) {
                        pluginManager.callEvent(new PlayerTriggerTrackerUpdate(player,tracker));//Calls Event for triggering a tracker Cycle
                    }
                }
            }
        }
    }

    @EventHandler
    public void switchToTracker(PlayerItemHeldEvent e) {
        //Code for when player holds item
        //Need some way of detecting when they switch off... probably best to use hashmap tracking for players "on tracker"
    }

    @EventHandler
    public void testEvent(PlayerInteractEvent e) {
        Action a = e.getAction();
        FileConfiguration config = Freetracker.getPlugin(Freetracker.class).getConfig();
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        if (CLICK_TYPES.contains(a)) {
            Player player = e.getPlayer();
            ItemStack clickedItem = player.getInventory().getItemInMainHand();
            if (clickedItem == null) {
                return;
            }

            TrackerManager trackerManager = new TrackerManager();
            Tracker tracker = trackerManager.getTrackerFromItemStack(clickedItem);

            if (tracker != null) {

                if (LEFT_CLICK_TYPES.contains(a)) {
                    boolean updateOnLeftClick = config.getBoolean(UPDATE_ON_LEFT_CLICK_PATH);
                    boolean cycleOnLeftClick = config.getBoolean(CYCLE_ON_LEFT_CLICK_PATH);
                    if (cycleOnLeftClick) {
                        player.sendMessage("Cycle on left click");
                    } else if (updateOnLeftClick) {
                        player.sendMessage("Update on left click");
                    }
                }
            }
        }
    }
}

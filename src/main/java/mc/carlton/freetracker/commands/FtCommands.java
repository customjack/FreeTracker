package mc.carlton.freetracker.commands;

import mc.carlton.freetracker.Freetracker;
import mc.carlton.freetracker.tracking.TrackerManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class FtCommands implements CommandExecutor {
    private Plugin plugin = Freetracker.getPlugin(Freetracker.class);
    private static final String KEY_PREFIX = "FreeTracker_";

    private static final List<String> HELP_KEYWORDS  = Arrays.asList(new String[]{"help","?"});
    private static final List<String> TRACK_KEYWORDS  = Arrays.asList(new String[]{"track","follow"});
    private static final List<String> IGNORE_KEYWORDS  = Arrays.asList(new String[]{"ignore","noTrack"});
    private static final List<String> UNIGNORE_KEYWORDS  = Arrays.asList(new String[]{"unignore","trackAgain"});
    private static final List<String> ADD_TRACKER_KEYWORDS  = Arrays.asList(new String[]{"addTracker","add"});
    private static final List<String> REMOVE_TRACKER_KEYWORDS  = Arrays.asList(new String[]{"removeTracker","remove"});

    private static String HELP_PERMISSION_ID = "help";
    private static String TRACK_PERMISSION_ID = "track";
    private static String IGNORE_PERMISSION_ID = "ignore";
    private static String ADD_TRACKER_PERMISSION_ID = "addTracker";

    private void helpCommand(CommandSender sender, String[] args) {
        final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft help";
        CommandHelper commandHelper = new CommandHelper(sender,args,0,IMPROPER_ARGUMENTS_MESSSAGE);
        commandHelper.setPermissionName(HELP_PERMISSION_ID);
        if (commandHelper.isProperCommand()) {
            sender.sendMessage("Commands: help, track, ignore, unignore, addTracker, removeTracker");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Help
        if (args.length == 0) { //Help
            helpCommand(sender,args);
        }
        else if (HELP_KEYWORDS.contains(args[0])) {
            helpCommand(sender,args);
        }
        //Track
        else if (TRACK_KEYWORDS.contains(args[0])) {
            final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft track [playerName]";
            CommandHelper commandHelper = new CommandHelper(sender,args,1,IMPROPER_ARGUMENTS_MESSSAGE);
            commandHelper.setPermissionName(TRACK_PERMISSION_ID);
            commandHelper.setPlayerOnlyCommand(true);
            if (commandHelper.isProperCommand()) {

            }
        }
        //Ignore
        else if (IGNORE_KEYWORDS.contains(args[0])) {
            final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft ignore [playerName]";
            CommandHelper commandHelper = new CommandHelper(sender,args,1,IMPROPER_ARGUMENTS_MESSSAGE);
            commandHelper.setPermissionName(IGNORE_PERMISSION_ID);
            commandHelper.setPlayerOnlyCommand(true);
            if (commandHelper.isProperCommand()) {

            }
        }
        //Unignore
        else if (UNIGNORE_KEYWORDS.contains(args[0])) {
            final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft unignore [playerName]";
            CommandHelper commandHelper = new CommandHelper(sender,args,1,IMPROPER_ARGUMENTS_MESSSAGE);
            commandHelper.setPermissionName(IGNORE_PERMISSION_ID);
            commandHelper.setPlayerOnlyCommand(true);
            if (commandHelper.isProperCommand()) {

            }
        }
        //Add Tracker
        else if (ADD_TRACKER_KEYWORDS.contains(args[0])) {
            final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft addTracker";
            CommandHelper commandHelper = new CommandHelper(sender,args,0,IMPROPER_ARGUMENTS_MESSSAGE);
            commandHelper.setPermissionName(ADD_TRACKER_PERMISSION_ID);
            commandHelper.setPlayerOnlyCommand(true);
            if (commandHelper.isProperCommand()) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInMainHand();
                new TrackerManager().addTracker(item,new NamespacedKey(plugin, KEY_PREFIX + System.nanoTime()));
            }
        }
        //Remove Tracker
        else if (REMOVE_TRACKER_KEYWORDS.contains(args[0])) {
            final String IMPROPER_ARGUMENTS_MESSSAGE = " /ft removeTracker";
            CommandHelper commandHelper = new CommandHelper(sender,args,0,IMPROPER_ARGUMENTS_MESSSAGE);
            commandHelper.setPermissionName(ADD_TRACKER_PERMISSION_ID);
            commandHelper.setPlayerOnlyCommand(true);
            if (commandHelper.isProperCommand()) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInMainHand();
                new TrackerManager().removeTracker(item);
            }
        }
        return true;
    }
}

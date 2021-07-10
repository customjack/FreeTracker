package mc.carlton.freetracker.utilities;

import mc.carlton.freetracker.Freetracker;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class UtilityMethods {
    private static final String TRACKED_GAMEMODES_LIST_CONFIG_PATH = "general.trackedGamemodes";
    private static final String MINIMUM_TRACK_DISTANCE= "trackerMechanics.minimumDistance";
    private static final String MAXIMUM_TRACK_DISTANCE = "trackerMechanics.maximumDistance";
    private static final String INVALID_GAMEMODE_ENUM_ERROR_MESSAGE = "Invalid Gamemode Enum, Options are ADVENTURE, SURVIVAL, CREATIVE, SPECTATOR. Received:  ";
    private static List<GameMode> PLAYER_GAMEMODES;

    public static ArrayList<Player> getPlayersSortedByDistance(Location origin) {
        ArrayList<Player> participatingPlayers = getParticipatingPlayers(origin);

        //Sorts the players by distance from some given location (origin)
        participatingPlayers.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                if (p1.getLocation().distance(origin) > p2.getLocation().distance(origin)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return participatingPlayers;
    }

    public static ArrayList<Player> getPlayersSortedByName(Location origin) {
        ArrayList<Player> participatingPlayers = getParticipatingPlayers(origin);

        //Sorts the players by username
        participatingPlayers.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                if (p1.getDisplayName().toLowerCase().compareTo(p2.getDisplayName().toLowerCase()) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return participatingPlayers;
    }

    private static ArrayList<Player> getParticipatingPlayers(Location origin) {
        World world = origin.getWorld();
        List<Player> players = world.getPlayers();
        List<GameMode> allowedPlayerGamemodes = getPlayerGamemodes();
        ArrayList<Player> participatingPlayers = new ArrayList<>();

        FileConfiguration config = Freetracker.getPlugin(Freetracker.class).getConfig();
        double minDistance = config.getDouble(MINIMUM_TRACK_DISTANCE);
        double maxDistance = config.getDouble(MAXIMUM_TRACK_DISTANCE);

        //Gets list of players in an acceptable GameMode and within an acceptable distance range
        for (Player player : players) {
            if (allowedPlayerGamemodes.contains(player.getGameMode())) {
                double distanceFromOrigin = player.getLocation().distance(origin);
                if (distanceFromOrigin > minDistance && distanceFromOrigin < maxDistance) {
                    participatingPlayers.add(player);
                }
            }
        }
        return participatingPlayers;
    }

    private static List<GameMode> getPlayerGamemodes() {
        if (PLAYER_GAMEMODES == null) {
            // Gets a list of acceptable Gamemode Enums from the config if it hasn't been done already
            List<String> trackedGamemodesList = Freetracker.getPlugin(Freetracker.class).getConfig().getStringList(TRACKED_GAMEMODES_LIST_CONFIG_PATH);
            ArrayList<GameMode> allowedPlayerGamemodes = new ArrayList<>();
            for (String trackedGamemodeString : trackedGamemodesList) {
                try {
                    GameMode gameMode = GameMode.valueOf(trackedGamemodeString);
                    allowedPlayerGamemodes.add(gameMode);
                } catch (Error e) {
                    UtilityMethods.FreeTrackerPrint(INVALID_GAMEMODE_ENUM_ERROR_MESSAGE + trackedGamemodeString);
                }
            }
            PLAYER_GAMEMODES = allowedPlayerGamemodes;
            return allowedPlayerGamemodes;
        } else {
            // If the acceptable GameMode enums have been found already, returns them
            return PLAYER_GAMEMODES;
        }

    }

    public static void FreeTrackerPrint(String string) {
        System.out.println("[FreeTracker] " + string);
    }

    public static <T> T perioidicGetNext(List<T> list, int index) {
        if (index >= list.size() - 1) {
            index = 0;
        }
        return list.get(0);
    }
}

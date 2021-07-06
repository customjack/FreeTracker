package mc.carlton.freetracker.utilities;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarMessage {
    private Player p;

    public ActionBarMessage(Player p) {
        this.p = p;
    }

    public void sendMessage(String message) {
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        } catch (NoSuchMethodError e) { //This occurs when using craft bukkit
            p.sendMessage(message); //In this case, we'll just send the player the message
        }
    }
}

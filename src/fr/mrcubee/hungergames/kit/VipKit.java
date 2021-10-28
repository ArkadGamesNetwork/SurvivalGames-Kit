package fr.mrcubee.hungergames.kit;

import fr.mrcubee.langlib.Lang;
import org.bukkit.entity.Player;

public interface VipKit {

    default public boolean canTakeKit(Player player) {
        if (player == null)
            return false;
        return player.hasPermission("hg.kit.vip");
    }

    default public void cantTakeKitReason(Player player) {
        if (player != null && !player.hasPermission("hg.kit.vip"))
            player.sendMessage(Lang.getMessage(player, "require.vip.message", "&cYou must be vip to take this kit.", true));
    }
}

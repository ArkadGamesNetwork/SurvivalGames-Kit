package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.Game;
import fr.mrcubee.hungergames.GameStats;
import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.kit.Kit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CopierKit extends Kit {

    protected CopierKit() {
        super("Copier", new ItemStack(Material.PAINTING));
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
    }

    @Override
    public void cantTakeKitReason(Player player) {

    }

    @Override
    public void givePlayerKit(Player player) {

    }

    @Override
    public void removePlayerKit(Player player) {

    }

    @Override
    public boolean canLostItem(ItemStack itemStack) {
        return true;
    }

    @Override
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.copier.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.copier.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Game game = HungerGamesAPI.getGame();
        Player player = event.getEntity().getKiller();
        Kit[] kits;

        if (game.getGameStats() != GameStats.DURING || !containsPlayer(player))
            return;
        kits = game.getKitManager().getKitByPlayer(event.getEntity());
        if (kits == null)
            return;
        for (Kit kit : kits) {
            kit.addPlayer(player);
            kit.givePlayerKit(player);
        }
    }

}

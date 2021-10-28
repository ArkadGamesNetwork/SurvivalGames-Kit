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

public class StingyKit extends Kit {


    protected StingyKit() {
        super("Stingy", new ItemStack(Material.CHEST));
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
        return Lang.getMessage(player, "kit.stingy.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.stingy.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Game game = HungerGamesAPI.getGame();

        if (game.getGameStats() != GameStats.DURING || !containsPlayer(event.getEntity()))
            return;
        event.getDrops().clear();
    }
}

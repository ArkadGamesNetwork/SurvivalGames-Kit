package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.api.event.PlayerRadarEvent;
import fr.mrcubee.hungergames.kit.Kit;

public class NoRadarKit extends Kit {

	public NoRadarKit() {
		super( "NoRadar", new ItemStack(Material.BARRIER));
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
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
		return Lang.getMessage(player, "kit.noRadar.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.noRadar.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void playerRadarEvent(PlayerRadarEvent event) {
		int playerInGame = HungerGamesAPI.getGame().getNumberPlayer();

		if (playerInGame == 2 || playerInGame == getNumberPlayer())
			return;
		if (containsPlayer(event.getTarget()))
			event.setCancelled(true);
	}

}

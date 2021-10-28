package fr.mrcubee.hungergames.kit.list;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.mrcubee.hungergames.kit.VipKit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.hungergames.GameStats;
import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.kit.Kit;

public class ThiefKit extends Kit implements VipKit {

	private final Map<Player, Player> thiefTargets;
	
	public ThiefKit() {
		super("Thief", new ItemStack(Material.EYE_OF_ENDER, 1));
		this.thiefTargets = new HashMap<Player, Player>();;
	}

	@Override
	public boolean canTakeKit(Player player) {
		return VipKit.super.canTakeKit(player);
	}

	@Override
	public void cantTakeKitReason(Player player) {
		VipKit.super.cantTakeKitReason(player);
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
		return Lang.getMessage(player, "kit.thief.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.thief.description", "&cERROR", true);
	}

	@Override
	public void update() {
		if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		for (Entry<Player, Player> entry : thiefTargets.entrySet()) {
			if (entry.getKey().getLocation().distance(entry.getValue().getLocation()) > 4)
				entry.getKey().closeInventory();
		}
	}

	@EventHandler
	public void playerInteract(PlayerInteractEntityEvent event) {
		Player thief;
		Player target;

		if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING || !containsPlayer(event.getPlayer())
				|| !(event.getRightClicked() instanceof Player) || !event.getPlayer().isSneaking())
			return;
		thief = event.getPlayer();
		target = (Player) event.getRightClicked();
		thief.openInventory(target.getInventory());
		thiefTargets.put(thief, target);
	}

	@EventHandler
	public void playerInventoryClose(InventoryCloseEvent event) {
		thiefTargets.remove((Player) event.getPlayer());
	}

	@EventHandler
	public void playerInventoryMoveItem(InventoryClickEvent event) {
		Player target = thiefTargets.get((Player) event.getWhoClicked());

		if (target == null || HungerGamesAPI.getGame().getKitManager().canLostItem(target, event.getCurrentItem()))
			return;
		event.setCancelled(true);
	}
}
package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

import java.util.Iterator;

public class MinerKit extends Kit {

	private final ItemStack[] items;

	public MinerKit() {
		super("Miner", new ItemStack(Material.STONE_PICKAXE));
		ItemMeta itemMeta;

		this.items = new ItemStack[] {
				new ItemStack(Material.STONE_PICKAXE)
		};
		this.items[0].addEnchantment(Enchantment.DIG_SPEED, 5);
		itemMeta = this.items[0].getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Miner");
		itemMeta.spigot().setUnbreakable(true);
		this.items[0].setItemMeta(itemMeta);
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().addItem(this.items);
	}

	@Override
	public void removePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().removeItem(this.items);
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		if (itemStack == null)
			return true;
		for (ItemStack item : this.items)
			if (itemStack.isSimilar(item))
				return false;
		return true;
	}

	@Override
	public String getDisplayName(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.miner.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.miner.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	private boolean blockBreakRecipe(BlockBreakEvent event, Recipe recipe) {
		FurnaceRecipe furnaceRecipe;
		Block block;
		World world;

		if (event == null || !(recipe instanceof FurnaceRecipe))
			return false;
		furnaceRecipe = (FurnaceRecipe) recipe;
		if (!event.getBlock().getType().equals(furnaceRecipe.getInput().getType()))
			return false;
		event.setCancelled(true);
		block = event.getBlock();
		block.setType(Material.AIR);
		world = block.getWorld();
		world.dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), furnaceRecipe.getResult());
		return true;
	}

	@EventHandler
	public void blockBreakEvent(BlockBreakEvent event) {
		Iterator<Recipe> recipeIterator;

		if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING
		|| !getPlayers().contains(event.getPlayer()) || canLostItem(event.getPlayer().getItemInHand()))
			return;
		recipeIterator = Bukkit.getServer().recipeIterator();
		if (recipeIterator == null)
			return;
		while (recipeIterator.hasNext() && !blockBreakRecipe(event, recipeIterator.next()));
	}
}

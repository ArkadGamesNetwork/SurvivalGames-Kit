package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.kit.ItemKit;
import fr.mrcubee.langlib.Lang;
import fr.mrcubee.hungergames.GameStats;
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

import java.util.Iterator;

public class MinerKit extends ItemKit {

	public MinerKit() {
		super("Miner", new ItemStack(Material.STONE_PICKAXE), new ItemStack[] {
				new ItemStack(Material.STONE_PICKAXE)
		});
		ItemMeta itemMeta;

		this.kitItems[0].addEnchantment(Enchantment.DIG_SPEED, 5);
		itemMeta = this.kitItems[0].getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Miner");
		itemMeta.spigot().setUnbreakable(true);
		this.kitItems[0].setItemMeta(itemMeta);
	}

	@Override
	public boolean canTakeKit(Player player) {
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

		if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING
		|| !containsPlayer(event.getPlayer()) || canLostItem(event.getPlayer().getItemInHand()))
			return;
		recipeIterator = Bukkit.getServer().recipeIterator();
		if (recipeIterator == null)
			return;
		while (recipeIterator.hasNext() && !blockBreakRecipe(event, recipeIterator.next()));
	}
}

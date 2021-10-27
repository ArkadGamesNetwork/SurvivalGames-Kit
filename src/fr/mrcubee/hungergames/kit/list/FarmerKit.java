package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.kit.ItemKit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class FarmerKit extends ItemKit {

	public FarmerKit() {
		super("Farmer", new ItemStack(Material.SEEDS, 1), new ItemStack[] {
				new ItemStack(Material.SEEDS),
				new ItemStack(Material.MELON_SEEDS),
				new ItemStack(Material.PUMPKIN_SEEDS),
				new ItemStack(Material.CARROT),
				new ItemStack(Material.POTATO),
				new ItemStack(Material.COCOA)
		});

	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public String getDisplayName(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.farmer.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.farmer.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		if (!containsPlayer(event.getPlayer()))
			return;

		if (event.getBlock().getType().equals(Material.CROPS) || event.getBlock().getType().equals(Material.COCOA)
				|| event.getBlock().getType().equals(Material.CARROT)
				|| event.getBlock().getType().equals(Material.POTATO)
				|| event.getBlock().getType().equals(Material.MELON_STEM)
				|| event.getBlock().getType().equals(Material.PUMPKIN_STEM))
			event.getBlock().setData(CropState.RIPE.getData());
	}

}

package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.mrcubee.hungergames.kit.Kit;

public class MrCubeeKit extends Kit {

	public static MrCubeeKit generateKit() {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		skullMeta.setOwner("MrCubee");
		itemStack.setItemMeta(skullMeta);
		return new MrCubeeKit("MrCubee", itemStack);
	}

	private MrCubeeKit(String name, ItemStack itemStack) {
		super(name, itemStack);
	}

	@Override
	public boolean canTakeKit(Player player) {
		return player.getUniqueId().toString().equals("cf48c920-86a9-49bb-973b-99b6a365bcc4");
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
		return Lang.getMessage(player, "kit.mrcubee.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.mrcubee.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}
}

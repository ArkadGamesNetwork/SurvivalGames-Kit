package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.hungergames.GameStats;
import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.kit.Kit;

public class HulkKit extends Kit {
	
	public static HulkKit generateKit() {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

		skullMeta.setOwner("Incredible_Hulk");
		itemStack.setItemMeta(skullMeta);
		return new HulkKit("Hulk", itemStack);
	}

	private HulkKit(String name, ItemStack itemStack) {
		super(name, itemStack);
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
		return Lang.getMessage(player, "kit.hulk.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.hulk.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		if (e.getDamager() instanceof Player) {
			if ((e.getEntity() instanceof Player) && (!HungerGamesAPI.getGame().isPvpEnable()))
				return;
			Player attacker = (Player) e.getDamager();
			if (containsPlayer(attacker)) {
				e.getEntity().setVelocity(attacker.getLocation().getDirection().multiply(20F));
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		if (!containsPlayer(event.getPlayer()))
			return;
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.JUMP))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 8), true);
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1), true);
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2), true);
		if (event.getPlayer().isSneaking() && (event.getPlayer().getVelocity().getY() > 1)
				&& (event.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().isSolid()
						|| event.getPlayer().getLocation().clone().subtract(0, 2, 0).getBlock().getType().isSolid()))
			event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(5F));
	}

}

package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class MyopieKit extends Kit {
	
	public MyopieKit(JavaPlugin javaPlugin) {
		super("MyopieKit", new ItemStack(Material.PUMPKIN, 1));
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
		return null;
	}

	@Override
	public String getDescription(Player player) {
		return null;
	}

	@Override
	public void update() {
		if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		for (Player player : getPlayers()) {
			if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
			if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 3));
			if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 3));
		}
	}

}

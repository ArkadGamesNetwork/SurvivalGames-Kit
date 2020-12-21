package fr.mrcubee.survivalgames.kit;

import fr.mrcubee.survivalgames.kit.list.MinerKit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import fr.mrcubee.survivalgames.kit.list.RegisterKit;

import java.util.ArrayList;

public class SurvivalGamesKit extends JavaPlugin {

	private static SurvivalGamesKit instance;

	@Override
	public void onLoad() {
		SurvivalGamesKit.instance = this;
		if (this.getServer().getPluginManager().getPlugin("SurvivalGames") == null)
			this.getServer().getPluginManager().disablePlugin(this);
	}
	
	@Override
	public void onEnable() {
		if (this.getServer().getPluginManager().getPlugin("SurvivalGames") == null) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		getServer().getPluginManager().registerEvents(new MinerKit(), this);
		RegisterKit.register();
	}

	public static SurvivalGamesKit getInstance() {
		return SurvivalGamesKit.instance;
	}
}

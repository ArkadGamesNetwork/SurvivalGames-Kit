package fr.mrcubee.hungergames.kit;

import fr.mrcubee.langlib.Lang;
import org.bukkit.plugin.java.JavaPlugin;
import fr.mrcubee.hungergames.kit.list.RegisterKit;

public class HungerGamesKit extends JavaPlugin {

	private static HungerGamesKit instance;

	@Override
	public void onLoad() {
		if (this.getServer().getPluginManager().getPlugin("HungerGames") == null)
			this.getServer().getPluginManager().disablePlugin(this);
		HungerGamesKit.instance = this;
	}
	
	@Override
	public void onEnable() {
		if (this.getServer().getPluginManager().getPlugin("HungerGames") == null) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Lang.setDefaultLang("EN_us");
		RegisterKit.register();
	}

	public static HungerGamesKit getInstance() {
		return HungerGamesKit.instance;
	}
}

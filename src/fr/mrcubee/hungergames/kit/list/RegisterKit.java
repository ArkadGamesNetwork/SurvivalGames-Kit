package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.finder.plugin.PluginFinder;
import org.bukkit.Bukkit;

import fr.mrcubee.hungergames.HungerGamesAPI;
import fr.mrcubee.hungergames.kit.Kit;
import fr.mrcubee.hungergames.kit.KitManager;
import org.bukkit.plugin.Plugin;

public class RegisterKit {
	
	public static void register() {
		Kit[] kits = new Kit[] {
				new ArcherKit(),
				new MinerKit(),
				new LumberJackKit(),
				new MonsterKit(),
				new CreeperKit(),
				new FarmerKit(),
				new NinjaKit(),
				new ThiefKit(),
				new WereWolfKit(),
				new NoRadarKit(),
				new FakeRadarKit(),
				new DeathNote(),
				new SpiderMan(),
				new SwapperKit()
				//new IllusionKit()
				//MrCubeeKit.generateKit()
		};
		KitManager kitManager = HungerGamesAPI.getGame().getKitManager();

		for (Kit kit : kits) {
			kitManager.registerKit(kit);
			Bukkit.getServer().getPluginManager().registerEvents(kit, (Plugin) PluginFinder.INSTANCE.findPlugin());
		}
	}
}
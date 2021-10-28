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
				new CopierKit(),
				new CreeperKit(),
				new DeathNote(),
				new FakeRadarKit(),
				new FarmerKit(),
				new LumberJackKit(),
				new MinerKit(),
				new MonsterKit(),
				new NinjaKit(),
				new NoRadarKit(),
				new SpiderMan(),
				new StingyKit(),
				new SwapperKit(),
				new ThiefKit(),
				new WereWolfKit()
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

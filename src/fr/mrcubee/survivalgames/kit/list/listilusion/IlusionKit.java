package fr.mrcubee.survivalgames.kit.list.listilusion;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.bukkit.Versions;
import fr.mrcubee.survivalgames.kit.Kit;

public class IlusionKit extends Kit{
	
	private int entity_id_start;
	private int entity_id_end;
	private Map<Player, Integer> ids;
	private Map<Player, List<CloneIlusion>> clones;
	
	private Random random;
	
	public IlusionKit(){
		super("Ilusion", "Make you like drogs", getItem());
		this.random = new Random();
		this.ids = new HashMap<>();
		this.clones = new HashMap<>();
	}
	
	private static ItemStack getItem(){
		return new ItemStack(Material.BLAZE_POWDER);
	}

	@Override
	public boolean canLostItem(ItemStack item){
		return true;
	}

	@Override
	public boolean canTakeKit(Player Player){
		return true;
	}

	@Override
	public void givePlayerKit(Player player){}

	@Override
	public void removePlayerKit(Player player){}

	@Override
	public void update(){	
		if(this.entity_id_start == 0){
			takeIDS();
			Set<Player> ilusionist = getPlayers();
			for(Player player : Bukkit.getOnlinePlayers()){
				makeIlusion(player, ((ilusionist.contains(player))));
			}
		}
		
		for(Player player : Bukkit.getOnlinePlayers()){
			for(CloneIlusion clone : this.clones.get(player)){
				clone.update();
				for(Player players : Bukkit.getOnlinePlayers()){
					if(player.equals(players)) continue;
					clone.update(players);
				}
			}
		}
	}
	
	private void takeIDS(){
		try {
			Field field;
			
			field = Class.forName("net.minecraft.server."+Versions.getCurrent().toString()+".Entity").getDeclaredField("entityCount");
			field.setAccessible(true);
			
			entity_id_start = field.getInt(null);
			entity_id_end = entity_id_start+(Bukkit.getOnlinePlayers().size()*5);
			
			field.set(null, entity_id_end);
			
			int index = 0;
			for(Player player : Bukkit.getOnlinePlayers()){
				this.ids.put(player, (index++)*5);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeIlusion(Player player, boolean ilusionist){
		Location loc = player.getLocation();
		int start_id = this.ids.get(player);
		for(int i = 0; i < ((ilusionist) ? 5 : 2); i++){
			CloneIlusion clone = new CloneIlusion(player, this.random, start_id+i);
			Location spawn = clone.makeClone(loc);
			if(random.nextBoolean()) loc = spawn;
		}
	}
	
}	
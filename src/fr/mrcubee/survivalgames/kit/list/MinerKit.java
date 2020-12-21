package fr.mrcubee.survivalgames.kit.list;


import fr.mrcubee.survivalgames.kit.SurvivalGamesKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

import java.util.ArrayList;

public class MinerKit extends Kit implements Listener {

	private final ItemStack[] items;

	public MinerKit() {
		super("Miner", "You appear with:\n" + "- 1x Stone pickaxe durability 3  and efficiency 5\n" + "- Smelting the iron and the gold" ,
				new ItemStack(Material.STONE_PICKAXE));
		ItemMeta itemMeta;

		this.items = new ItemStack[] {
				new ItemStack(Material.STONE_PICKAXE)
		};
		this.items[0].addEnchantment(Enchantment.DURABILITY, 2);
		this.items[0].addEnchantment(Enchantment.DIG_SPEED, 5);
		this.items[0].addEnchantment(Enchantment.DURABILITY, 3);
		itemMeta = this.items[0].getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Miner");
		this.items[0].setItemMeta(itemMeta);
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	private ArrayList<Player> mineur = new ArrayList<>();

	@Override
	public void givePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().addItem(this.items);
		mineur.add(player);
	}


	@Override
	public void removePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().removeItem(this.items);
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		if (itemStack == null)
			return true;
		for (ItemStack item : this.items)
			if (itemStack.isSimilar(item))
				return false;
		return true;
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent e){
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack itemStack = e.getPlayer().getItemInHand();

		if(mineur.contains(player)){
			if(itemStack == new ItemStack(Material.GOLD_PICKAXE) || itemStack == new ItemStack(Material.WOOD_PICKAXE)){
				switch (block.getType()){
					case IRON_ORE:
						block.setType(Material.AIR);
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT));
						break;

					case GOLD_ORE:
						block.setType(Material.AIR);
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.AIR));
						break;


					case DIAMOND_ORE:
						block.setType(Material.AIR);
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.AIR));
						break;

					default:
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.AIR));
						break;

				}
				return;
			}
				switch (block.getType()){
					case IRON_ORE:
						block.setType(Material.AIR);
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT));
						break;

					case GOLD_ORE:
						block.setType(Material.AIR);
						break;

					case DIAMOND_ORE:
						block.setType(Material.AIR);
						SurvivalGamesKit.getInstance().getServer().getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.DIAMOND));
						break;

					default: break;
				}
			}
		}
	}

package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.kit.ItemKit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArcherKit extends ItemKit {

    public ArcherKit() {
        super("Archer", new ItemStack(Material.BOW), new ItemStack[] {
                new ItemStack(Material.BOW)
        });
        ItemMeta itemMeta;

        this.kitItems[0].addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        this.kitItems[0].addEnchantment(Enchantment.ARROW_INFINITE, 1);
        this.kitItems[0].addEnchantment(Enchantment.DURABILITY, 3);
        itemMeta = this.kitItems[0].getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Archer");
        this.kitItems[0].setItemMeta(itemMeta);
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
    }

    @Override
    public void cantTakeKitReason(Player player) {

    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        super.givePlayerKit(player);
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }

    @Override
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.archer.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.archer.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }
}

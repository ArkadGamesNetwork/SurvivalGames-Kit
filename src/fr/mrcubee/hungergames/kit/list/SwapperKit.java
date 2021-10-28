package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.kit.CoolDownProjectileKit;
import fr.mrcubee.hungergames.kit.ItemKit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SwapperKit extends CoolDownProjectileKit {

    public SwapperKit() {
        super("Swapper", new ItemStack(Material.SNOW_BALL), new ItemStack[] {
                new ItemStack(Material.SNOW_BALL)
        }, 5000, 0);
        ItemMeta itemMeta;

        itemMeta = this.kitItems[0].getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "Swap");
        itemMeta.setLore(null);
        this.kitItems[0].setItemMeta(itemMeta);
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
    }

    @Override
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.swapper.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.swapper.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }

    @Override
    protected void onProjectileHit(Projectile projectile, Player launcher) {

    }

    @Override
    protected void onProjectileDamageEntity(Projectile projectile, Player launcher, Entity entity) {
        Location temp;

        if (launcher == null || entity == null)
            return;
        temp = entity.getLocation();
        entity.teleport(launcher);
        launcher.teleport(entity);
    }

    @Override
    protected void onItemUse(Player player) {
        if (!launch(player, Snowball.class))
            sendCoolDownMessage(player);
    }
}

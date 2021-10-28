package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.hungergames.kit.CoolDownProjectileKit;
import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SpiderMan extends CoolDownProjectileKit {

    public SpiderMan() {
        super("SpiderMan", new ItemStack(Material.WEB, 1), new ItemStack[] {
                new ItemStack(Material.WEB)
        }, 10000, 0);
        ItemMeta itemMeta;

        itemMeta = this.kitItems[0].getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "WEB LAUNCHER");
        itemMeta.setLore(Arrays.asList());
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
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.spiderMan.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.spiderMan.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }

    private void spawnWeb(Location location) {
        Location current;

        if (location == null)
            return;
        current = location.subtract(1, 1, 1);
        for (int y = 0; y < 3; y++) {
            for (int z = 0; z < 3; z++) {
                for (int x = 0; x < 3; x++) {
                    if (current.getBlock().getType().equals(Material.AIR))
                        current.getBlock().setType(Material.WEB);
                    current.add(1, 0, 0);
                }
                current.subtract(3, 0, 0).add(0, 0, 1);
            }
            current.subtract(0, 0, 3).add(0, 1, 0);
        }
    }

    @Override
    protected void onProjectileHit(Projectile projectile, Player launcher) {
        if (projectile == null)
            return;
        spawnWeb(projectile.getLocation());
    }

    @Override
    protected void onProjectileDamageEntity(Projectile projectile, Player launcher, Entity entity) {
        if (entity == null)
            return;
        spawnWeb(entity.getLocation());
    }

    @Override
    protected void onItemUse(Player player) {
        if (!launch(player, Snowball.class))
            sendCoolDownMessage(player);
    }
}

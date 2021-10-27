package fr.mrcubee.hungergames.kit.list;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.hungergames.GameStats;
import fr.mrcubee.hungergames.HungerGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.hungergames.kit.Kit;

public class SwapperKit extends Kit {

    private final ItemStack[] items;

    public SwapperKit() {
        super("Archer", new ItemStack(Material.SNOW_BALL));
        ItemMeta itemMeta;

        this.items = new ItemStack[]{
                new ItemStack(Material.SNOW_BALL)
        };
        itemMeta = this.items[0].getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "Swap");
        itemMeta.setLore(null);
        this.items[0].setItemMeta(itemMeta);
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().addItem(this.items);
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

    private void swapWithLivingEntity(Player player, LivingEntity livingEntity) {
        Location playerLocation;

        if (player == null || livingEntity == null)
            return;
        playerLocation = player.getLocation();
        player.teleport(livingEntity);
        livingEntity.teleport(playerLocation);
    }

    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) { Projectile projectile;
        Player shooter;

        if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING
        || !(event.getDamager() instanceof Projectile) || !(event.getEntity() instanceof LivingEntity))
            return;
        projectile = (Projectile) event.getDamager();
        if (projectile.getType() != EntityType.SNOWBALL || !(projectile.getShooter() instanceof Player))
            return;
        shooter = (Player) projectile.getShooter();
        if (!containsPlayer(shooter))
            return;
        swapWithLivingEntity(shooter, (LivingEntity) event.getEntity());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING
        || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
        || canLostItem(event.getItem()))
            return;
        event.setCancelled(true);

    }
}

package fr.mrcubee.hungergames.kit;

import fr.mrcubee.hungergames.GameStats;
import fr.mrcubee.hungergames.HungerGamesAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class CoolDownProjectileKit extends CoolDownItemKit {

    private final int useItemIndex;
    private final Map<Projectile, Player> playerProjectileMap;

    public CoolDownProjectileKit(String name, ItemStack itemKit, ItemStack[] kitItems, long coolDownMillis, int useItemIndex) {
        super(name, itemKit, kitItems, coolDownMillis);
        this.useItemIndex = useItemIndex;
        this.playerProjectileMap = new WeakHashMap<Projectile, Player>();
    }

    protected <T extends Projectile> boolean launch(Player player, Class<T> projectileClass) {
        Projectile projectile;

        if (player == null || projectileClass == null || !use(player))
            return false;
        projectile = player.launchProjectile(projectileClass);
        this.playerProjectileMap.put(projectile, player);
        return true;
    }

    protected <T extends Projectile> boolean launch(Player player, Class<T> projectileClass, Vector vector) {
        Projectile projectile;

        if (player == null || projectileClass == null || !use(player))
            return false;
        if (vector == null)
            projectile = player.launchProjectile(projectileClass);
        else
            projectile = player.launchProjectile(projectileClass, vector);
        this.playerProjectileMap.put(projectile, player);
        return true;
    }

    protected abstract void onProjectileHit(Projectile projectile, Player launcher);
    protected abstract void onProjectileDamageEntity(Projectile projectile, Player launcher, Entity entity);
    protected abstract void onItemUse(Player player);

    @EventHandler
    public void projectileHitEvent(ProjectileHitEvent event) {
        if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING || !this.playerProjectileMap.containsKey(event.getEntity()))
            return;
        onProjectileHit(event.getEntity(), this.playerProjectileMap.remove(event.getEntity()));
    }

    @EventHandler
    public void projectileDamageEntityEvent(EntityDamageByEntityEvent event) {
        Projectile projectile;

        if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING || !(event.getDamager() instanceof Projectile)
        || !this.playerProjectileMap.containsKey((Projectile) event.getDamager()))
            return;
        event.setCancelled(true);
        projectile = (Projectile) event.getDamager();
        onProjectileDamageEntity(projectile, this.playerProjectileMap.remove(projectile), event.getEntity());
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (HungerGamesAPI.getGame().getGameStats() != GameStats.DURING
        || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
        || (this.useItemIndex >= 0 && this.useItemIndex < this.kitItems.length && event.getItem() != null && event.getItem().isSimilar(this.kitItems[this.useItemIndex])))
            return;
        event.setCancelled(true);
        onItemUse(event.getPlayer());
    }

}

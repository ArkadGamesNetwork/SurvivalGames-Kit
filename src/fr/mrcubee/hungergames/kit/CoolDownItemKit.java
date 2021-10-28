package fr.mrcubee.hungergames.kit;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class CoolDownItemKit extends ItemKit {

    private final long coolDownMillis;
    private final Map<Player, Long> playerCoolDown;

    public CoolDownItemKit(String name, ItemStack itemKit, ItemStack[] kitItems, long coolDownMillis) {
        super(name, itemKit, kitItems);
        this.coolDownMillis = coolDownMillis;
        this.playerCoolDown = new WeakHashMap<Player, Long>();
    }

    protected long getCoolDownTime(Player player) {
        Long time;
        long result;

        if (player == null)
            return 0;
        time = this.playerCoolDown.get(player);
        if (time == null)
            return 0;
        result = this.coolDownMillis - (System.currentTimeMillis() - time);
        return result >= 0 ? result : 0;
    }

    protected long getCoolDownTimeSecond(Player player) {
        return getCoolDownTime(player) / 1000;
    }

    protected void sendCoolDownMessage(Player player) {
        long coolDownTime;
        String message;

        if (player == null)
            return;
        coolDownTime =  getCoolDownTimeSecond(player);
        message = Lang.getMessage(player, "kit_cooldown.wait", "&7You will be able to use it in &l&c%l &r&7second(s).", true, coolDownTime);

        if (PlayerUtil.sendPlayerActionBar(player, message))
            return;
        player.sendMessage(message);
    }

    protected boolean canUse(Player player) {
        Long time;

        if (player == null)
            return false;
        time = this.playerCoolDown.get(player);
        if (time != null && (System.currentTimeMillis() - time) < this.coolDownMillis)
            return false;
        this.playerCoolDown.remove(player);
        return true;
    }

    protected boolean use(Player player) {
        if (canUse(player)) {
            this.playerCoolDown.put(player, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    @Override
    public void removePlayerKit(Player player) {
        super.removePlayerKit(player);
        if (player != null)
            this.playerCoolDown.remove(player);
    }
}

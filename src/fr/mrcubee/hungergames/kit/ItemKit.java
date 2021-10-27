package fr.mrcubee.hungergames.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemKit extends Kit {

    protected final ItemStack[] kitItems;

    public ItemKit(String name, ItemStack itemKit, ItemStack[] kitItems) {
        super(name, itemKit);
        this.kitItems = kitItems;
    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().addItem(this.kitItems);
    }

    @Override
    public void removePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().removeItem(this.kitItems);
    }

    @Override
    public boolean canLostItem(ItemStack targetedItemStack) {
        if (targetedItemStack == null)
            return true;
        for (ItemStack itemStack : this.kitItems)
            if (targetedItemStack.isSimilar(itemStack))
                return false;
        return true;
    }
}

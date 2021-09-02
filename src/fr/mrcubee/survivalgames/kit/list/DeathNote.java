package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.sign.gui.SignGUi;
import fr.mrcubee.survivalgames.Game;
import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;
import fr.mrcubee.survivalgames.kit.SurvivalGamesKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DeathNote extends Kit {

    private final SignGUi signGUi;
    private final ItemStack deathNoteItem;

    protected DeathNote() {
        super("DeathNote", new ItemStack(Material.PAPER, 1));
        ItemMeta itemMeta;

        this.deathNoteItem = new ItemStack(Material.PAPER, 1);
        itemMeta = this.deathNoteItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "DEATH NOTE");
        itemMeta.setLore(Arrays.asList());
        this.deathNoteItem.setItemMeta(itemMeta);
        this.signGUi = SignGUi.create(SurvivalGamesKit.getInstance());
    }

    @Override
    public boolean canTakeKit(Player player) {
        Game game;

        if (this.signGUi == null) {
            player.sendMessage(ChatColor.RED + "[ERROR] SignGUI not loaded.");
            return false;
        }
        game = SurvivalGamesAPI.getGame();
        if (game == null || game.getPlayerInGame().size() < 10) {
            player.sendMessage(Lang.getMessage(player, "kit.deathNote.message.required.players",
                    "&cYou need at least %d players in the game to take the kit.", true, 10));
            return false;
        } else if (getPlayers().size() > 0) {
            player.sendMessage(Lang.getMessage(player, "kit.deathNote.message.required.alreadyUse",
                    "&cThe kit is already in use by another player.", true));
            return false;
        }
        return true;
    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().addItem(this.deathNoteItem);
    }

    @Override
    public void removePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().remove(this.deathNoteItem);
    }

    @Override
    public boolean canLostItem(ItemStack itemStack) {
        return (itemStack == null || !itemStack.isSimilar(this.deathNoteItem));
    }

    @Override
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.deathNote.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.deathNote.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }

    private void executeDeathNote(Player player, String[] lines) {
        Player target;
        StringBuilder nameBuilder;
        String name;
        String prefix;

        if (player == null || lines == null)
            return;
        nameBuilder = new StringBuilder();
        nameBuilder.append(lines[0]);
        nameBuilder.append(lines[1]);
        name = nameBuilder.toString();
        if (name.isEmpty())
            return;
        target = Bukkit.getPlayer(name);
        if (target == null || SurvivalGamesAPI.getGame().isSpectator(target)) {
            prefix = Lang.getMessage(player, "kit.deathNote.message.prefix", "&d[DEATH NOTE] &a", true);
            player.sendMessage(prefix + Lang.getMessage(player, "kit.deathNote.message.kill.error",
                    "Player %s does not exists.", true, name));
            return;
        }
        player.getInventory().remove(this.deathNoteItem);
        target.setHealth(0);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING
        || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) || event.getItem() == null
        || !event.getItem().isSimilar(this.deathNoteItem))
            return;
        this.signGUi.open(event.getPlayer(), lines -> executeDeathNote(event.getPlayer(), lines),
                "",
                "",
                "^^^",
                "Player Name");
    }
}

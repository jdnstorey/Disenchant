package me.polo.disenchant;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Objects;

public class DisenchantCmd implements CommandExecutor {

    private Plugin plugin = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.getLevel() >= 3) {
                if (p.getInventory().contains(Material.BOOK)) {

                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();

                    if(meta.hasEnchants()) {
                        if (!item.getType().equals(Material.ENCHANTED_BOOK)) {

                            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
                            EnchantmentStorageMeta esmeta = (EnchantmentStorageMeta) book.getItemMeta();

                            p.getInventory().remove(new ItemStack(Material.BOOK, 1));

                            for (Enchantment e : item.getEnchantments().keySet()) {

                                //p.sendMessage("Enchantment: " + e.getKey() + "    ||     " + "Level: " + item
                                // .getEnchantmentLevel(e));

                                esmeta.addStoredEnchant(e, item.getEnchantmentLevel(e), true);
                                //p.sendMessage("" + e + "     ||     " + "" + esmeta);

                                item.removeEnchantment(e);
                            }

                            //p.sendMessage("" + esmeta.getStoredEnchants());
                            book.setItemMeta(esmeta);
                            p.getInventory().addItem(book);

                            //remove 2 levels
                            int level = p.getLevel();
                            p.setLevel(level - 3);

                            p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Successfully disenchated the item");
                        } else {
                            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Why would you want to disenchant a book?");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The item must have enchantments");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You need a book in your inventory");
                }
            } else {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You need at least 3 xp levels");
            }
        }
        return false;
    }
}

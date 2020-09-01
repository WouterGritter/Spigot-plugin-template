package me.woutergritter.plugintemplate.util.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    private ItemUtils() {
    }

    public static ItemStack create(Material material, int amount, String displayName, List<String> lore, Map<Enchantment, Integer> enchantments) {
        ItemStack res = new ItemStack(material, amount);
        ItemMeta itemMeta = res.getItemMeta();

        if(itemMeta == null) {
            return res;
        }

        if(displayName != null) {
            itemMeta.setDisplayName(displayName);
        }

        if(lore != null && !lore.isEmpty()) {
            itemMeta.setLore(lore);
        }

        if(enchantments != null && !enchantments.isEmpty()) {
            enchantments.forEach((enchantment, level) -> {
                itemMeta.addEnchant(enchantment, level, true);
            });
        }

        res.setItemMeta(itemMeta);
        return res;
    }

    /**
     * 1.8.8 only implementation
     */
    public static ItemStack create(Material material, int amount, int durability, String displayName, List<String> lore, Map<Enchantment, Integer> enchantments) {
        ItemStack res = create(material, amount, displayName, lore, enchantments);
        res.setDurability((short) durability);

        return res;
    }

    public static ItemStack fromConfig(ConfigurationSection conf) {
        if(!conf.contains("type")) {
            return null;
        }

        Material type = Material.matchMaterial(conf.getString("type"));
        if(type == null) {
            return null;
        }

        ItemStack res = new ItemStack(type);

        if(conf.contains("amount")) {
            int amount = conf.getInt("amount", 1);
            res.setAmount(amount);
        }

        if(conf.contains("durability") || conf.contains("damage")) {
            short durability = (short) conf.getInt(conf.contains("durability") ? "durability" : "damage", 0);
            res.setDurability(durability);
        }

        ItemMeta itemMeta = res.getItemMeta();
        if(itemMeta == null) {
            return res;
        }

        if(conf.contains("name")) {
            String name = ChatColor.translateAlternateColorCodes('&', conf.getString("name"));
            itemMeta.setDisplayName(name);
        }

        if(conf.contains("lore")) {
            List<String> lore = new ArrayList<>(conf.getStringList("lore"));
            lore.replaceAll(l -> ChatColor.translateAlternateColorCodes('&', l));
            itemMeta.setLore(lore);
        }

        if(conf.contains("enchantments") && conf.isConfigurationSection("enchantments")) {
            conf.getConfigurationSection("enchantments").getKeys(false).forEach(enchantmentName -> {
                Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
                if(enchantment == null) {
                    return;
                }

                int level = conf.getInt("enchantments." + enchantmentName, 1);
                itemMeta.addEnchant(enchantment, level, true);
            });
        }

        res.setItemMeta(itemMeta);
        return res;
    }

    /**
     * Calls {@link String#format(String, Object...)} on the display name and lore
     * of the item if applicable.
     */
    public static ItemStack formatItemStack(ItemStack itemStack, Object... args) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) {
            return itemStack;
        }

        if(itemMeta.hasDisplayName()) {
            String displayName = String.format(itemMeta.getDisplayName(), args);
            itemMeta.setDisplayName(displayName);
        }

        if(itemMeta.hasLore()) {
            List<String> lore = new ArrayList<>(itemMeta.getLore());
            lore.replaceAll(l -> String.format(l, args));
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

package me.woutergritter.plugintemplate.util.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
    @Deprecated
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

        if(conf.contains("hide-tooltips") && conf.getBoolean("hide-tooltips")) {
            for(ItemFlag itemFlag : ItemFlag.values()) {
                if(itemFlag.name().startsWith("HIDE_")) {
                    itemMeta.addItemFlags(itemFlag);
                }
            }
        }

        if(conf.contains("item-flags")) {
            conf.getStringList("item-flags").forEach(itemFlagStr -> {
                ItemFlag itemFlag;
                try{
                    itemFlag = ItemFlag.valueOf(itemFlagStr.toUpperCase());
                }catch(IllegalArgumentException e) {
                    return;
                }

                itemMeta.addItemFlags(itemFlag);
            });
        }

        if(conf.contains("leather-armor-color")) {
            if(itemMeta instanceof LeatherArmorMeta) {
                String colorStr = conf.getString("leather-armor-color");
                Color color = null;
                try {
                    color = (Color) Color.class.getField(colorStr.toUpperCase()).get(null);
                }catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ignored) {}

                if(color == null) {
                    try{
                        int red = -1;
                        int green = -1;
                        int blue = -1;

                        if(colorStr.contains("0x") && colorStr.length() == 8) {
                            red   = Integer.parseInt(colorStr.substring(2, 4), 16);
                            green = Integer.parseInt(colorStr.substring(4, 6), 16);
                            blue  = Integer.parseInt(colorStr.substring(6, 8), 16);
                        }else if(colorStr.split(",").length == 3) {
                            String[] parts = colorStr.split(",");
                            red = Integer.parseInt(parts[0]);
                            green = Integer.parseInt(parts[1]);
                            blue = Integer.parseInt(parts[2]);
                        }

                        color = Color.fromRGB(red, green, blue);
                    }catch(Exception ignored) {}
                }

                if(color != null) {
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                    leatherArmorMeta.setColor(color);
                }
            }
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

    /**
     * Performs a check similar to {@link ItemStack#isSimilar(ItemStack)}
     * but with items that have been passed through {@link ItemUtils#formatItemStack(ItemStack, Object...)}
     */
    public static boolean isSimilarFormatted(ItemStack a, ItemStack b) {
        if(a == b) {
            return true;
        }

        if(a == null || b == null || a.getType() != b.getType() ||
                a.getDurability() != b.getDurability()) {
            return false;
        }

        ItemMeta aMeta = a.getItemMeta();
        ItemMeta bMeta = b.getItemMeta();

        if(aMeta == bMeta) {
            return true;
        }

        if(aMeta == null || bMeta == null) {
            return false;
        }

        // Check if they both have or don't have a display name, same with lore.
        if(aMeta.hasDisplayName() != bMeta.hasDisplayName() ||
                aMeta.hasLore() != bMeta.hasLore()) {
            return false;
        }

        // Only check if aMeta has display name and lore, because we know they're both the same.
        boolean hasDisplayName = aMeta.hasDisplayName();
        boolean hasLore = aMeta.hasLore();

        if(hasDisplayName) {
            // Remove display name with '%' for String#format calls
            if(aMeta.getDisplayName().contains("%") ||
                    bMeta.getDisplayName().contains("%")) {
                aMeta.setDisplayName(null);
                bMeta.setDisplayName(null);
            }
        }

        if(hasLore) {
            List<String> aLore = aMeta.getLore();
            List<String> bLore = bMeta.getLore();

            if(aLore.size() != bLore.size()) {
                return false;
            }

            // Remove lines with '%' for String#format calls
            for(int i = 0; i < aLore.size(); i++) {
                if(aLore.get(i).contains("%") ||
                        bLore.get(i).contains("%")) {
                    aLore.remove(i);
                    bLore.remove(i);
                    i--;
                }
            }

            aMeta.setLore(aLore);
            bMeta.setLore(bLore);
        }

        return Bukkit.getItemFactory().equals(aMeta, bMeta);
    }
}

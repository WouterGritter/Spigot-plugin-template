package me.woutergritter.plugintemplate.util.item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIItem {
    private int relativeSlot;
    private ItemStack itemStack;

    public GUIItem(int relativeSlot, ItemStack itemStack) {
        this.relativeSlot = relativeSlot;
        this.itemStack = itemStack;
    }

    public ItemStack insert(Inventory inv, Object... args) {
        int slot = calculateSlot(inv.getSize());

        if(slot < 0 || slot >= inv.getSize()) {
            return null;
        }

        ItemStack inserted = ItemUtils.formatItemStack(itemStack.clone(), args);
        inv.setItem(slot, inserted);

        return inserted;
    }

    public ItemStack insert(ItemStack[] contents, Object... args) {
        int slot = calculateSlot(contents.length);

        if(slot < 0 || slot >= contents.length) {
            return null;
        }

        ItemStack inserted = ItemUtils.formatItemStack(itemStack.clone(), args);
        contents[slot] = inserted;

        return inserted;
    }

    public int calculateSlot(int invSize) {
        if(relativeSlot >= 0) {
            return relativeSlot;
        }else{
            return invSize - Math.abs(relativeSlot);
        }
    }

    public boolean is(int slot, int invSize) {
        return calculateSlot(invSize) == slot;
    }

    public boolean is(int slot, ItemStack[] contents) {
        return is(slot, contents.length);
    }

    public boolean is(int slot, Inventory inv) {
        return is(slot, inv.getSize());
    }

    public boolean is(ItemStack other) {
        return itemStack.isSimilar(other);
    }

    public int getRelativeSlot() {
        return relativeSlot;
    }

    public void setRelativeSlot(int relativeSlot) {
        this.relativeSlot = relativeSlot;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static GUIItem fromConfig(ConfigurationSection conf) {
        int relativeSlot = conf.getInt("slot");
        ItemStack itemStack = ItemUtils.fromConfig(conf);

        if(itemStack == null) {
            return null;
        }

        return new GUIItem(relativeSlot, itemStack);
    }
}

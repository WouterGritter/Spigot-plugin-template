package me.woutergritter.plugintemplate.util.data.configuration;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An empty configuration as per the spec of {@link ConfigurationSection} and {@link Configuration}.
 */
public class EmptyConfiguration implements Configuration {
    @Override
    public Set<String> getKeys(boolean deep) {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        return Collections.emptyMap();
    }

    @Override
    public boolean contains(String path) {
        return false;
    }

    // 1.8 doesnt contain this method - don't override
    public boolean contains(String path, boolean ignoreDefault) {
        return false;
    }

    @Override
    public boolean isSet(String path) {
        return false;
    }

    @Override
    public String getCurrentPath() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Configuration getRoot() {
        return this;
    }

    @Override
    public ConfigurationSection getParent() {
        return null;
    }

    @Override
    public Object get(String path) {
        return null;
    }

    @Override
    public Object get(String path, Object def) {
        return def;
    }

    @Override
    public void set(String path, Object value) {
    }

    @Override
    public ConfigurationSection createSection(String path) {
        return new EmptyConfiguration();
    }

    @Override
    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return new EmptyConfiguration();
    }

    @Override
    public String getString(String path) {
        return null;
    }

    @Override
    public String getString(String path, String def) {
        return def;
    }

    @Override
    public boolean isString(String path) {
        return false;
    }

    @Override
    public int getInt(String path) {
        return 0;
    }

    @Override
    public int getInt(String path, int def) {
        return def;
    }

    @Override
    public boolean isInt(String path) {
        return false;
    }

    @Override
    public boolean getBoolean(String path) {
        return false;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return def;
    }

    @Override
    public boolean isBoolean(String path) {
        return false;
    }

    @Override
    public double getDouble(String path) {
        return 0;
    }

    @Override
    public double getDouble(String path, double def) {
        return def;
    }

    @Override
    public boolean isDouble(String path) {
        return false;
    }

    @Override
    public long getLong(String path) {
        return 0;
    }

    @Override
    public long getLong(String path, long def) {
        return def;
    }

    @Override
    public boolean isLong(String path) {
        return false;
    }

    @Override
    public List<?> getList(String path) {
        return null;
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return def;
    }

    @Override
    public boolean isList(String path) {
        return false;
    }

    @Override
    public List<String> getStringList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Float> getFloatList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Long> getLongList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Byte> getByteList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Short> getShortList(String path) {
        return Collections.emptyList();
    }

    @Override
    public List<Map<?, ?>> getMapList(String path) {
        return Collections.emptyList();
    }

    // 1.8 doesnt contain this method - don't override
    public <T> T getObject(String path, Class<T> clazz) {
        return null;
    }

    // 1.8 doesnt contain this method - don't override
    public <T> T getObject(String path, Class<T> clazz, T def) {
        return def;
    }

    // 1.8 doesnt contain this method - don't override
    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz) {
        return null;
    }

    // 1.8 doesnt contain this method - don't override
    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz, T def) {
        return def;
    }

    @Override
    public Vector getVector(String path) {
        return null;
    }

    @Override
    public Vector getVector(String path, Vector def) {
        return def;
    }

    @Override
    public boolean isVector(String path) {
        return false;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path) {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return def;
    }

    @Override
    public boolean isOfflinePlayer(String path) {
        return false;
    }

    @Override
    public ItemStack getItemStack(String path) {
        return null;
    }

    @Override
    public ItemStack getItemStack(String path, ItemStack def) {
        return def;
    }

    @Override
    public boolean isItemStack(String path) {
        return false;
    }

    @Override
    public Color getColor(String path) {
        return null;
    }

    @Override
    public Color getColor(String path, Color def) {
        return def;
    }

    @Override
    public boolean isColor(String path) {
        return false;
    }

    // 1.8 doesnt contain this method - don't override
    public Location getLocation(String path) {
        return null;
    }

    // 1.8 doesnt contain this method - don't override
    public Location getLocation(String path, Location def) {
        return def;
    }

    // 1.8 doesnt contain this method - don't override
    public boolean isLocation(String path) {
        return false;
    }

    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        return null;
    }

    @Override
    public boolean isConfigurationSection(String path) {
        return false;
    }

    @Override
    public ConfigurationSection getDefaultSection() {
        return null;
    }

    @Override
    public void addDefault(String path, Object value) {
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
    }

    @Override
    public void addDefaults(Configuration defaults) {
    }

    @Override
    public void setDefaults(Configuration defaults) {
    }

    @Override
    public Configuration getDefaults() {
        return null;
    }

    @Override
    public ConfigurationOptions options() {
        return new EmptyConfigurationOptions(this);
    }
}

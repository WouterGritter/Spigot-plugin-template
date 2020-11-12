package me.woutergritter.plugintemplate.util.serialization;

import me.woutergritter.plugintemplate.util.function.ThrowingBiConsumer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class BukkitDataOutputStream extends DataOutputStream {
    public BukkitDataOutputStream(OutputStream out) {
        super(out);
    }

    public void writeLocation(Location location) throws IOException {
        writeUTF(location.getWorld().getName());

        writeDouble(location.getX());
        writeDouble(location.getY());
        writeDouble(location.getZ());

        writeFloat(location.getYaw());
        writeFloat(location.getPitch());
    }

    public void writeBlock(Block block) throws IOException {
        writeUTF(block.getWorld().getName());

        writeInt(block.getX());
        writeInt(block.getY());
        writeInt(block.getZ());
    }

    public void writeUUID(UUID uuid) throws IOException {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public void writeEnum(Enum<?> e) throws IOException {
        writeUTF(e.name());
    }

    public void writeItemStackYaml(ItemStack itemStack) throws IOException {
        // Hacky way of writing an ItemStack, I know..
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("v", itemStack);

        writeUTF(yaml.saveToString());
    }

    // -- Lists and maps -- //

    public <T> void writeList(Collection<T> list, ThrowingBiConsumer<BukkitDataOutputStream, T, IOException> serializeFunction) throws IOException {
        writeInt(list != null ? list.size() : 0);

        if(list != null) {
            for (T t : list) {
                serializeFunction.accept(this, t);
            }
        }
    }

    public <T, U> void writeMap(Map<T, U> map, ThrowingBiConsumer<BukkitDataOutputStream, T, IOException> keySerializeFunction,
                                ThrowingBiConsumer<BukkitDataOutputStream, U, IOException> valueSerializeFunction) throws IOException {
        writeInt(map != null ? map.size() : 0);

        if(map != null) {
            for(T key : map.keySet()) {
                U value = map.get(key);

                keySerializeFunction.accept(this, key);
                valueSerializeFunction.accept(this, value);
            }
        }
    }
}

package me.woutergritter.plugintemplate.util.serialization;

import me.woutergritter.plugintemplate.util.function.ThrowingBiConsumer;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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

    public <T extends Serializable> void write(T t) throws IOException {
        t.serialize(this);
    }

    public <T> void writeList(List<T> list, ThrowingBiConsumer<T, BukkitDataOutputStream, IOException> serializeFunction) throws IOException {
        writeInt(list.size());

        for(T t : list) {
            serializeFunction.accept(t, this);
        }
    }

    public <T extends Serializable> void writeList(List<T> list) throws IOException {
        writeList(list, (t, dos) -> write(t));
    }
}

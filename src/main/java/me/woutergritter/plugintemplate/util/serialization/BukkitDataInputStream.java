package me.woutergritter.plugintemplate.util.serialization;

import me.woutergritter.plugintemplate.util.function.ThrowingFunction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BukkitDataInputStream extends DataInputStream {
    public BukkitDataInputStream(InputStream in) {
        super(in);
    }

    public Location readLocation() throws IOException {
        World world = Bukkit.getWorld(readUTF());

        double x = readDouble();
        double y = readDouble();
        double z = readDouble();

        float yaw = readFloat();
        float pitch = readFloat();

        if(world == null) {
            return null;
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    public Block readBlock() throws IOException {
        World world = Bukkit.getWorld(readUTF());

        int x = readInt();
        int y = readInt();
        int z = readInt();

        if(world == null) {
            return null;
        }

        return world.getBlockAt(x, y, z);
    }

    public UUID readUUID() throws IOException {
        long mostSigBits = readLong();
        long leastSigBits = readLong();

        return new UUID(mostSigBits, leastSigBits);
    }

    public <T extends Enum<T>> T readEnum(Class<T> enumType) throws IOException {
        return Enum.valueOf(enumType, readUTF());
    }

    public <T extends Serializable> T read(Class<T> clazz) {
        try {
            Method deserialize = clazz.getMethod("deserialize", BukkitDataInputStream.class);

            return (T) deserialize.invoke(null, this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> List<T> readList(ThrowingFunction<BukkitDataInputStream, T, IOException> deserializeFunction) throws IOException {
        int size = readInt();
        List<T> list = new ArrayList<>(size);

        for(int i = 0; i < size; i++) {
            T t = deserializeFunction.apply(this);
            list.add(t);
        }

        return list;
    }

    public <T extends Serializable> List<T> readList(Class<T> clazz) throws IOException {
        return readList(dis -> read(clazz));
    }
}

package me.woutergritter.plugintemplate.util.serialization;

import java.io.IOException;

public interface Serializable {
    void serialize(BukkitDataOutputStream dos) throws IOException;
}

package me.woutergritter.plugintemplate.util.data.configuration;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;

import java.util.Objects;

/**
 * Empty configuration options.
 */
public class EmptyConfigurationOptions extends ConfigurationOptions {
    protected EmptyConfigurationOptions(Configuration configuration) {
        super(configuration);
    }

    @Override
    public ConfigurationOptions pathSeparator(char pathSeparator) {
        return this; // Override to do nothing
    }

    @Override
    public ConfigurationOptions copyDefaults(boolean copyDefaults) {
        return this; // Override to do nothing
    }

    @Override
    public boolean equals(Object otherObj) {
        if(!(otherObj instanceof EmptyConfigurationOptions)) {
            return false;
        }

        if(otherObj == this) {
            return true;
        }

        EmptyConfigurationOptions other = (EmptyConfigurationOptions) otherObj;
        return other.configuration() == this.configuration();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.configuration());
    }
}

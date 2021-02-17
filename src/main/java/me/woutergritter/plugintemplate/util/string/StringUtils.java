package me.woutergritter.plugintemplate.util.string;

import me.woutergritter.plugintemplate.util.data.configuration.EmptyConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class StringUtils {
    private StringUtils() {
    }

    public static String prettifyString(Object obj) {
        String s = String.valueOf(obj);

        // Replace - and _ with spaces
        s = s.replace('-', ' ').replace('_', ' ');

        // toLowerCase whole string, except for the first char
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

        return s;
    }

    public static String toScreamingSnakeCase(Object obj) {
        String s = String.valueOf(obj);

        // Replace space and - with _
        s = s.replace(' ', '_').replace('-', '_');

        // toUpperCase whole string
        s = s.toUpperCase();

        return s;
    }

    public static String createProgressBar(char c, int length, double completedPercent, ChatColor completedColor, ChatColor nonCompletedColor) {
        StringBuilder sb = new StringBuilder(length + 4);
        sb.append(completedColor);

        int completedUntilIndex = (int) (completedPercent * length);
        for(int i = 0; i < length; i++) {
            if(completedUntilIndex == i) {
                sb.append(nonCompletedColor);
            }

            sb.append(c);
        }

        return sb.toString();
    }

    public static String formatTime(int seconds, ConfigurationSection _conf) {
        List<String> parts = new ArrayList<>();

        if(_conf == null) {
            _conf = new EmptyConfiguration();
        }
        final ConfigurationSection conf = _conf;

        seconds = formatTimeUnit(
                seconds,
                60 * 60 * 24,
                () -> conf.getString("day-singular", "day"),
                () -> conf.getString("day-plural", "days"),
                parts
        );

        seconds = formatTimeUnit(
                seconds,
                60 * 60,
                () -> conf.getString("hour-singular", "hour"),
                () -> conf.getString("hour-plural", "hours"),
                parts
        );

        seconds = formatTimeUnit(
                seconds,
                60,
                () -> conf.getString("minute-singular", "minute"),
                () -> conf.getString("minute-plural", "minutes"),
                parts
        );

        seconds = formatTimeUnit(
                seconds,
                1,
                () -> conf.getString("second-singular", "second"),
                () -> conf.getString("second-plural", "seconds"),
                parts
        );

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < parts.size(); i++) {
            if(parts.size() > 1) {
                if(i == parts.size() - 1) {
                    sb.append(conf.getString("spliterator-last", " and "));
                }else if(i != 0) {
                    sb.append(conf.getString("spliterator", ", "));
                }
            }

            sb.append(parts.get(i));
        }

        return sb.toString();
    }

    public static String formatTime(int seconds) {
        return formatTime(seconds, null);
    }

    private static int formatTimeUnit(int secondsRemaining, int unitSeconds, Supplier<String> singularProvider, Supplier<String> pluralProvider, List<String> parts) {
        int units = secondsRemaining / unitSeconds;
        secondsRemaining -= units * unitSeconds;

        if(units > 0) {
            String text = String.valueOf(units) + ' ' + (units == 1 ? singularProvider : pluralProvider).get();
            parts.add(text);
        }

        return secondsRemaining;
    }
}

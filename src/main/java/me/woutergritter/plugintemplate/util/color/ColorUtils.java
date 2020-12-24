package me.woutergritter.plugintemplate.util.color;

import org.bukkit.Color;

public class ColorUtils {
    private ColorUtils() {
    }

    public static Color fromString(String colorStr) {
        if(colorStr == null) {
            return null;
        }

        try {
            return (Color) Color.class.getField(colorStr.toUpperCase()).get(null);
        }catch(Exception ignored) {}

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

            return Color.fromRGB(red, green, blue);
        }catch(Exception ignored) {}

        return null;
    }
}

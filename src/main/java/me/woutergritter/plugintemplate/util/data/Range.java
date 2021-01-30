package me.woutergritter.plugintemplate.util.data;

public class Range {
    public final double min, max;

    /**
     * @param min Minimum (inclusive)
     * @param max Maximum (exclusive)
     */
    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(double n) {
        return n >= min && n < max;
    }

    public double random() {
        return Math.random() * (max - min) + min;
    }

    public static Range parse(String s, boolean allowDouble) {
        if(!s.contains("-")) {
            return null;
        }

        String[] parts = s.split("-");
        if(parts.length != 2) {
            return null;
        }

        double min, max;
        try{
            min = Double.parseDouble(parts[0]);
            max = Double.parseDouble(parts[1]);
        }catch(NumberFormatException e) {
            return null;
        }

        return new Range(min, max);
    }

    public static Range parse(String s) {
        return parse(s, true);
    }
}

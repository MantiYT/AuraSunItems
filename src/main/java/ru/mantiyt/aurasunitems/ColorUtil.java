package ru.mantiyt.aurasunitems;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.*;

public class ColorUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:#[a-fA-F0-9]{6}:#[a-fA-F0-9]{6}>(.*?)</gradient>");
    private static final char COLOR_CHAR = '&';

    public static String translate(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        message = translateHexCodes(message);
        message = translateStandardCodes(message);

        return message;
    }

    public static List<String> translate(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            return messages;
        }

        List<String> translated = new ArrayList<>();
        for (String message : messages) {
            translated.add(translate(message));
        }

        return translated;
    }

    private static String translateHexCodes(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);

        while (matcher.find()) {
            String hexCode = matcher.group(0);
            ChatColor color = ChatColor.of(hexCode);
            matcher.appendReplacement(buffer, color.toString());
        }

        return matcher.appendTail(buffer).toString();
    }

    private static String translateStandardCodes(String message) {
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, message);
    }

    public static String translateGradients(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        Matcher matcher = GRADIENT_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String text = matcher.group(1);
            String[] colors = matcher.group(0)
                    .substring(10, matcher.group(0).indexOf('>'))
                    .split(":");

            if (colors.length >= 2) {
                String gradient = applyGradient(text, colors[0], colors[1]);
                matcher.appendReplacement(buffer, gradient);
            }
        }

        return matcher.appendTail(buffer).toString();
    }

    private static String applyGradient(String text, String startColor, String endColor) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        java.awt.Color start = hexToColor(startColor);
        java.awt.Color end = hexToColor(endColor);

        StringBuilder result = new StringBuilder();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);

            if (character == ' ') {
                result.append(character);
                continue;
            }

            float ratio = (float) i / (length - 1);
            int red = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
            int green = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
            int blue = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

            ChatColor color = ChatColor.of(new java.awt.Color(red, green, blue));
            result.append(color).append(character);
        }

        return result.toString();
    }

    private static java.awt.Color hexToColor(String hex) {
        hex = hex.replace("#", "");

        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);

        return new java.awt.Color(red, green, blue);
    }

    public static String stripColor(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        return ChatColor.stripColor(translate(message));
    }

    public static boolean isValidHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            return false;
        }

        return HEX_PATTERN.matcher(hex).matches();
    }

    public static String colorCodeToHex(String colorCode) {
        if (colorCode == null || colorCode.length() != 2) {
            return null;
        }

        ChatColor color = ChatColor.getByChar(colorCode.charAt(1));
        if (color == null) {
            return null;
        }

        if (color.equals(BLACK)) {
            return "#000000";
        } else if (color.equals(DARK_BLUE)) {
            return "#0000AA";
        } else if (color.equals(DARK_GREEN)) {
            return "#00AA00";
        } else if (color.equals(DARK_AQUA)) {
            return "#00AAAA";
        } else if (color.equals(DARK_RED)) {
            return "#AA0000";
        } else if (color.equals(DARK_PURPLE)) {
            return "#AA00AA";
        } else if (color.equals(GOLD)) {
            return "#FFAA00";
        } else if (color.equals(GRAY)) {
            return "#AAAAAA";
        } else if (color.equals(DARK_GRAY)) {
            return "#555555";
        } else if (color.equals(BLUE)) {
            return "#5555FF";
        } else if (color.equals(GREEN)) {
            return "#55FF55";
        } else if (color.equals(AQUA)) {
            return "#55FFFF";
        } else if (color.equals(RED)) {
            return "#FF5555";
        } else if (color.equals(LIGHT_PURPLE)) {
            return "#FF55FF";
        } else if (color.equals(YELLOW)) {
            return "#FFFF55";
        } else if (color.equals(WHITE)) {
            return "#FFFFFF";
        }
        return null;
    }

    public static String rainbow(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        ChatColor[] colors = {
                ChatColor.RED,
                ChatColor.GOLD,
                ChatColor.YELLOW,
                ChatColor.GREEN,
                ChatColor.AQUA,
                ChatColor.BLUE,
                ChatColor.LIGHT_PURPLE
        };

        int colorIndex = 0;
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                result.append(c);
                continue;
            }

            result.append(colors[colorIndex % colors.length]).append(c);
            colorIndex++;
        }

        return result.toString();
    }
}
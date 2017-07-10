package eu.iamgio.base;

import eu.iamgio.jrfl.api.commands.Command;

/**
 * @author Gio
 */
public class Utils {

    private Utils(){}

    public static String USAGE_MESSAGE(Command command) {
        return "§7Invalid syntax. Usage: §f" + command.getUsage();
    }
}

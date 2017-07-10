package eu.iamgio.base.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Clear extends Command {

    public Clear() {
        super("clear", "[Base] Clears the console", "clear");
    }

    @Override
    public void onCommand(String[] args) {
        Console.getConsole().clear();
    }
}

package eu.iamgio.base.commands;

import eu.iamgio.jrfl.api.commands.Command;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class End extends Command {

    public End() {
        super("end", "[Base] Closes the console", "end");
    }

    @Override
    public void onCommand(String[] args) {
        System.exit(0);
    }
}

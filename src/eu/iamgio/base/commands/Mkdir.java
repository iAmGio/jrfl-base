package eu.iamgio.base.commands;

import eu.iamgio.base.Utils;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Mkdir extends Command {

    public Mkdir() {
        super("mkdir", "[Base] Creates a new directory", "mkdir <%directory>");
        setArgCompletion(0, new FileTabCompletion().setOnlyFolders(true));
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.sendMessage(Utils.USAGE_MESSAGE(this));
            return;
        }
        File dir = FileTabCompletion.file(console.getFolder(), args[0]);
        if(!dir.exists()) {
            try {
                FileUtils.forceMkdir(dir);
                console.sendMessage("Created directory " + dir);
            }
            catch(IOException e) {
                console.sendMessage("§cCannot create directory");
            }
        }
        else console.sendMessage("§cThe directory already exists");
    }
}

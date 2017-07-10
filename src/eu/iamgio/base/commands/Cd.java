package eu.iamgio.base.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import eu.iamgio.jrfl.api.commands.completion.TabCompletion;
import eu.iamgio.jrfl.api.configuration.Formatter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Cd extends Command {

    public Cd() {
        super("cd", "[Base] Moves to the selected directory", "cd [%folder] [-abs]");
        setArgCompletion(1, TabCompletion.auto(Arrays.asList("-abs")));
        setArgCompletion(0, new FileTabCompletion().setOnlyFolders(true));
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.setFolder(new File(Formatter.getInstance().format("{home}")));
        }
        else {
            File file =
                    args.length == 2 && args[1].equals("-abs") ?
                            new File(FileTabCompletion.trasformedPath(args[0])) :
                            FileTabCompletion.file(console.getFolder(), args[0]);
            if(file.exists() && !file.isFile()) {
                try {
                    console.setFolder(file.getCanonicalFile());
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
            else console.sendMessage("§cInvalid directory");
        }
    }
}

package eu.iamgio.base.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;

import java.io.File;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Ls extends Command {

    public Ls() {
        super("ls", "[Base] Shows the content of the current folder", "ls [%folder]");
        setArgCompletion(0, new FileTabCompletion().setOnlyFolders(true));
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        File folder;
        if(args.length == 0) {
            folder = console.getFolder();
        }
        else {
            File file = FileTabCompletion.file(console.getFolder(), args[0]);
            if(file.exists()) {
                folder = file;
            }
            else {
                console.sendMessage("§cInvalid directory");
                return;
            }
        }
        String text = "";
        for(File file : folder.listFiles()) {
            text += (file.isFile() ? "§a" : "§b") + file.getName() + "  ";
        }
        console.sendMessage(text);
    }
}

package eu.iamgio.base.commands;

import eu.iamgio.base.Utils;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Mv extends Command {

    public Mv() {
        super("mv", "[Base] Moves a file to another directory", "mv <%file> <%destination>");
        setArgCompletion(1, new FileTabCompletion().setOnlyFolders(true));
        setArgCompletion(0, new FileTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length < 2) {
            console.sendMessage(Utils.USAGE_MESSAGE(this));
            return;
        }
        File file = FileTabCompletion.file(console.getFolder(), args[0]);
        File dest = FileTabCompletion.file(console.getFolder(), args[1]);

        if(!file.exists()) {
            console.sendMessage("§cInvalid file");
            return;
        }
        else if(dest.exists() && !dest.isFile()) {
            console.sendMessage("§cInvalid destination");
            return;
        }
        else if(!dest.exists()) {
            try {
                FileUtils.forceMkdir(dest.getParentFile());
            }
            catch(IOException e) {
                console.sendMessage("§cCannot move file");
                return;
            }
        }

        try {
            Files.move(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            console.sendMessage("§aFile moved successfully");
        }
        catch(IOException e) {
            console.sendMessage("§cCannot move file");
        }
    }
}

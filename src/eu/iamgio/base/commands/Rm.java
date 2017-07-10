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
public class Rm extends Command {

    public Rm() {
        super("rm", "[Base] Removes a file from the disk", "rm <%file>");
        setArgCompletion(0, new FileTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 1) {
            File file = FileTabCompletion.file(console.getFolder(), args[0]);
            if(file.exists()) {
                try {
                    if(file.getCanonicalPath().equals(console.getFolder().getAbsolutePath())) {
                        console.sendMessage("§cCannot delete your current folder");
                        return;
                    }
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                boolean success;
                if(file.isFile()) {
                    success = file.delete();
                }
                else {
                    try {
                        FileUtils.deleteDirectory(file);
                        success = true;
                    }
                    catch(IOException e) {
                        success = false;
                    }
                }
                if(success) {
                    console.sendMessage("File deleted successfully");
                }
                else console.sendMessage("§cCannot delete file");
            }
            else console.sendMessage("§cInvalid file");
        }
        else console.sendMessage(Utils.USAGE_MESSAGE(this));
    }
}

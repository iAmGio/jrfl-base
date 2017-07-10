package eu.iamgio.base.commands;

import eu.iamgio.base.Utils;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Unzip extends Command {

    public Unzip() {
        super("unzip", "[Base] Unzips a compressed archive", "unzip <%zipfile> [%destination]");
        setArgCompletion(1, new FileTabCompletion().setOnlyFolders(true));
        setArgCompletion(0, new FileTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.sendMessage(Utils.USAGE_MESSAGE(this));
            return;
        }
        ZipFile file;
        try {
            file = new ZipFile(FileTabCompletion.file(console.getFolder(), args[0]));
        }
        catch(ZipException e) {
            console.sendMessage("§cInvalid archive");
            return;
        }
        try {
            file.extractAll(args.length >= 2 ?
                    FileTabCompletion.file(console.getFolder(), args[1]).getAbsolutePath() :
                    console.getFolder().getAbsolutePath());
        }
        catch(ZipException e) {
            console.sendMessage("§cAn error occurred during the process");
        }
    }
}

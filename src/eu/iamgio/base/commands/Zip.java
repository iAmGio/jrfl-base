package eu.iamgio.base.commands;

import eu.iamgio.base.Utils;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Zip extends Command {

    public Zip() {
        super("zip", "[Base] Puts the current folder into a compressed archive", "zip <%destination>");
        setArgCompletion(0, new FileTabCompletion().setOnlyFolders(true));
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.sendMessage(Utils.USAGE_MESSAGE(this));
            return;
        }

        try {
            ZipFile file = new ZipFile(FileTabCompletion.file(console.getFolder(), args[0]));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            file.addFolder(console.getFolder(), parameters);
        }
        catch(ZipException e) {
            console.sendMessage("§cAn error occurred during the process");
            return;
        }

        console.sendMessage("§aSuccessfully created " + args[0]);
    }
}

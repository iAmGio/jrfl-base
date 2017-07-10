package eu.iamgio.base.commands;

import eu.iamgio.base.Utils;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import eu.iamgio.jrfl.api.commands.completion.TabCompletion;
import eu.iamgio.jrfl.api.components.EditorComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gio
 */
@SuppressWarnings("unused")
public class Np extends Command {

    public Np() {
        super("np", "[Base] Basic file editor", "np <%file>");
        setArgCompletion(1, TabCompletion.auto(Arrays.asList("-newfile")));
        setArgCompletion(0, new FileTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.sendMessage(Utils.USAGE_MESSAGE(this));
            return;
        }
        boolean newFile = args.length >= 2 && args[1].equals("-newfile");
        File file = FileTabCompletion.file(console.getFolder(), args[0]);
        if(newFile || file.exists()) {
            if(!newFile && !file.isFile()) {
                console.sendMessage("§cCannot read directories");
                return;
            }
            List<String> lines;
            try {
                lines = newFile ? new ArrayList<>() : Files.readAllLines(file.toPath());
            }
            catch(IOException e) {
                console.sendMessage("§cCannot read file");
                return;
            }
            EditorComponent component = new EditorComponent(lines);
            Runnable save = () -> {
                try {
                    if(newFile && !file.exists()) {
                        FileUtils.forceMkdir(file.getParentFile());
                        if(!file.createNewFile()) {
                            console.sendMessage("§cCannot create file " + file.getName());
                            return;
                        }
                    }
                    Files.write(file.toPath(), component.getLines());
                    console.sendMessage("§a" + file.getName() + " successfully saved");
                }
                catch(IOException e) {
                    console.sendMessage("§cCannot write file " + file.getName());
                }
            };
            Runnable close = () -> console.removeComponent(component);

            component.addKeyCombination(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), save);
            component.addKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE), close);
            component.getTextArea().setPromptText("CTRL+S to save, ESC to close");
            console.showComponent(component);
        }
        else console.sendMessage("§cInvalid file");
    }
}

package command;

import java.util.Stack;

/**
 * Class to help execute and undo commands.
 */
public class CommandProcessor {
    private Stack<ICommand> commandHistory;

    public CommandProcessor() {
        commandHistory = new Stack<>();
    }

    public void execute(ICommand command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undo() {
        ICommand command = commandHistory.pop();
        command.undo();
    }
}

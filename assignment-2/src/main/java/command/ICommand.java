package command;

/**
 * Interface to create commands that can support undoing of operations.
 */
public interface ICommand {
    void execute();
    void undo();
}

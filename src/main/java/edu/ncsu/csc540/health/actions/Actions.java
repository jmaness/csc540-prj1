package edu.ncsu.csc540.health.actions;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.function.Function;

public class Actions {
    public static Function<Action, Action> notYetImplemented = (Action action) -> (TextIO textIO) -> {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        terminal.println("Not yet implemented");
        terminal.println();
        return action;
    };

    public static Action exit = (TextIO textIO) -> {
        textIO.dispose();
        return null;
    };
}

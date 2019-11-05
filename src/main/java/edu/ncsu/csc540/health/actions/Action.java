package edu.ncsu.csc540.health.actions;

import org.beryx.textio.TextIO;

import java.util.function.Function;

public interface Action extends Function<TextIO, Action> {}

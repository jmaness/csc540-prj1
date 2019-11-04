package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;

import java.util.function.Function;

public interface Page extends Function<TextIO, Page> {}

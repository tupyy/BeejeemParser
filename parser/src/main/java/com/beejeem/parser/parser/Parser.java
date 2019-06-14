package com.beejeem.parser.parser;

import org.antlr.v4.runtime.misc.ParseCancellationException;


public interface Parser<T> {
    T parse(String code) throws ParseCancellationException;
}

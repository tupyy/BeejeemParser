package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;
import org.antlr.v4.runtime.misc.ParseCancellationException;


public interface Parser {
    Program parse(String code) throws ParseCancellationException;
}

package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;

public interface Parser {
    Program parse(String code);
}

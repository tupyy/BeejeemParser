package com.beejeem.parser.parser;

import com.beejeem.parser.domain.DefaultProgram;

public interface Parser {
    DefaultProgram parse(String code);
}

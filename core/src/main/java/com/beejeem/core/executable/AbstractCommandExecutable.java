package com.beejeem.core.executable;

import com.beejeem.core.result.JsonExtractor;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.VariableParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandExecutable implements CommandExecutable{

    private static Logger logger = LogManager.getLogger(AbstractCommandExecutable.class);
    private VariableParser resultParser;

    public List<Variable> extractOutputVariables(String output) {

        if (this.getResultParser() == null) {
            return new ArrayList<>();
        }

        try {
            return this.getResultParser().parse(JsonExtractor.extract(output));
        }
        catch (ParseCancellationException ex) {
            logger.error(ex.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Return the parser used to parse the output
     * @return
     */
    public VariableParser getResultParser() {
        return resultParser;
    }

    /**
     * set the output stream parser. This parser will return a list of variables parsed in the output stream
     * @param resultParser
     */
    public void setResultParser(VariableParser resultParser) {
        this.resultParser = resultParser;
    }
}

package com.beejeem.core.command.result;

import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.JsonParser;
import com.beejeem.parser.parser.VariableParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

public class JsonResultParser implements ResultParser {

    private static Logger logger = LogManager.getLogger(JsonResultParser.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Variable> parse(String output) {
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
    private VariableParser getResultParser() {
        return new JsonParser();
    }
}

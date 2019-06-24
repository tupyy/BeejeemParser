package com.beejeem.core;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static String readFileAsString(String fileName)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {
        try {
            String data = readFileAsString("/home/cosmin/Projects/BeejeemParser/code.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

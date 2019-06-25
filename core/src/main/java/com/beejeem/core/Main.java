package com.beejeem.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static String readFileAsString(String fileName)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {
        try {
            ApplicationContext context = new
                    AnnotationConfigApplicationContext(CoreContext.class);

            Core core = context.getBean(Core.class);
            System.out.print("End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

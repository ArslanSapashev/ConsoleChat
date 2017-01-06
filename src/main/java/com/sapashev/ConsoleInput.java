package com.sapashev;

import java.util.Scanner;

/**
 * Reads data from System.in
 * @author Arslan Sapashev
 * @since 06.01.2017
 * @version 1.0
 */
public class ConsoleInput implements Input{
    Scanner scanner;

    public ConsoleInput(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     *
     * @param question - prompt for user input.
     * @return
     */
    public String ask (String question) {
        System.out.println(question);
        return scanner.hasNext() ? scanner.next() : "";
    }
}

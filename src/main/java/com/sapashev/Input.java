package com.sapashev;

/**
 * Interface describes user input methods
 * @author Arslan Sapashev
 * @since 06.01.2016
 * @version 1.0
 */
public interface Input {
    /**
     * Prompts user to input data and returns it to caller.
     * @param question - prompt for user input.
     * @return - user input
     */
    String ask (String question);
}

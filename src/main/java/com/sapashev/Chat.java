package com.sapashev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Describes console chat application
 * @author Arslan Sapashev
 * @since 06.01.2017
 * @version 1.0
 */
public class Chat {
    public static void main (String[] args) throws IOException {
        new Chat().start(args[0]);
    }

    public void start(String filename) throws IOException{
        try(Scanner scanner = new Scanner(System.in)){
            Input input = new ConsoleInput(scanner);
            String userInput;
            boolean isFinished = false;
            boolean isPausedAnswer = false;

            while (!isFinished){
                if (!(userInput = input.ask("Введите фразу")).equalsIgnoreCase("закончить")){
                    isPausedAnswer = checkIsPaused(isPausedAnswer, userInput);
                    if(isPausedAnswer){
                        continue;
                    } else {
                        System.out.println(getAnswer(filename));
                    }
                } else {
                    isFinished = true;
                }
            }
        }
    }

    /**
     * If user input is equals to "стоп" - isPausedAnswer assigned to true;
     * If user input is equals to "продолжить" - isPausedAnswer assigned to false;
     * Otherwise isPausedAnswer stays unmodified.
     * @param isPausedAnswer - current pause status
     * @param userInput - string typed by user.
     * @return - true - user typed "стоп" or didn't typed "продолжить",
     * false - user typed "продолжить" or didn't typed "стоп".
     */
    private boolean checkIsPaused (boolean isPausedAnswer, String userInput) {
        if("стоп".equalsIgnoreCase(userInput)){
            isPausedAnswer = true;
        }
        if ("продолжить".equalsIgnoreCase(userInput)) {
            isPausedAnswer = false;
        }
        return isPausedAnswer;
    }

    /**
     * Reads lines from source file and return anyone randomly chosen;
     * @param filename - file which contains lines with answers.
     * @return - randomly chosen line or "" if the file has no any lines;
     * @throws IOException
     */
    private String getAnswer(String filename) throws IOException{
        try(Stream<String> lines = Files.lines(Paths.get(filename))){
            return lines.findAny().orElse("");
        }
    }
}

package com.sapashev;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Describes console chat application
 * @author Arslan Sapashev
 * @since 06.01.2017
 * @version 1.0
 */
public class Chat {
    private final Logger LOG = LoggerFactory.getLogger(Chat.class);

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
                    LOG.info("User typed {}", userInput);
                    isPausedAnswer = checkIsPaused(isPausedAnswer, userInput);
                    if(!isPausedAnswer){
                        String answer = getAnswer(filename);
                        System.out.println(answer);
                        LOG.info("The answer is {}", answer);
                    }
                } else {
                    isFinished = true;
                }
            }
            LOG.info("Chat finished");
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
            LOG.info("STOP to answer to user input");
        }
        if ("продолжить".equalsIgnoreCase(userInput)) {
            isPausedAnswer = false;
            LOG.info("RESUME to answer to user input");
        }
        return isPausedAnswer;
    }

    /**
     * Reads lines from source file and return anyone randomly chosen;
     * @param filename - file which contains lines with answers.
     * @return - randomly chosen line;
     * @throws IOException
     */
    private String getAnswer(String filename) throws IOException{
        Random r = new Random();
        try(Stream<String> lines = Files.lines(Paths.get(filename))){
            List<String> strings = lines.collect(Collectors.toList());
            return strings.get(r.nextInt(strings.size()));
        }
    }
}

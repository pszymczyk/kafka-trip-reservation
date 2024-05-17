package com.pszymczyk.kafkatripreservation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void failSometimes() {
        Random rand = new Random();
        int randomNum = rand.nextInt(0, 5);
        if (randomNum == 2) {
            throw new RuntimeException("Random number 2 = exception!");
        }
    }


    public static void sleeep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.error("Sleeping exception.", e);
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(String path) {
        try {
            return Files.readAllLines(Path.of(Utils.class.getClassLoader().getResource(path).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package ru.job4j.tracker;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public interface Input {

    String ask(String question);

    int ask(String question, int[] range);
}

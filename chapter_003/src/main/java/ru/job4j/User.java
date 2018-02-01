package ru.job4j;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class User {
    private int id;
    private String name;
    private String city;

    public User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return this.id;
    }
}

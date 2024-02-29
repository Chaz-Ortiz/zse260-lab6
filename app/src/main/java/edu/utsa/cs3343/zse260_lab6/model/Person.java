package edu.utsa.cs3343.zse260_lab6.model;

/**
 * An abstract class representing a person.
 * This serves as the base class for specific types of persons,
 * such as Avengers, with common attributes like real name, alias, height, weight, and photo.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public abstract class Person {

    protected String realname;
    protected String alias;
    protected int height;
    protected String weight;
    protected String photo;

    // Main constructor
    /**
     * Main constructor for creating a person with specified attributes.
     * @param realname The real name of the person.
     * @param alias    The alias or superhero name of the person.
     * @param height   The height of the person.
     * @param weight   The weight of the person.
     * @param photo    The photo associated with the person.
     */
    public Person(String realname, String alias, int height, String weight, String photo) {
        this.realname = realname;
        this.alias = alias;
        this.height = height;
        this.weight = weight;
        this.photo = photo;
    }

    public Person(String realName) {     // Constructor for subclasses to override if needed
        this.realname = realName;
    }

    /**
     * Constructor for subclasses to override if needed.
     *
     * @param realName The real name of the person.
     * @param gender   The gender of the person.
     * @param height   The height of the person.
     * @param weight   The weight of the person.
     */
    // Constructor for subclasses to override if needed
    public Person(String realName, String gender, int height, int weight) {
        this.realname = realName;
        this.height = height;
        this.weight = Integer.toString(weight); // Convert int to String for consistency
    }

    /**
     * Default constructor with default values.
     */
    public Person() {
        this.realname = "Default Name";
        this.alias = "Default Alias";
        this.height = 0;
        this.weight = "0";
        this.photo = "default.jpg";
    }

    // Getters and setters...
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * Get a string representation of the person.
     *
     * @return A string representation of the person.
     */
    @Override
    public String toString() {
        return "Person{" +
                "realname='" + realname + '\'' +
                ", alias='" + alias + '\'' +
                ", height=" + height +
                ", weight='" + weight + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
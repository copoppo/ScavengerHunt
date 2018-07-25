package com.google.sample.cloudvision;

/**
 * Created by yuhu on 11/27/16.
 */

public class ScavengeObject
{
    //INSTANCE VARIABLES
    String name;
    String location;


    /**
     * Constructor
     * @param s
     */
    public ScavengeObject(String s)
    {
        name = s;
    }


    /**
     * Set the location
     * @param s
     */
    public void setLocation(String s)
    {
        location = s;
    }

    //getter method
    public String getName()
    {
        return name;
    }

    //getter method
    public String getLocation()
    {
        return location;
    }
}

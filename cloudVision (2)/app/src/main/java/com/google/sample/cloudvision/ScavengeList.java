package com.google.sample.cloudvision;

/**
 * Created by yuhu on 11/27/16.
 */
import java.util.HashSet;


public class ScavengeList {

    //INSTANCE VARIABLES
    private HashSet<ScavengeObject> scavengeList;
    private HashSet<String> nameList;
    private static String[] foodList = {
            "apple", "banana", "orange", "peach", "plum", "grape", "melon"};

    /**
     * Constructor
     */
    public ScavengeList()
    {
        //create the hashsets
        scavengeList = new HashSet<>(); //this is a hashset of ScavengeObjects
        nameList = new HashSet<>(); //this is a hashset of the ScavengeObjects names so that it's easier to search for
        createList();
    }

    public void insert(ScavengeObject o){
        scavengeList.add(o);
    }

    /**
     * getter method for scavengeList
     * @return menu
     */
    public HashSet<ScavengeObject> getScavengeList()
    {
        return scavengeList;
    }

    public HashSet<String> getNameList(){return nameList;}

    /**
     * Create the scavengeList and nameList
     */
    public void createList()
    {
        for (int i = 0; i < foodList.length; i++)
        {

            nameList.add(foodList[i]);
        }
    }

    /**
     * Search for the object
     * @param s
     * @return
     */
    public boolean search (String s){
        if(nameList.contains(s)){
            return true;
        }else{
            return false;
        }
    }





}


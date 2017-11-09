package com.example.aayush.firebasecrud;

/**
 * Created by Aayush on 11/9/2017.
 */

public class Actor {
    public String actor_id;
    public String actor_name;
    public  String actor_industry;
    public Actor(){}
    public Actor(String actor_id,String actor_name,String actor_industry){
        this.actor_id = actor_id;
        this.actor_name = actor_name;
        this.actor_industry = actor_industry;
    }

    public String getActor_id() {
        return actor_id;
    }

    public String getActor_name() {
        return actor_name;
    }

    public String getActor_industry() {
        return actor_industry;
    }
}

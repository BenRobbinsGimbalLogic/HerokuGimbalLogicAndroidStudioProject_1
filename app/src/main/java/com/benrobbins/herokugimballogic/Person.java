package com.benrobbins.herokugimballogic;

import com.google.gson.annotations.SerializedName;

class Person {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }
}

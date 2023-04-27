package com.example.shrinkitdataview

class User {
    // variables for our first name,
    // team name, steps
    //val id: String
    val name: String
    val teamName: String
    var steps: String

    constructor( name: String, teamName: String, steps: String ){
        this.name = name
        this.teamName = teamName
        this.steps = steps
    }
}
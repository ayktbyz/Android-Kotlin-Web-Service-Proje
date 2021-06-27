package com.anroid.aykutbeyaz.entity

class Country {
    var id  = 0
    var name = ""

    //Constructor
    constructor(){

    }

    constructor(id : Int , name : String){
        this.id = id
        this.name = name
        println("constructor calisti")
    }

    fun getid() : Int{
        return this.id
    }
    fun getname() : String{
        return this.name
    }

    fun setid(id : Int){
         this.id = id
    }
    fun setname(name : String){
        this.name = name
    }

    @Override
    override fun toString(): String {
        return this.name
    }
}
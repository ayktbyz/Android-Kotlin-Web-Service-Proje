package com.anroid.aykutbeyaz.entity

class Place {
    var id  = 0
    var name = ""
    var countryid = 0

    constructor(){
    }

    constructor(id: Int, name: String, countryid: Int) {
        this.id = id
        this.name = name
        this.countryid = countryid
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

    fun getcountryid() : Int{
        return this.countryid
    }

    fun setcountryid(id: Int){
        this.countryid = id
    }

    @Override
    override fun toString(): String {
        return this.name
    }
}
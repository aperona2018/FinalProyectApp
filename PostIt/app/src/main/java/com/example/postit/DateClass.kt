package com.example.postit

class DateClass {
    var dateTitle : String? = null
    var dateText : String? = null
    var creator : String? = null

    constructor(dateTitle: String?, dateText: String?, creator: String?){
        this.dateTitle = dateTitle
        this.dateText = dateText
        this.creator = creator
    }

    constructor()
}
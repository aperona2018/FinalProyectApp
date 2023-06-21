package com.example.postit

class NoteClass {
    var noteTitle: String? = null
    var noteContent : String? = null
    var noteCreator: String? = null

    constructor(noteTitle: String?, noteContent: String?){
        this.noteTitle = noteTitle
        this.noteContent = noteContent
    }

    constructor()
}
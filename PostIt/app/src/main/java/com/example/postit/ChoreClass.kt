package com.example.postit

class ChoreClass {
    var choreTitle : String? = null
    var choreDesc : String? = null
    var chorePriority : String? = null
    var choreImage : String? = null

    constructor(choreTitle: String?, choreDesc: String?, chorePriority: String?, choreImage: String?){
        this.choreTitle = choreTitle
        this.choreDesc = choreDesc
        this.chorePriority = chorePriority
        this.choreImage = choreImage
    }

}
package com.example.gonbe_house.Post

class  PostInfo{
    var UserUID:String?=null
    var text:String?=null
    var postImage:String?=null
    constructor(UserUID:String,text:String,postImage:String){
        this.UserUID=UserUID
        this.text=text
        this.postImage=postImage
    }
}
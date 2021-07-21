package com.kujira.homestay.data.model.response

class AddRoomModel {
    var id: String = ""
    var address: String = ""
    var typeRoom: String = ""
    var nameRoom: String = ""
    var s_room: String = ""
    var numberSleepRoom: String = ""
    var convenient: String = ""
    var introduce: String = ""
    var imageRoom1: String = ""
    var imageRoom2: String = ""
    var status: String = ""
    var price: String = ""
    var uid: String = ""

    constructor() {}


    constructor(
        id: String,
        address: String,
        typeRoom: String,
        nameRoom: String,
        s_room: String,
        numberSleepRoom: String,
        convenient: String,
        introduce: String,
        imageRoom1: String,
        imageRoom2: String,
        status: String,
        price: String,
        uid: String
    ){
        this.id=id
        this.address=address
        this.typeRoom=typeRoom
        this.nameRoom=nameRoom
        this.s_room=s_room
        this.numberSleepRoom=numberSleepRoom
        this.convenient=convenient
        this.introduce=introduce
        this.imageRoom1=imageRoom1
        this.imageRoom2=imageRoom2
        this.status=status
        this.price=price
        this.uid=uid
    }
}


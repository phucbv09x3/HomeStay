package com.kujira.homestay.data.model.response

data class AddRoomModel(
    var id:String,
    var address: String,
    var typeRoom: String,
    var nameRoom: String,
    var s_room: String,
    var numberSleepRoom: String,
    var convenient: String,
    var introduce: String,
    var imageRoom1: String,
    var imageRoom2: String,
    var status: String,
    var price: String,
    var uid : String
)
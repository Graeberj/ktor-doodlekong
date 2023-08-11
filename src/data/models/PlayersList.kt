package com.plcoding.data.models

import com.plcoding.util.Constants


data class PlayersList(
    val players: List<PlayerData>
): BaseModel(Constants.TYPE_PLAYERS_LIST)

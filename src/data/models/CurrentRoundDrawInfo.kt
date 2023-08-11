package com.plcoding.data.models

import com.plcoding.util.Constants.TYPE_CURRENT_ROUND_DRAW_INFO

data class RoundDrawInfo(
    val data: List<String>
): BaseModel(TYPE_CURRENT_ROUND_DRAW_INFO)

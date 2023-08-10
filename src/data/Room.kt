package com.plcoding.data

import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.isActive

class Room(
    val name: String,
    val maxPlayers: Int,
    var players: List<Player> = listOf()
) {

    private var phaseChangedListener: ((Phase) -> Unit)? = null
    var phase = Phase.WAITING_FOR_PLAYERS
        set(value){
            synchronized(field){
                field = value
                phaseChangedListener?.let { change ->
                    change(value)
                }
            }
        }

    private fun setPhaseChangedListener(listener: (Phase) -> Unit){
        phaseChangedListener = listener
    }

    init {
        setPhaseChangedListener {phase ->
            when(phase) {
                Phase.WAITING_FOR_PLAYERS -> waitingForPlayers()
                Phase.WAITING_TO_START -> waitingToStart()
                Phase.NEW_ROUND -> newRound()
                Phase.GAME_RUNNING -> gameRunning()
                Phase.SHOW_WORD -> showWord()
            }
        }
    }
    suspend fun broadcast(message: String){
        players.forEach{player ->
            if (player.socket.isActive){
                player.socket.send(Frame.Text(message))
            }
        }
    }

    suspend fun broadcastToAllExept(message: String, clientId: String){
        players.forEach{player ->
            if (player.clientId != clientId && player.socket.isActive){
                player.socket.send(Frame.Text(message))
            }
        }
    }

    fun containsPlayer(username: String): Boolean{
        return players.find { it.username == username } != null
    }

    private fun waitingForPlayers(){

    }
    private fun waitingToStart(){

    }

    private fun newRound(){

    }
    private fun gameRunning(){

    }

    private fun showWord(){

    }
    enum class Phase {
        WAITING_FOR_PLAYERS,
        WAITING_TO_START,
        NEW_ROUND,
        GAME_RUNNING,
        SHOW_WORD
    }
}
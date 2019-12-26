package com.example.demo

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class SocketHandler() : TextWebSocketHandler() {

    val sessionList = arrayListOf<WebSocketSession>()

    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        session.sendMessage(TextMessage("Connected"))
        sessionList.add(session)
    }

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        sessionList.filter { it!=session }.forEach { it.sendMessage(message) }
    }

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        sessionList.remove(session)
        sessionList.forEach { it.sendMessage(TextMessage("DISCONNECT")) }
    }

}

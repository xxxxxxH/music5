package jboster.poshe.one.http

import android.app.Activity
import android.content.Context
import jboster.poshe.one.http.Connection


object HttpTools {
    fun with(context: Context): Connection {
        val connection = Connection(context)
        connection.setSingletonInstance(connection)
        return connection
    }

    fun with(activity: Activity): Connection {
        val connection = Connection(activity)
        connection.setSingletonInstance(connection)
        return connection
    }
}
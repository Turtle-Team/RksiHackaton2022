package com.turtleteam.myapp.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.turtleteam.myapp.data.model.event.Events
import com.turtleteam.myapp.data.repositories.EventRepository
import com.turtleteam.myapp.data.wrapper.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class EventsService : Service() {

    companion object {
        const val START_REQUEST = "start"
    }

    @Inject
    lateinit var repository: EventRepository

    private val binder by lazy { EventsBinder() }
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    var _events = MutableLiveData<Result<List<Events>>>(Result.Loading)

    override fun onBind(intent: Intent?): IBinder = binder


    override fun onCreate() {
        super.onCreate()
        scope.launch {
            _events.postValue(repository.getAllEvent())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        while (true) {
            var a = "null"
            scope.launch{ a = repository.getAllEvent().toString()}
            Log.e("jjjjjj",a)
            return START_STICKY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    inner class EventsBinder : Binder() {
        fun getService(): EventsService = this@EventsService
    }
}
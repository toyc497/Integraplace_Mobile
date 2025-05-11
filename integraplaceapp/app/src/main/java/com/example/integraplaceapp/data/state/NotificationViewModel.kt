package com.example.integraplaceapp.data.state

import androidx.lifecycle.ViewModel
import com.example.integraplaceapp.data.model.NotificationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationViewModel: ViewModel() {

    private val _notificationState = MutableStateFlow<List<NotificationEntity>>(emptyList())
    val notificationState = _notificationState.asStateFlow()

    fun setNotification(notification: List<NotificationEntity>) {
        _notificationState.value = notification
    }

    fun removeNotification(notification: NotificationEntity) {
        _notificationState.value = _notificationState.value.filter { it.id != notification.id }
    }

}
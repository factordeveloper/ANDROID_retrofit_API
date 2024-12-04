package com.factor.dev.wifi_ip_cyberalerts_retrofit_api

data class Alert(
    val type: String,
    val message: String,
    val timestamp: String? = null
)
package com.factor.dev.wifi_ip_cyberalerts_retrofit_api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call

interface ApiService {
    @POST("/alerts")
    fun postAlert(@Body alert: Alert): Call<Alert>

    @GET("/alerts")
    fun getAlerts(): Call<List<Alert>>
}
package com.example.integraplaceapp.data.network

import com.example.integraplaceapp.data.model.BPR1Entity
import com.example.integraplaceapp.data.model.BPR1EntityForm
import com.example.integraplaceapp.data.model.CepInfo
import com.example.integraplaceapp.data.model.ItemEntity
import com.example.integraplaceapp.data.model.ItemEntityForm
import com.example.integraplaceapp.data.model.LoginEntity
import com.example.integraplaceapp.data.model.NotificationEntity
import com.example.integraplaceapp.data.model.OPORResultsEntity
import com.example.integraplaceapp.data.model.OpportunityEntity
import com.example.integraplaceapp.data.model.OrderDetailForm
import com.example.integraplaceapp.data.model.OrderEntity
import com.example.integraplaceapp.data.model.SansaoListDAO
import com.example.integraplaceapp.data.model.UsersEntity
import com.example.integraplaceapp.data.model.WarehouseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("User/login")
    suspend fun login(@Body users: UsersEntity): Response<LoginEntity>

    @POST("User/save")
    suspend fun createUser(@Body users: UsersEntity): Response<UsersEntity>

    @GET("Opportunity/all")
    suspend fun getOpportunities(): Response<List<OpportunityEntity>>

    @PUT("Opportunity/update/participation")
    suspend fun setOportunityParticipation(@Body oportunity: OpportunityEntity): Response<OpportunityEntity>

    @GET("Opportunity/results")
    suspend fun getOportunityResults(): Response<OPORResultsEntity>

    @GET("Warehouse/all")
    suspend fun getWarehouses(): Response<List<WarehouseEntity>>

    @POST("Warehouse/create")
    suspend fun createWarehouse(@Body warehouse: WarehouseEntity): Response<WarehouseEntity>

    @POST("Item/create")
    suspend fun createItem(@Body item: ItemEntityForm): Response<ItemEntity>

    @GET("Item/all")
    suspend fun getItems(): Response<List<ItemEntity>>

    @GET("Order/all")
    suspend fun getOrders(): Response<List<OrderEntity>>

    @GET("Order/findbycode/{code}")
    suspend fun getOrderByCode(@Path("code") code: String): Response<OrderDetailForm>

    @POST("Order/create")
    suspend fun createOrder(@Body order: OrderEntity): Response<Void>

    @GET("Externalapi/cep/{cep}")
    suspend fun cepInfo(@Path("cep") cep: String): Response<CepInfo>

    @GET("BussinessPartner1/all")
    suspend fun getAllBPR1(): Response<List<BPR1Entity>>

    @POST("BussinessPartner1/create")
    suspend fun createBPR1(@Body bpr1: BPR1EntityForm): Response<BPR1Entity>

    @GET("Copetitor/consult/{cpfCnpj}")
    suspend fun getCompetitor(@Path("cpfCnpj") cpfCnpj: String): Response<SansaoListDAO>

    @GET("Notification/all")
    suspend fun getAllNotifications(): Response<List<NotificationEntity>>

    @DELETE("Notification/delete/{id}")
    suspend fun deleteNotification(@Path("id") id: Long)

}
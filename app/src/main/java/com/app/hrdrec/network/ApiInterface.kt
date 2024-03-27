package com.app.hrdrec.network


import com.app.hrdrec.admin.roles.DataRolesPayload
import com.app.hrdrec.admin.roles.UpdateRolesPayload
import com.app.hrdrec.admin.roles.get_all_roles_response.GetAllRolesResponse
import com.app.hrdrec.admin.users.AddUserPayload
import com.app.hrdrec.admin.users.UpdateUserPayload
import com.app.hrdrec.admin.users.employes_response.GetEmployeeByOrgResponse
import com.app.hrdrec.admin.users.user_models.GetAllUserResponse
import com.app.hrdrec.expenses.ExpenseResponse
import com.app.hrdrec.expenses.dataclasses.ExpenseReport
import com.app.hrdrec.home.AllModuleResponse
import com.app.hrdrec.leaves.response.AddLeave
import com.app.hrdrec.login.model.UserInfo
import com.app.hrdrec.login.model.LoginParams
import com.app.hrdrec.organization.clients.addclients.AddClientsParams
import com.app.hrdrec.organization.clients.addclients.UpdateClientsParams
import com.app.hrdrec.organization.clients.get_clients_response.GetAllClientsResponse
import com.app.hrdrec.organization.locations.addlocation.AddLocationParams
import com.app.hrdrec.organization.locations.get_location_response.GelAllLocationResponse
import com.app.hrdrec.organization.locations.GetCountryResponse
import com.app.hrdrec.organization.locations.addlocation.UpdateLocationParams
import com.app.hrdrec.organization.locations.state_response.GetStateResponse
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.leaves.response.AllLeaveResponse
import com.app.hrdrec.leaves.response.LeaveBalanceResponse
import com.app.hrdrec.leaves.response.LeaveTypeResponse
import com.app.hrdrec.manager.all_leave_submitted.GetLeaveManagerModel
import com.app.hrdrec.manager.response.all_submitted_response.GetSubmitedResponse
import com.app.hrdrec.organization.HolidayCalendars.dataclass.AllHolidayCalendarData
import com.app.hrdrec.organization.HolidayCalendars.dataclass.GetAllHolidayCalendarResponse
import com.app.hrdrec.organization.expensecategories.dataclass.GetAllExpenseCategoriesResponse
import com.app.hrdrec.organization.leavetypes.AddLeavetypePayload
import com.app.hrdrec.organization.leavetypes.UpdateLeavetypePayload
import com.app.hrdrec.organization.leavetypes.leavetype_models.GetAllLeavetypeResponse
import com.app.hrdrec.organization.leavetypes.locations_response.GetLocationByOrgResponse
import com.app.hrdrec.organization.organizationprofile.addorganizationprofile.AddOrganizationProfileParams
import com.app.hrdrec.organization.organizationprofile.addorganizationprofile.UpdateOrganizationProfileParams
import com.app.hrdrec.organization.organizationprofile.get_organizationprofile_response.GetAllOrganizationProfileResponse
import com.app.hrdrec.projects.AddProjectPayload
import com.app.hrdrec.projects.AddProjectResourcesPayload
import com.app.hrdrec.projects.GetAllProjectResponse
import com.app.hrdrec.projects.UpdateProjectPayload
import com.app.hrdrec.projects.UpdateProjectResourcesPayload
import com.app.hrdrec.projects.clients_response.GetClientByOrgResponse
import com.app.hrdrec.projects.manager_response.GetManagerByOrgResponse
import com.app.hrdrec.timesheet.request_payload.SavedDataResponse
import com.app.hrdrec.timesheet.response.TimeScheduleResponse
import com.app.hrdrec.timesheet.response.project_response.ProjectDataResposne
import com.app.hrdrec.utils.CommonResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {


    @POST("users/login")
    suspend fun loginUser(@Body loginRequest: LoginParams): Response<UserInfo>

    @GET("modules/getAll")
    suspend fun gelAllModule(): Response<AllModuleResponse>

    @GET("locations/getAll/{id}")
    suspend fun getAllLocations(@Path("id") id: Int): Response<GelAllLocationResponse>

    @POST("locations/save")
    suspend fun saveLocation(@Body loginRequest: AddLocationParams): Response<CommonResponse>

    @POST("locations/save")
    suspend fun updateLocation(@Body loginRequest: UpdateLocationParams): Response<CommonResponse>

    @DELETE("locations/deleteById/{id}")
    suspend fun deleteLocation(@Path("id") id: Int): Response<CommonResponse>

    @GET("roles/getAll/{id}")
    suspend fun getAllRoles(@Path("id") id: Int): Response<GetAllRolesResponse>

    @GET("users/getAll/{id}")
    suspend fun getAllUsers(@Path("id") id: Int): Response<GetAllUserResponse>

    @GET("countries/getAll")
    suspend fun getCountries(): Response<GetCountryResponse>

    @GET("countries/getById/{id}")
    suspend fun getStates(@Path("id") id: Int): Response<GetStateResponse>


    @POST("roles/save")
    suspend fun addRoles(@Body body: DataRolesPayload): Response<CommonResponse>

    @POST("roles/save")
    suspend fun updateRoles(@Body body: UpdateRolesPayload): Response<CommonResponse>


    @GET("employees/getAll/{id}")
    suspend fun getEmployeeByOrg(@Path("id") id: Int): Response<GetEmployeeByOrgResponse>

    @POST("users/save")
    suspend fun addUser(@Body body: AddUserPayload): Response<CommonResponse>

    @POST("users/save")
    suspend fun updateUser(@Body body: UpdateUserPayload): Response<CommonResponse>

    @DELETE("users/deleteById/{id}")
    suspend fun deleteusers(@Path("id") id: Int): Response<CommonResponse>


    @GET("projectclients/getAll/{id}")
    suspend fun getAllClients(@Path("id") id: Int): Response<GetAllClientsResponse>

    @POST("projectclients/save")
    suspend fun saveClients(@Body loginRequest: AddClientsParams): Response<CommonResponse>

    @POST("projectclients/save")
    suspend fun updateClients(@Body loginRequest: UpdateClientsParams): Response<CommonResponse>

    @DELETE("projectclients/deleteById/{id}")
    suspend fun deleteClients(@Path("id") id: Int): Response<CommonResponse>

    @POST("timesheets/save")
    suspend fun saveTimeSheet(@Body addTimeModal: AddTimeModal): Response<CommonResponse>

    @GET("timesheets/getAll/{id}")
    suspend fun getTimesheetscheduler(@Path("id") id: Int): Response<TimeScheduleResponse>

    @GET("timesheets/getAll/{id}")
    suspend fun getTimesheetschedulerDates(
        @Path("id") id: Int, @QueryMap params: HashMap<String, String>
    ): Response<TimeScheduleResponse>

    @GET("leaves/getAll/{id}")
    suspend fun getAllLeaves(@Path("id") id: Int): Response<AllLeaveResponse>

    @GET("leaves/getAll/{id}")
    suspend fun getAllLeavesDates(
        @Path("id") id: Int, @QueryMap params: HashMap<String, String>
    ): Response<AllLeaveResponse>

    @GET("timesheets/getById/{id}")
    suspend fun getSavedTimesheetById(@Path("id") id: Int): Response<SavedDataResponse>


    @GET("projects/getAllProjects/{id}")
    suspend fun getprojects(@Path("id") id: Int): Response<ProjectDataResposne>


    @GET("leavetype/getAllByLocationId/{id}")
    suspend fun getLeaveTypes(@Path("id") id: Int): Response<LeaveTypeResponse>

    @GET("leavebalances/getAll/{id}/{year}")
    suspend fun getLeaveBalance(
        @Path("id") id: Int, @Path("year") year: Int
    ): Response<LeaveBalanceResponse>

    @POST("leaves/save")
    suspend fun applyLeave(@Body addTimeModal: AddLeave): Response<CommonResponse>


    @GET("timesheets/getSubmittedByManager/{id}")
    suspend fun getSubmittedByManager(@Path("id") id: Int): Response<GetSubmitedResponse>

    @GET("timesheets/getSubmittedByManager/{id}")
    suspend fun getSubmittedByManagerByDates(
        @Path("id") id: Int, @QueryMap params: HashMap<String, String>
    ): Response<GetSubmitedResponse>

    @FormUrlEncoded
    @POST("timesheets/approve/{timesheetId}/{projectId}")
    suspend fun approveRecord(
        @Path("timesheetId") timesheetId: Int,
        @Path("projectId") projectId: Int,
        @Field("remarks") remarks: String
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("timesheets/reject/{timesheetId}/{projectId}")
    suspend fun rejectRecord(
        @Path("timesheetId") timesheetId: Int,
        @Path("projectId") projectId: Int,
        @Field("remarks") remarks: String
    ): Response<CommonResponse>


    @GET("leaves/getAppliedLeavesByManagerId/{id}")
    suspend fun getAllLeaveManager(@Path("id") id: Int): Response<GetLeaveManagerModel>


    @GET("leaves/getAppliedLeavesByManagerId/{id}")
    suspend fun getAllLeaveManagerDates(
        @Path("id") id: Int, @QueryMap params: HashMap<String, String>
    ): Response<GetLeaveManagerModel>

    @FormUrlEncoded
    @POST("leaves/approve/{id}")
    suspend fun approveLeave(
        @Path("id") id: Int, @Field("remarks") remarks: String
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("leaves/reject/{id}")
    suspend fun rejectLeave(
        @Path("id") timesheetId: Int, @Field("remarks") remarks: String
    ): Response<CommonResponse>


    @GET("projects/getAll/{id}")
    suspend fun getAllProjects(@Path("id") id: Int): Response<GetAllProjectResponse>

    @GET("projects/getAll/{id}")
    suspend fun getAllProjectResource(@Path("id") id: Int): Response<GetAllProjectResponse>

    @POST("projects/save")
    suspend fun addproject(@Body loginRequest: AddProjectPayload): Response<CommonResponse>

    @POST("projects/save")
    suspend fun updateproject(@Body loginRequest: UpdateProjectPayload): Response<CommonResponse>

    @DELETE("projects/deleteById/{id}")
    suspend fun deleteprojects(@Path("id") id: Int): Response<CommonResponse>


    @GET("projectclients/getAll/{id}")
    suspend fun getClientsByOrg1(@Path("id") id: Int): Response<GetClientByOrgResponse>


    @GET("employees/getAll/{id}")
    suspend fun getemployeeByOrg1(@Path("id") id: Int): Response<com.app.hrdrec.projects.employee_response.GetEmployeeByOrgResponse>

    @GET("employees/getAll/{id}")
    suspend fun getManagerByOrg(@Path("id") id: Int): Response<GetManagerByOrgResponse>

    @POST("projects/save")
    suspend fun addProjectResource(@Body loginRequest: AddProjectResourcesPayload): Response<CommonResponse>

    @POST("projects/save")
    suspend fun updateProjectResource(@Body loginRequest: UpdateProjectResourcesPayload): Response<CommonResponse>

    //    http://106.51.52.207:8009/hrdome/holidays/getHolidayCalendar/59
//    http://106.51.52.207:8009/hrdome/holidays/save
    //    http://106.51.52.207:8009/hrdome/holidays/getAll/110/2025 -  to know the year data
//    http://106.51.52.207:8009/hrdome/holidays/getById/1438 - after selecting year and the data inside
//    http://106.51.52.207:8009/hrdome/holidays/deleteByHolidayId/1438
    @GET("holidays/getHolidayCalendar/{id}")
    suspend fun getAllHolidayCalendars(@Path("id") id: Int): Response<GetAllHolidayCalendarResponse>

    @DELETE("holidays/deleteByHolidayId/{id}")
    suspend fun deleteHolidayCalendar(@Path("id") id: Int): Response<CommonResponse>

    @GET("holidays/getById/{id}")
    suspend fun getHoliday(@Path("id") id: Int): Response<CommonResponse>

    //    @POST("holidays/save")
//    suspend fun saveHoliday(@Body body: UpdateHoliday): Response<CommonResponse>
    @GET("holidays/getAll/{locationId}/{year}")
    suspend fun getHolidaysByLocation(
        @Path("locationId") locationId: Int, @Path("year") year: Int
    ): Response<GetAllHolidayCalendarResponse>


    //Expense Categories
//    http://106.51.52.207:8009/hrdome/expensecategories/getAll/59
//    http://106.51.52.207:8009/hrdome/expensecategories/getById/181
//    http://106.51.52.207:8009/hrdome/expensecategories/save

    @GET("expensecategories/getAll/{id}")
    suspend fun getAllExpenseCategories(@Path("id") id: Int): Response<GetAllExpenseCategoriesResponse>

    @GET("leavetype/getAllByOrganizationId/{id}")
    suspend fun getAllLeavetypes(@Path("id") id: Int): Response<GetAllLeavetypeResponse>

    @GET("locations/getAll/{id}")
    suspend fun getLocationByOrg1(@Path("id") id: Int): Response<GetLocationByOrgResponse>

    @POST("leavetype/save")
    suspend fun addleavetype(@Body loginRequest: AddLeavetypePayload): Response<CommonResponse>

    @POST("leavetype/save")
    suspend fun updateleavetype(@Body loginRequest: UpdateLeavetypePayload): Response<CommonResponse>

    @DELETE("leavetype/deleteById/{id}")
    suspend fun deleteleavetypes(@Path("id") id: Int): Response<CommonResponse>

    //Organization profile

    @GET("organizations/getById/{id}")
    suspend fun getAllOrganizationProfile(@Path("id") id: Int): Response<GetAllOrganizationProfileResponse>

    @POST("organizations/save")
    suspend fun saveOrganizationProfile(@Body loginRequest: AddOrganizationProfileParams): Response<CommonResponse>

    @POST("organizations/save")
    suspend fun updateOrganizationProfile(@Body loginRequest: UpdateOrganizationProfileParams): Response<CommonResponse>

    @DELETE("organizations/deleteById/{id}")
    suspend fun deleteOrganizationProfile(@Path("id") id: Int): Response<CommonResponse>




    //ExpenseSheet

    @Multipart
    @POST("expensesheets/save")
    suspend fun saveExpanseSheet(
        @Part("expenseSheet") requestBody: RequestBody,
        // @Part file: MultipartBody.Part
    ): Response<CommonResponse>

    @GET("currencies/getAll")
    suspend fun getCurrency(): Response<ExpenseResponse>

    @GET("expensesheets/getById/{id}")
    suspend fun expenseSheetGetById(@Path("id") id: Int): Response<ExpenseReport>

    @DELETE("expensesheets/deleteById/{id}")
    suspend fun deleteExpense(@Path("id") id: Int): Response<CommonResponse>

    @GET("expensesheets/getAllSheetsByDates/{username}")
    suspend fun getReimberseList(@Path("username") id: String): Response<ExpenseResponse>

    @GET("expensesheets/getAllSheetsByDates/{username}")
    suspend fun getReimberseListFromDate(@Path("username") id: String,@QueryMap params: HashMap<String,String>): Response<ExpenseResponse>

    @GET("expensecategories/getTypesById/{id}")
    suspend fun getExpenseType(@Path("id") id: Int): Response<ExpenseResponse>

    @GET("expensecategories/getAll/{id}")
    suspend fun getExpenseCategory(@Path("id") id: Int): Response<ExpenseResponse>

    @GET("expensesheets/getAllSheetsByDatesEmployeeId/{id}")
    suspend fun getExpenseList(@Path("id") id: Int): Response<ExpenseResponse>

    @GET("expensesheets/getAllSheetsByDatesEmployeeId/{id}")
    suspend fun getExpenseListFromDates(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<ExpenseResponse>


    @GET("expensesheets/getAllByManager/{id}")
    suspend fun getAuthorizeExpenseList(@Path("id") id: Int): Response<ExpenseResponse>

    @GET("expensesheets/getAllByManager/{id}")
    suspend fun getAuthorizeExpenseListByDate(@Path("id") id: Int,@QueryMap params: HashMap<String,String>): Response<ExpenseResponse>
}
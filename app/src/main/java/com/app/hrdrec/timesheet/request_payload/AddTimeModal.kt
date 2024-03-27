package com.app.hrdrec.timesheet.request_payload

import com.google.gson.annotations.SerializedName

data class AddTimeModal(@SerializedName("weekendDate"      ) var weekendDate      : String?                     = null,
                        @SerializedName("totalHours"       ) var totalHours       : Double?                        = null,
         /*               @SerializedName("modifiedBy"       ) var modifiedBy       : Int?                        = null,
                        @SerializedName("createdBy"        ) var createdBy        : Int?                        = null,*/
                        @SerializedName("status"           ) var status           : String?                     = null,
                        @SerializedName("employeeId"       ) var employeeId       : Int?                        = null,
                        @SerializedName("organizationId"   ) var organizationId   : Int?                        = null,
                        @SerializedName("locationId"       ) var locationId       : Int?                        = null,
                        @SerializedName("timesheetRowDTOs" ) var timesheetRowDTOs : ArrayList<TimesheetRowDTOs> = arrayListOf(),
                        @SerializedName("id"               ) var id               : Int?    = null


)

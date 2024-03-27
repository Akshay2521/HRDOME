package com.app.hrdrec.leaves.response

import java.util.Date

data class AddLeave(
 var fromDate       : String? = null,
   var toDate         : String? = null,
     var numberofDays   : Int?    = null,
    var reason         : String? = null,
   var status         : String? = null,
    var remarks        : String? = null,
    var createdBy      : Int?    = null,
    var modifiedBy     : Int?    = null,
    var employeeId     : Int?    = null,
     var leavetypeId    : Int?    = null,
   var organizationId : Int?    = null,
   var fromSession    : String? = null,
    var toSession      : String? = null,
    var employeeName      : String? = null,
   var leavetypeName        : String? = null,
   var organizationName      : String? = null,
 var id      : Int?    = null,
 var submittedDate:   String? = null,

)

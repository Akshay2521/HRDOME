package com.app.hrdrec.organization.locations.addlocation


data class UpdateLocationParams(

    var name: String? = null,
    var address1: String? = null,
    var address2: String? = null,
    var city: String? = null,
    var zipCode: String? = null,
    var headOffice: Boolean? = true,
    var deleted: Boolean? = false,
    var organizationId: Int =1,
    var countryId: String? = "0",
    var stateId: String? =  "0",
    var id:Int
    )
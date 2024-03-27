package com.app.hrdrec.organization.clients.addclients

class UpdateClientsParams(


    val deleted: Boolean? = false,
    val email: String? = null,
    val locationName: String? = null,
    val name: String? = null,
    val organizationId: Int = 0,
    var id: Int
)
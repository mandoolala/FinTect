package com.example.fintectapp

data class UserDTO(var email: String? = null, var phone: String? = null, var requests: Int = 0)

data class Content( var name: String? = null,
                    var timestamp: Long? = null)

package com.example.usuario.registro

import java.util.*

data class Datos(var id: String = "", var nombre: String = "", var contraseña: String = "") {

    val miHashMapDatos = HashMap<String, Any>()


    fun crearHashMapDatos() {
        miHashMapDatos.put("id", id)
        miHashMapDatos.put("nombre", nombre)
        miHashMapDatos.put("contraseña", contraseña)
    }
}
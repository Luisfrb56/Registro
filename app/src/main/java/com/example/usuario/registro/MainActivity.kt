package com.example.usuario.registro


import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.iid.FirebaseInstanceId
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {




    private var database: DatabaseReference? = null

    lateinit var misDatos : Datos
    private var FCMToken: String? = null

    val miHashMapChild = HashMap<String, Any>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        FCMToken = FirebaseInstanceId.getInstance().token
        database = FirebaseDatabase.getInstance().getReference("/registro")


            bEnviar.setOnClickListener {
                if(trId.text.toString()!=""){
                    if (trNombre.text.toString()!="") {
                        if (trContraseña.text.toString() != "") {
                            misDatos = Datos("", "", "")
                            misDatos.id = trId.text.toString()
                            misDatos.nombre = trNombre.text.toString()
                            misDatos.contraseña = trContraseña.text.toString()



                            misDatos.crearHashMapDatos()

                            miHashMapChild.put(FCMToken.toString(), misDatos.miHashMapDatos)
                            // actualizamos el child
                            database!!.updateChildren(miHashMapChild)
                        }else {
                            toast("Contraseña: Campo Obligatorio")
                        }

                    }else{
                        toast("Nombre: Campo Obligatorio")
                    }
                } else{
                    toast("ID: Campo Obligatorio")
                }

            }
        bBorrar.setOnClickListener{


            database!!.child(FCMToken.toString()).removeValue()


        }


        initListener()
        }


private fun initListener() {

    val childEventListener = object : ChildEventListener {

        override fun onChildRemoved(p0: DataSnapshot) {
            Log.d("Registro", "Datos borrados: "+misDatos.id)

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {


            var misDatosCambiados =Datos( "","",""  )

            misDatosCambiados = p0.getValue(Datos::class.java)!!

            Log.d("Registro", "Datos cambiados: " + misDatosCambiados.nombre + " " + misDatosCambiados.contraseña)
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            Log.d("Registro", "Datos movidos")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            // onChildAdded() capturamos la key
            Log.d("Registro", "Datos añadidos: " + p0.key)

        }

        override fun onCancelled(p0: DatabaseError) {
            Log.d("Registro", "Error cancelacion")
        }
    }
    // attach el evenListener a la basededatos
    database!!.addChildEventListener(childEventListener)
}
}

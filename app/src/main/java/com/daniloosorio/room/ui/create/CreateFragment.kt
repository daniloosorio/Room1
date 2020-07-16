
package com.daniloosorio.room.ui.create

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daniloosorio.room.R
import com.daniloosorio.room.model.remote.DeudorRemote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.et_nombre
import kotlinx.android.synthetic.main.fragment_create.et_telefono
import java.io.ByteArrayOutputStream

class CreateFragment : Fragment() {

private  val REQUEST_IMAGE_CAPTURE =1234
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsBienvenida()
    iv_foto.setOnClickListener{
        dispatchTakePictureIntent()
    }
        /// guardar en ROOM
        bt_entrar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty()||et_telefono.text.isNullOrEmpty()||et_cantidad.text.isNullOrEmpty()){
                Toast.makeText(context, "Debe llenar todoos los campos", Toast.LENGTH_SHORT).show()
            }else {
                val nombre = et_nombre.text.toString()
                val telefono =et_telefono.text.toString()
                val cantidad = et_cantidad.text.toString().toLong()
                guardarEnFirebase(nombre, telefono, cantidad)
                cleanEditText()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{takePictureIntent->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also{
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
        val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_foto.setImageBitmap(imageBitmap)
        }
    }

    private fun smsBienvenida() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth.currentUser
        val correo = user?.email
        Toast.makeText(context, "Bienvenido $correo", Toast.LENGTH_SHORT).show()
    }

    private fun guardarEnFirebase(nombre: String, telefono: String, cantidad: Long) {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef :DatabaseReference =database.getReference("deudores")
        val id :String? = myRef.push().key

        val mStorage =FirebaseStorage.getInstance()
        val photoRef =mStorage.reference.child(id!!)
        var urlPhoto =""
        iv_foto.isDrawingCacheEnabled =true
        iv_foto.buildDrawingCache()
        val bitmap=(iv_foto.drawable as BitmapDrawable).bitmap
        val baos =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        var uploadTask =photoRef.putBytes(data)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                urlPhoto = task.result.toString()

                val deudor =DeudorRemote(
                    id,
                    nombre,
                    telefono,
                    cantidad,
                    urlPhoto
                )
                myRef.child(id!!).setValue(deudor)
            } else {
                // Handle failures
                // ...
            }
        }

    }

    private fun cleanEditText() {
        et_nombre.setText("")
        et_telefono.setText("")
        et_cantidad.setText("")
        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
    }
}

/*GUARDAR EN ROOM
*
* bt_entrar.setOnClickListener {
            if(et_nombre.text.isNullOrEmpty() || et_telefono.text.isNullOrEmpty() || et_cantidad.text.isNullOrEmpty()){
                Toast.makeText(context, "Deber llenar todos los datos", Toast.LENGTH_SHORT).show()
            }else {
                val nombre = et_nombre.text.toString()
                val telefono = et_telefono.text.toString()
                val cantidad = et_cantidad.text.toString().toLong()
                val deudor = Deudor(NULL, nombre, telefono, cantidad)
                val deudorDAO: DeudorDAO = SesionROOM.database.deudorDAO()
                deudorDAO.crearDeudor(deudor)
                et_nombre.setText("")
                et_telefono.setText("")
                et_cantidad.setText("")
            }
        }*/
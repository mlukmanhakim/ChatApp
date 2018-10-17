package lukmancyb.com.chatapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import lukmancyb.com.chatapp.R





class RegisterActivity : AppCompatActivity() {

    private  var mAuth = FirebaseAuth.getInstance()
    var REQUEST_CODE = 233

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register.setOnClickListener {
           registrasi()

        }
        textAlready.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_select_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("Regiister Activity", "Photo was selected")
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            btn_select_image.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun registrasi() {
        val email = email_edit_register.text.toString()
        val password = passwor_edit_register.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Silahkan masukan email dan password", Toast.LENGTH_LONG).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d("main", "Sukses dengan uid : ${it.result?.user?.uid}")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to create user : ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d("Main","Failed to create user : ${it.message}")
                }
    }

}

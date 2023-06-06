package com.stackmobile.strengthhub.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stackmobile.strengthhub.R
import com.stackmobile.strengthhub.databinding.ActivityTelaPrincipalBinding
import com.stackmobile.strengthhub.view.formlogin.FormLogin

class TelaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityTelaPrincipalBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_tela_principal)
        setContentView(binding.root)

        binding.btdeslocar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }
        binding.btdeslocar.setOnClickListener {
            val usuariosMap = hashMapOf(
                "nome" to "João",
                "sobrenome" to "Barbosa",
                "idade" to 28
            )
            db.collection("Usuários").document("João")
                .set(usuariosMap).addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar os dados do usuário!")
                }.addOnFailureListener {


                }
        }
       binding.btLerdados.setOnClickListener {
           db.collection("Usuários").document("João")
               .addSnapshotListener { documento, error ->
                   if (documento != null){
                       val idade = documento.getLong("idade")
                       binding.textResultadoNome.text = documento.getString("nome")
                       binding.textResultadoSobrenome.text = documento.getString("sobrenome")
                       binding.textResultadoIdade.text = idade.toString()
                   }
               }
       }
    }
}
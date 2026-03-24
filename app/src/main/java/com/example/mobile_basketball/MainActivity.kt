package com.example.mobile_basketball

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var pontuacaoTimeA: Int = 0;
    private var pontuacaoTimeB: Int = 0;

    private lateinit var pTimeA: TextView
    private lateinit var pTimeB: TextView
    private var corPadraoTimeA: Int = Color.WHITE
    private var corPadraoTimeB: Int = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_cadastro)

        val editNomeTimeA: EditText = findViewById(R.id.editNomeTimeA)
        val editNomeTimeB: EditText = findViewById(R.id.editNomeTimeB)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)

        btnIniciar.setOnClickListener {
            val nomeTimeA = editNomeTimeA.text.toString().trim()
            val nomeTimeB = editNomeTimeB.text.toString().trim()

            if (nomeTimeA.isEmpty() || nomeTimeB.isEmpty()) {
                Toast.makeText(this, "Preencha o nome dos dois times!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            iniciarPlacar(nomeTimeA, nomeTimeB)
        }
    }

    fun iniciarPlacar(nomeTimeA: String, nomeTimeB: String) {
        setContentView(R.layout.layout_main)

        pTimeA = findViewById(R.id.placarTimeA)
        pTimeB = findViewById(R.id.placarTimeB)
        corPadraoTimeA = pTimeA.currentTextColor
        corPadraoTimeB = pTimeB.currentTextColor

        val txtTimeA: TextView = findViewById(R.id.timeA)
        val txtTimeB: TextView = findViewById(R.id.timeB)
        txtTimeA.text = nomeTimeA
        txtTimeB.text = nomeTimeB

        val bTresPontosTimeA: Button = findViewById(R.id.tresPontosA)
        val bDoisPontosTimeA: Button = findViewById(R.id.doisPontosA)
        val bTLivreTimeA: Button = findViewById(R.id.tiroLivreA)
        val bTresPontosTimeB: Button = findViewById(R.id.tresPontosB)
        val bDoisPontosTimeB: Button = findViewById(R.id.doisPontosB)
        val bTLivreTimeB: Button = findViewById(R.id.tiroLivreB)
        val bReiniciar: Button = findViewById(R.id.reiniciarPartida)


        bTresPontosTimeA.setOnClickListener { adicionarPontos(3, "A") }

        bDoisPontosTimeA.setOnClickListener { adicionarPontos(2, "A") }

        bTLivreTimeA.setOnClickListener { adicionarPontos(1, "A") }

        bTresPontosTimeB.setOnClickListener { adicionarPontos(3, "B") }

        bDoisPontosTimeB.setOnClickListener { adicionarPontos(2, "B") }

        bTLivreTimeB.setOnClickListener { adicionarPontos(1, "B") }

        bReiniciar.setOnClickListener { confirmarReinicio() }
    }

    fun adicionarPontos(pontos: Int, time: String) {
        if(time == "A"){
            pontuacaoTimeA += pontos
        }else {
            pontuacaoTimeB += pontos

        }
        atualizaPlacar(time)
    }

    fun atualizaPlacar(time: String){
        if(time == "A"){
            pTimeA.setText(pontuacaoTimeA.toString())
            pTimeA.setTextColor(Color.parseColor("#d60000"))

            Handler(Looper.getMainLooper()).postDelayed({
                pTimeA.setTextColor(corPadraoTimeA)
            }, 600)
        }else {
            pTimeB.setText(pontuacaoTimeB.toString())
            pTimeB.setTextColor(Color.parseColor("#6200EE"))

            Handler(Looper.getMainLooper()).postDelayed({
                pTimeB.setTextColor(corPadraoTimeB)
            }, 600)
        }
    }

    fun confirmarReinicio() {
        AlertDialog.Builder(this)
            .setTitle("Reiniciar Partida")
            .setMessage("Tem certeza que deseja reiniciar o placar?")
            .setPositiveButton("Sim") { _, _ ->
                reiniciarPartida()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    fun reiniciarPartida() {
        pontuacaoTimeA = 0
        pTimeA.setText(pontuacaoTimeA.toString())
        pontuacaoTimeB = 0
        pTimeB.setText(pontuacaoTimeB.toString())
        Toast.makeText(this, "Placar reiniciado", Toast.LENGTH_SHORT).show()
    }
}

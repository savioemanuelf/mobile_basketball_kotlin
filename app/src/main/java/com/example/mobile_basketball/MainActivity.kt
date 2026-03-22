package com.example.mobile_basketball

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var pontuacaoTimeA: Int = 0;
    private var pontuacaoTimeB: Int = 0;

    private lateinit var pTimeA: TextView
    private lateinit var pTimeB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        pTimeA = findViewById(R.id.placarTimeA)
        pTimeB = findViewById(R.id.placarTimeB)

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

        bReiniciar.setOnClickListener { reiniciarPartida() }


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
                pTimeA.setTextColor(getColor(android.R.color.white))
            }, 600)
        }else {
            pTimeB.setText(pontuacaoTimeB.toString())
            pTimeB.setTextColor(Color.parseColor("#6200EE"))

            Handler(Looper.getMainLooper()).postDelayed({
                pTimeB.setTextColor(getColor(android.R.color.white))
            }, 600)
        }
    }

    fun reiniciarPartida() {
        pontuacaoTimeA = 0
        pTimeA.setText(pontuacaoTimeA.toString())
        pontuacaoTimeB = 0
        pTimeB.setText(pontuacaoTimeB.toString())
        Toast.makeText(this,"Placar reiniciado",Toast.LENGTH_SHORT).show()

    }
}

package com.example.mobile_basketball

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var pontuacaoTimeA: Int = 0
    private var pontuacaoTimeB: Int = 0

    private lateinit var pTimeA: TextView
    private lateinit var pTimeB: TextView
    private var corPadraoTimeA: Int = Color.WHITE
    private var corPadraoTimeB: Int = Color.WHITE

    private var periodoAtual: Int = 1
    private val totalPeriodos: Int = 4
    private val duracaoPeriodoMs: Long = 10 * 60 * 1000
    private var tempoRestanteMs: Long = duracaoPeriodoMs
    private var timerRodando: Boolean = false
    private var cronometro: CountDownTimer? = null

    private lateinit var txtPeriodo: TextView
    private lateinit var txtCronometro: TextView
    private lateinit var btnIniciarPausar: Button
    private lateinit var btnProximoPeriodo: Button

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

        txtPeriodo = findViewById(R.id.txtPeriodo)
        txtCronometro = findViewById(R.id.txtCronometro)
        btnIniciarPausar = findViewById(R.id.btnIniciarPausar)
        btnProximoPeriodo = findViewById(R.id.btnProximoPeriodo)

        atualizarExibicaoPeriodo()
        atualizarExibicaoCronometro()

        btnIniciarPausar.setOnClickListener {
            if (timerRodando) pausarTimer() else iniciarTimer()
        }

        btnProximoPeriodo.setOnClickListener { proximoPeriodo() }

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

    fun iniciarTimer() {
        cronometro = object : CountDownTimer(tempoRestanteMs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempoRestanteMs = millisUntilFinished
                atualizarExibicaoCronometro()
            }

            override fun onFinish() {
                tempoRestanteMs = 0
                atualizarExibicaoCronometro()
                timerRodando = false
                btnIniciarPausar.text = "▶ INICIAR"
                if (periodoAtual < totalPeriodos) {
                    Toast.makeText(this@MainActivity, "Fim do ${txtPeriodo.text}!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Fim de jogo!", Toast.LENGTH_LONG).show()
                    btnProximoPeriodo.isEnabled = false
                }
            }
        }.start()
        timerRodando = true
        btnIniciarPausar.text = "⏸ PAUSAR"
    }

    fun pausarTimer() {
        cronometro?.cancel()
        timerRodando = false
        btnIniciarPausar.text = "▶ CONTINUAR"
    }

    fun proximoPeriodo() {
        if (periodoAtual >= totalPeriodos) {
            Toast.makeText(this, "Já é o último período!", Toast.LENGTH_SHORT).show()
            return
        }
        cronometro?.cancel()
        timerRodando = false
        periodoAtual++
        tempoRestanteMs = duracaoPeriodoMs
        atualizarExibicaoCronometro()
        atualizarExibicaoPeriodo()
        btnIniciarPausar.text = "▶ INICIAR"
        if (periodoAtual == totalPeriodos) {
            btnProximoPeriodo.isEnabled = false
        }
    }

    fun atualizarExibicaoPeriodo() {
        val ordinal = when (periodoAtual) {
            1 -> "1º"
            2 -> "2º"
            3 -> "3º"
            else -> "4º"
        }
        txtPeriodo.text = "$ordinal PERÍODO"
    }

    fun atualizarExibicaoCronometro() {
        val minutos = (tempoRestanteMs / 1000) / 60
        val segundos = (tempoRestanteMs / 1000) % 60
        txtCronometro.text = String.format("%02d:%02d", minutos, segundos)
    }

    fun adicionarPontos(pontos: Int, time: String) {
        if (time == "A") {
            pontuacaoTimeA += pontos
        } else {
            pontuacaoTimeB += pontos
        }
        atualizaPlacar(time)
    }

    fun atualizaPlacar(time: String) {
        if (time == "A") {
            pTimeA.setText(pontuacaoTimeA.toString())
            pTimeA.setTextColor(Color.parseColor("#d60000"))

            Handler(Looper.getMainLooper()).postDelayed({
                pTimeA.setTextColor(corPadraoTimeA)
            }, 600)
        } else {
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
        pTimeA.setText("0")
        pontuacaoTimeB = 0
        pTimeB.setText("0")

        cronometro?.cancel()
        timerRodando = false
        periodoAtual = 1
        tempoRestanteMs = duracaoPeriodoMs
        atualizarExibicaoCronometro()
        atualizarExibicaoPeriodo()
        btnIniciarPausar.text = "▶ INICIAR"
        btnProximoPeriodo.isEnabled = true

        Toast.makeText(this, "Placar reiniciado", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cronometro?.cancel()
    }
}

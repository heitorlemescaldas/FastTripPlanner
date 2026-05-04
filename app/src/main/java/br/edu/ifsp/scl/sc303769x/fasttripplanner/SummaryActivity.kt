package br.edu.ifsp.scl.sc303769x.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc303769x.fasttripplanner.ui.theme.FastTripPlannerTheme

class SummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Resgatando o pacotão de dados
        val destino = intent.getStringExtra(TripExtras.DESTINO) ?: ""
        val dias = intent.getIntExtra(TripExtras.DIAS, 0)
        val orcamento = intent.getDoubleExtra(TripExtras.ORCAMENTO, 0.0)
        val hospedagem = intent.getStringExtra(TripExtras.HOSPEDAGEM) ?: "Econômica"
        val transporte = intent.getBooleanExtra(TripExtras.TRANSPORTE, false)
        val alimentacao = intent.getBooleanExtra(TripExtras.ALIMENTACAO, false)
        val passeios = intent.getBooleanExtra(TripExtras.PASSEIOS, false)

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tela3ResumoViagem(
                        destino = destino,
                        dias = dias,
                        orcamento = orcamento,
                        hospedagem = hospedagem,
                        transporte = transporte,
                        alimentacao = alimentacao,
                        passeios = passeios,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Tela3ResumoViagem(
    destino: String, dias: Int, orcamento: Double, hospedagem: String,
    transporte: Boolean, alimentacao: Boolean, passeios: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Regras de Cálculo (PDF Item 3.2)
    val custoBase = dias * orcamento
    val multiplicadorHospedagem = when (hospedagem) {
        "Conforto" -> 1.5
        "Luxo" -> 2.2
        else -> 1.0 // Econômica
    }

    var extras = 0.0
    if (transporte) extras += 300.0
    if (alimentacao) extras += (50.0 * dias)
    if (passeios) extras += (120.0 * dias)

    val custoTotal = (custoBase * multiplicadorHospedagem) + extras

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Resumo da Viagem", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Destino: $destino")
        Text("Duração: $dias dias")
        Text("Orçamento Diário Base: R$ $orcamento")
        Text("Hospedagem: $hospedagem")

        Spacer(modifier = Modifier.height(8.dp))
        Text("Serviços Contratados:")
        if (transporte) Text("- Transporte (R$ 300)")
        if (alimentacao) Text("- Alimentação (R$ 50/dia)")
        if (passeios) Text("- Passeios (R$ 120/dia)")
        if (!transporte && !alimentacao && !passeios) Text("- Nenhum")

        Spacer(modifier = Modifier.height(24.dp))

        // Exibição do Custo Total formatado
        Text(
            text = "Custo Total: R$ ${String.format("%.2f", custoTotal)}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Reinicia o app limpando a pilha de navegação
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar Planejamento")
        }
    }
}
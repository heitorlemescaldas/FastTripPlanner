package br.edu.ifsp.scl.sc303769x.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc303769x.fasttripplanner.ui.theme.FastTripPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tela1DadosViagem(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Tela1DadosViagem(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // rememberSaveable preserva os dados na rotação da tela
    var destino by rememberSaveable { mutableStateOf("") }
    var dias by rememberSaveable { mutableStateOf("") }
    var orcamento by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Fast Trip Planner", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dias,
            onValueChange = { dias = it },
            label = { Text("Número de dias") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = orcamento,
            onValueChange = { orcamento = it },
            label = { Text("Orçamento Diário (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Validação de dados (RF01)
                val diasInt = dias.toIntOrNull()
                val orcamentoDouble = orcamento.toDoubleOrNull()

                if (destino.isNotBlank() && diasInt != null && diasInt > 0 && orcamentoDouble != null && orcamentoDouble > 0) {
                    // Dispara Intent para a Tela 2 (RF04)
                    val intent = Intent(context, OptionsActivity::class.java).apply {
                        putExtra(TripExtras.DESTINO, destino)
                        putExtra(TripExtras.DIAS, diasInt)
                        putExtra(TripExtras.ORCAMENTO, orcamentoDouble)
                    }
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Avançar para Opções")
        }
    }
}

// Objeto para padronizar as chaves das Intents
object TripExtras {
    const val DESTINO = "extra_destino"
    const val DIAS = "extra_dias"
    const val ORCAMENTO = "extra_orcamento"
    const val HOSPEDAGEM = "extra_hospedagem"
    const val TRANSPORTE = "extra_transporte"
    const val ALIMENTACAO = "extra_alimentacao"
    const val PASSEIOS = "extra_passeios"

    const val MODOECONOMICO = "modo_economico"
}
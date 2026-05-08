package br.edu.ifsp.scl.sc303769x.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc303769x.fasttripplanner.ui.theme.FastTripPlannerTheme

class OptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Resgatando dados da Tela 1
        val destino = intent.getStringExtra(TripExtras.DESTINO) ?: ""
        val dias = intent.getIntExtra(TripExtras.DIAS, 0)
        val orcamento = intent.getDoubleExtra(TripExtras.ORCAMENTO, 0.0)

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tela2OpcoesViagem(
                        destino = destino,
                        dias = dias,
                        orcamento = orcamento,
                        onBack = { finish() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Tela2OpcoesViagem(destino: String, dias: Int, orcamento: Double, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Opções de Hospedagem (Radio Buttons)
    val hospedagemOptions = listOf("Econômica", "Conforto", "Luxo")
    val somenteEconomica = "Econômica"
    var hospedagemSelecionada by rememberSaveable { mutableStateOf(hospedagemOptions[0]) }

    // Serviços Adicionais (Checkboxes)
    var isTransporte by rememberSaveable { mutableStateOf(false) }
    var isAlimentacao by rememberSaveable { mutableStateOf(false) }
    var isPasseios by rememberSaveable { mutableStateOf(false) }

    // Modo Economico
    var modoEconomico by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = modoEconomico, onCheckedChange = { modoEconomico = it })
            Text("Modo Econômico")
        }

        Text("Opções de Hospedagem", style = MaterialTheme.typography.titleMedium)
        if (modoEconomico)  hospedagemOptions.forEach { opcao ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (opcao == somenteEconomica),
                    onClick = { somenteEconomica }
                )
                Text(text = opcao)
            }
        }
        else
        hospedagemOptions.forEach { opcao ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (opcao == hospedagemSelecionada),
                    onClick = { hospedagemSelecionada = opcao }
                )
                Text(text = opcao)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Serviços Adicionais", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isTransporte, onCheckedChange = { isTransporte = it })
            Text("Transporte (+ R$ 300 fixo)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isAlimentacao, onCheckedChange = { isAlimentacao = it })
            Text("Alimentação (+ R$ 50/dia)")
        }
        if (modoEconomico)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Opção de Passeios Indisponível no Modo Econômico")
            }
        else
            Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isPasseios, onCheckedChange = { isPasseios = it })
            Text("Passeios (+ R$ 120/dia)")
            }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = onBack) {
                Text("Voltar")
            }
            Button(onClick = {
                // Passa TODOS os dados para a Tela 3
                val intent = Intent(context, SummaryActivity::class.java).apply {
                    putExtra(TripExtras.DESTINO, destino)
                    putExtra(TripExtras.DIAS, dias)
                    putExtra(TripExtras.ORCAMENTO, orcamento)
                    putExtra(TripExtras.HOSPEDAGEM, hospedagemSelecionada)
                    putExtra(TripExtras.TRANSPORTE, isTransporte)
                    putExtra(TripExtras.ALIMENTACAO, isAlimentacao)
                    putExtra(TripExtras.PASSEIOS, isPasseios)
                    putExtra(TripExtras.MODOECONOMICO, modoEconomico)
                }
                context.startActivity(intent)
            }) {
                Text("Calcular")
            }
        }
    }
}
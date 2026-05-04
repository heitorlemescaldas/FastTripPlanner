# FastTripPlanner

Aplicativo Android nativo para planejamento e cálculo de custos de viagens. Desenvolvido como requisito de avaliação prático para a disciplina de Programação para Dispositivos Móveis.

## 🚀 Sobre o Projeto

O FastTripPlanner é uma aplicação mobile focada na aplicação prática de navegação, passagem de parâmetros e gerenciamento de estado sem dependência de bancos de dados ou APIs externas. O fluxo de dados é inteiramente gerenciado em memória e transportado entre as telas da aplicação.

## 🛠 Tecnologias e Arquitetura

*   **Linguagem:** Kotlin
*   **Interface Gráfica:** Jetpack Compose (UI Declarativa)
*   **Navegação:** Intents Explícitas (transporte de estado via `Extras`)
*   **Gerenciamento de Estado:** `rememberSaveable` para preservação de dados durante o ciclo de vida (ex: rotação de tela)

## ⚙️ Funcionalidades Implementadas

O fluxo da aplicação é dividido em três etapas sequenciais:

1.  **Dados da Viagem (Tela 1):**
    *   Coleta de Destino, Duração (dias) e Orçamento Diário.
    *   Validação de campos nulos ou zerados.
2.  **Opções da Viagem (Tela 2):**
    *   Seleção de categoria de hospedagem (Econômica, Conforto, Luxo) via RadioButtons.
    *   Seleção de serviços adicionais (Transporte, Alimentação, Passeios) via Checkboxes.
3.  **Resumo e Cálculo (Tela 3):**
    *   Processamento matemático do custo total baseado em multiplicadores de hospedagem e taxas extras diárias/fixas.
    *   Exibição do consolidado da viagem.
    *   Ação de "Reiniciar", limpando a pilha de navegação (`FLAG_ACTIVITY_CLEAR_TOP`).

## 🧮 Regras de Cálculo

O algoritmo de precificação na etapa final segue a seguinte regra de negócio:

*   **Custo Base:** `Dias * Orçamento Diário`
*   **Multiplicador de Hospedagem:** Econômica (1.0x), Conforto (1.5x), Luxo (2.2x).
*   **Acréscimos:**
    *   Transporte: + R$ 300,00 (Fixo)
    *   Alimentação: + R$ 50,00 / dia
    *   Passeios: + R$ 120,00 / dia

## 💻 Como Executar

1.  Clone este repositório.
2.  Abra o projeto no **Android Studio** (versão recomendada com suporte a Jetpack Compose).
3.  Aguarde o Gradle sincronizar as dependências.
4.  Execute o aplicativo em um emulador ou dispositivo físico com Android 8.0 (API 26) ou superior.
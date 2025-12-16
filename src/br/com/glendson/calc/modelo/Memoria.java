package br.com.glendson.calc.modelo;

import java.util.List;
import java.util.ArrayList;

public class Memoria {

    private enum TipoComando {
        ZERAR, NUMERO, SINAL, IGUAL, VIRGULA, OPERACAO
    }

    private static final Memoria instancia = new Memoria();

    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private String textoAtual = "";
    private String textoBuffer = "";
    private boolean substituir = false;
    private TipoComando ultimaOperacao = null;

    private Memoria() {
    }

    public static Memoria getInstancia() {
        return instancia;
    }

    public void adicionarObservador(MemoriaObservador observador) {
        observadores.add(observador);
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }

    public void setTextoAtual(String texto) {

        TipoComando tipoComando = detectarTipoComando(texto);

        if (tipoComando == null) {
            return;
        } else if (tipoComando == TipoComando.ZERAR) {
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOperacao = null;
            ultimaOperacaoSimbolo = "";
        } else if (tipoComando == TipoComando.NUMERO) {
            textoAtual = substituir ? texto : textoAtual + texto;
            substituir = false;
        } else if (tipoComando == TipoComando.VIRGULA) {
            if (substituir) {
                textoAtual = "0.";
                substituir = false;
            } else if (!textoAtual.contains(".")) {
                textoAtual += ".";
            }
        } else if (tipoComando == TipoComando.OPERACAO) {
            // calcula usando a operação anterior (se existir)
            if (ultimaOperacao != null && ultimaOperacao != TipoComando.IGUAL && !ultimaOperacaoSimbolo.isEmpty()) {
                double v1 = Double.parseDouble(textoBuffer.isEmpty() ? "0" : textoBuffer);
                double v2 = Double.parseDouble(textoAtual.isEmpty() ? "0" : textoAtual);
                double resultado = calcular(v1, v2, ultimaOperacaoSimbolo);
                textoAtual = String.valueOf(resultado);
                textoBuffer = textoAtual;
            } else {
                textoBuffer = textoAtual.isEmpty() ? "0" : textoAtual;
            }
            substituir = true;
            // agora atualiza o símbolo para a nova operação (texto recebido)
            ultimaOperacaoSimbolo = texto;
            ultimaOperacao = TipoComando.OPERACAO;
        } else if (tipoComando == TipoComando.IGUAL) {
            if (ultimaOperacao != null && !ultimaOperacaoSimbolo.isEmpty()) {
                double v1 = Double.parseDouble(textoBuffer.isEmpty() ? "0" : textoBuffer);
                double v2 = Double.parseDouble(textoAtual.isEmpty() ? "0" : textoAtual);
                double resultado = calcular(v1, v2, ultimaOperacaoSimbolo);
                textoAtual = String.valueOf(resultado);
                substituir = true;
                ultimaOperacao = TipoComando.IGUAL;
                ultimaOperacaoSimbolo = "";
            }
        }

        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private String ultimaOperacaoSimbolo = "";

    private double calcular(double valor1, double valor2, String operacao) {
        switch (operacao) {
            case "+":
                return valor1 + valor2;
            case "-":
                return valor1 - valor2;
            case "*":
                return valor1 * valor2;
            case "/":
                if (valor2 == 0) {
                    throw new ArithmeticException("Divisão por zero não é permitida.");
                }
                return valor1 / valor2;
            default:
                throw new IllegalArgumentException("Operação desconhecida: " + operacao);
        }
    }

    private TipoComando detectarTipoComando(String texto) {
        if (texto == null || texto.isEmpty()) {
            return null;
        } else if (texto.matches("\\d")) { // dígito
            return TipoComando.NUMERO;
        } else if ("+".equals(texto) || "-".equals(texto) || "*".equals(texto) || "/".equals(texto)) {
            return TipoComando.OPERACAO;
        } else if ("=".equals(texto)) {
            return TipoComando.IGUAL;
        } else if (",".equals(texto) || ".".equals(texto)) {
            return TipoComando.VIRGULA;
        } else if ("AC".equals(texto)) {
            return TipoComando.ZERAR;
        } else {
            return TipoComando.SINAL;
        }
    }
}

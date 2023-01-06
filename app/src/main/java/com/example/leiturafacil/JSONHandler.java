package com.example.leiturafacil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class JSONHandler {
    public static String iniciaRequisicao(HttpURLConnection conexao) throws IOException {
        int codigoSucesso = 200;
        if (conexao.getResponseCode() != codigoSucesso)
            throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

        BufferedReader resposta = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

        return converteJsonParaString(resposta);
    }

    public static String converteJsonParaString(BufferedReader resposta) throws IOException {
        String response;
        StringBuilder jsonEmString = new StringBuilder();
        while ((response = resposta.readLine()) != null) {
            jsonEmString.append(response);
        }

        return jsonEmString.toString();
    }
}

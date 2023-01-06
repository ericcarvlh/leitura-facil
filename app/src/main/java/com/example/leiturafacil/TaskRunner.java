package com.example.leiturafacil;

import static com.example.leiturafacil.JSONHandler.iniciaRequisicao;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onComplete(R result);
    }

    public <eR> void executeAsync(Callable<eR> callable, Callback<eR> callback) {
        executor.execute(() -> {
            try {
                final eR result = callable.call();

                handler.post(() -> {
                    callback.onComplete(result);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

class ColetaDadosLivro implements Callable<String> {
    private final HttpURLConnection conexao;

    public ColetaDadosLivro(HttpURLConnection conexao) {
        this.conexao = conexao;
    }

    @Override
    public String call() throws JSONException, IOException {
        String jsonEmString = iniciaRequisicao(conexao);
        JSONObject jsonObject = new JSONObject(jsonEmString);
        return jsonObject.getJSONObject("paginacao").get("total_livros").toString();
    }
}

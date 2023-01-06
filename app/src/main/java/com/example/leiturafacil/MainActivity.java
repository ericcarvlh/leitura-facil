package com.example.leiturafacil;

import static com.example.leiturafacil.JSONHandler.iniciaRequisicao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    Button buttonPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPesquisar = findViewById(R.id.buttonPesquisar);

        buttonPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewQuantidadeLivro = findViewById(R.id.textViewQuantidadeDeLivros);
                String url = "https://lambda.estantevirtual.com.br/busca/exemplares/listagem?",
                format = "format=json",
                vendedor = "&vendedor=sebonovafloresta",
                pagina = "&pagina=1",
                order = "&order=data_cadastro";

                try {
                    String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
                    acceptLanguage = "pt-BR,pt;q=0.8",
                    referer = "https://www.facebook.com/",
                    userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

                    URL urlParaChamada = new URL(url+format+vendedor+pagina+order);
                    HttpURLConnection conexao = (HttpURLConnection) urlParaChamada.openConnection();
                    conexao.setRequestProperty("User-Agent", userAgent);
                    conexao.setRequestProperty("Accept", accept);
                    conexao.setRequestProperty("Accept-Language", acceptLanguage);
                    conexao.setRequestProperty("Referer", referer);

                    TaskRunner taskRunner = new TaskRunner();
                    taskRunner.executeAsync(new ColetaDadosLivro(conexao), (data) -> {
                        textViewQuantidadeLivro.setText(String.format("Há um total de %s livros.", data));
                    });

                    /*ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());
                    executor.execute(() -> {
                        String quantidadeLivro = "";
                        try {
                            JSONObject jsonObject = new JSONObject(JSONHandler.iniciaRequisicao(conexao));
                            quantidadeLivro = jsonObject.getJSONObject("paginacao").get("total_livros").toString();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        String finalQuantidadeLivro = quantidadeLivro;
                        handler.post(() -> {
                            textViewQuantidadeLivro.setText(String.format("Há um total de %s livros.", finalQuantidadeLivro));
                        });
                    });*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
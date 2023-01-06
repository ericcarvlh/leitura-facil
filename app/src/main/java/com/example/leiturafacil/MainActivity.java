package com.example.leiturafacil;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonPesquisar = findViewById(R.id.buttonPesquisar);

        buttonPesquisar.setOnClickListener(view -> {
            EditText editTextNomeLivreiro = findViewById(R.id.editTextNomeLivreiro),
            editTextNomeLivro = findViewById(R.id.editTextNomeLivro);
            TextView textViewQuantidadeLivro = findViewById(R.id.textViewQuantidadeDeLivros);

            String url = "https://lambda.estantevirtual.com.br/busca/exemplares/listagem?format=json",
            vendedor = "&vendedor=sebonovafloresta", pagina = "&pagina=1",
            order = "&order=data_cadastro";

            String nomeLivro = "", nomeLivreiro = "";

            nomeLivro = editTextNomeLivro.getText().toString();
            nomeLivreiro = editTextNomeLivreiro.getText().toString();

            try {
                if (nomeLivro.length() > 0)
                    url += String.format("&refinar_busca=%s", URLEncoder.encode(String.format("[\"%s\"]", nomeLivro), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (nomeLivreiro.length() > 0)
                vendedor = String.format("&vendedor=%s", nomeLivreiro);

            Log.d("urlParaChamada", url);

            try {
                String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
                acceptLanguage = "pt-BR,pt;q=0.8",
                referer = "https://www.facebook.com/",
                userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

                URL urlParaChamada = new URL(url+vendedor+pagina+order);

                Log.d("urlParaChamada", urlParaChamada.toString());

                /*HttpURLConnection conexao = (HttpURLConnection) urlParaChamada.openConnection();
                conexao.setRequestProperty("User-Agent", userAgent);
                conexao.setRequestProperty("Accept", accept);
                conexao.setRequestProperty("Accept-Language", acceptLanguage);
                conexao.setRequestProperty("Referer", referer);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    try {
                        JSONObject jsonObject = new JSONObject(JSONHandler.iniciaRequisicao(conexao));
                        String quantidadeLivro = jsonObject.getJSONObject("paginacao").get("total_livros").toString();
                        handler.post(() -> textViewQuantidadeLivro.setText(String.format("Há um total de %s livro(s).", quantidadeLivro)));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                });

                executor.shutdown();*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
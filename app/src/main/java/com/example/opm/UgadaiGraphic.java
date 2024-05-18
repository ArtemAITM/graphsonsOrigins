package com.example.opm;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.opm.databinding.ActivityUgadaiGraphicBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UgadaiGraphic extends AppCompatActivity {
    private ActivityUgadaiGraphicBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUgadaiGraphicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToServer();
            }
        });
        private void sendMessageToServer() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("localhost", 8080); // IP-адрес и порт сервера
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        // Отправка данных на сервер
                        String message = messageEditText.getText().toString();
                        out.println(message);

                        // Получение ответа от сервера
                        String response = in.readLine();
                        final String finalResponse = response;
                        // Обновление UI на основном потоке
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                responseTextView.setText(finalResponse);
                            }
                        });

                        // Закрытие соединения
                        in.close();
                        out.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

}
}
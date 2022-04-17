package com.example.olimpiadazueva;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialog extends AppCompatDialogFragment {
    OlimGame game;

    //Конструктор
    public MyDialog(OlimGame game) {
        this.game = game;
    }


    //Выводит окно с инфой по игре
    @SuppressLint({"ResourceType"})
    @Nullable
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("APP_LOG", "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(game.city)
                .setMessage("Город: " + game.city + "\n" +
                        "Страна: " + game.country + "\n" +
                        "Год проведения: " + game.year + "\n" +
                        "Начало: " + game.start + "\n" +
                        "Конец: " + game.finish + "\n" +
                        "Тип игр: " + game.gametype)
                .setIcon(R.drawable.ic_launcher_background)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    // Закрываем окно с инфой по игре
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}

package com.example.olimpiadazueva;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity implements OnlClickShowModalInterface {
    private MapView mapview;
    private MapObjectCollection mapObjects;
    private ArrayList<OlimGame> olimGames;
    private ArrayList<PlacemarkMapObject> points = new ArrayList<>();
    public int a = 0;

    //Функция, отвечающая за нажатие на текст в левом верхнем углу
    public void onMyButtonClick(View view) {
        a++;
        Log.i("APP_LOG", "onMyButtonClick");
        Toast.makeText(this, "Да, вот такая я крутая, раз сделала это))", Toast.LENGTH_SHORT).show();
    }

    //Функция, с которой все начинается. Как main() в простой джава программе
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("df512049-4c36-4236-aa9d-cb20dd7511e9");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        mapObjects = mapview.getMap().getMapObjects().addCollection();
        getDataFromBase();
        initMap();
    }

    //Функция,с которой все заканчивается
    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    //Функция, с которой все начинается 2.0
    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    //Функция, которая позволяет рисовать точки
    public Bitmap drawSimpleBitmap(String number) {
        int picSize = 100;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#549140"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(picSize/2, picSize/2, picSize/2, paint);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(25);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(number, picSize / 2,
                picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
        return bitmap;
    }

    //Функция, которая собсивенно рисует много точек при помощи другой функции
    public void initMap(){
        for(int i = 0; i < olimGames.size(); i++) {
            PlacemarkMapObject point = mapObjects
                    .addPlacemark(new Point(olimGames.get(i).attitude, olimGames.get(i).longitude),
                ImageProvider.fromBitmap(drawSimpleBitmap(olimGames.get(i).num)));
            point.setUserData(olimGames.get(i));
            point.addTapListener(circleMapObjectTapListener);
            points.add(point);
        }
    }

    //Берет данные из моей базы данных и вводит их в массив Олимпийских игр при условии
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDataFromBase(){
        olimGames = new ArrayList<>();
        try {
            InputStream inputStream = getBaseContext().getAssets().open("base.csv");
            List<String> lines = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.toList());
            Log.i("APP_LOG", lines.get(0));

            int numb = 0;
            for (int i = 0; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(",");
                if(columns.length >= 9) {
                    numb += 1;
                    try {
                        double attitude = Float.parseFloat(columns[8]);
                        double longitude = Float.parseFloat(columns[9]);


                        olimGames.add(new OlimGame(
                                String.valueOf(numb),
                                columns[1],
                                columns[2],
                                columns[3],
                                columns[4],
                                columns[5],
                                columns[6],
                                columns[7],
                                attitude,
                                longitude));
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Передает другой функции инфу об игре, чтоб та вывела окно с этой инфой
    @Override
    public void showModal(OlimGame game) {
        FragmentManager manager = getSupportFragmentManager();
        MyDialog infoOlimpGameMyDialog = new MyDialog(game);
        infoOlimpGameMyDialog.show(manager, "infoOlimpGameDialog");
    }

    //Она понимает нажатия на метки
    private MapObjectTapListener circleMapObjectTapListener = new MapObjectTapListener() {
        @Override
        public boolean onMapObjectTap(MapObject mapObject, Point point) {
            if (mapObject instanceof PlacemarkMapObject) {
                PlacemarkMapObject placemark = (PlacemarkMapObject)mapObject;
                Object userData = placemark.getUserData();
                if (userData instanceof OlimGame) {
                    OlimGame olimGameData = (OlimGame)userData;
                    showModal(olimGameData);
                }
            }
            return true;
        }
    };
}

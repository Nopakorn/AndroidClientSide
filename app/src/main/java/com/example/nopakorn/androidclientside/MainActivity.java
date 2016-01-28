package com.example.nopakorn.androidclientside;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String SCREEN_OPENING = "opening";
    public static final String SCREEN_FUEL = "fuel";
    public static final String SCREEN_WARNING = "warning";
    public static final String SCREEN_ECO = "eco_bar";
    public static final String SCREEN_ENDING = "ending";
    public static final String SCREEN_BATT1 = "batt1";
    public static final String SCREEN_BATT2 = "batt2";
    public static final String SCREEN_BATT3 = "batt3";
    public static final String SCREEN_BATT4 = "batt4";
    public static final String SCREEN_BATT5 = "batt5";

    TextView textResponse;
    EditText ipEditText;
    Button buttonConnect, buttonConnect2, buttonConnect3, buttonConnect4, buttonBatteryStart;
    Button opening, ending, warning, ecobar, fuel;
    Button ipConfig;
    private MyClientTask myClientTask;
    String SocketServerPORT = "8080";
    public String serverSocket = "192.168.43.121";
    ImageView screenPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonConnect2 = (Button) findViewById(R.id.connect2);
        buttonConnect3 = (Button) findViewById(R.id.connect3);
        buttonConnect4 = (Button) findViewById(R.id.connect4);
        buttonBatteryStart = (Button) findViewById(R.id.batteryStart);

        opening = (Button)findViewById(R.id.opening);
        ending = (Button)findViewById(R.id.ending);
        warning = (Button)findViewById(R.id.warning);
        ecobar = (Button)findViewById(R.id.ecobar);
        fuel = (Button)findViewById(R.id.fuel);

        textResponse = (TextView)findViewById(R.id.response);
        ipEditText = (EditText)findViewById(R.id.ipEditText);
        ipConfig = (Button)findViewById(R.id.ipConfigButton);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonConnect2.setOnClickListener(buttonConnectOnClickListener2);
        buttonConnect3.setOnClickListener(buttonConnectOnClickListener3);
        buttonConnect4.setOnClickListener(buttonConnectOnClickListener4);
        buttonBatteryStart.setOnClickListener(buttonConnectOnClickListenerStart);

        opening.setOnClickListener(buttonOpening);
        ending.setOnClickListener(buttonEnding);
        warning.setOnClickListener(buttonWarning);
        ecobar.setOnClickListener(buttonEcobar);
        fuel.setOnClickListener(buttonFuel);

        screenPre = (ImageView)findViewById(R.id.screenPreview);

        textResponse.setText("CONNECT TO "+serverSocket);
        ipConfig.setOnClickListener(buttonIpConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    OnClickListener buttonIpConfig =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String ipText = ipEditText.getText().toString();
                    serverSocket = ipText;
                    textResponse.setText("CONNECT TO "+serverSocket+"#");
                    ipEditText.setText("");
                    Log.d("ip","getting ip from text: "+ipText);
                }};
    OnClickListener buttonOpening =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "opening";
                    Log.d("ip","opening: "+serverSocket);
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonEnding =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "ending";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonWarning =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "warning";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonFuel =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "fuel";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonEcobar =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "eco_bar";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    OnClickListener buttonConnectOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batt1";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    OnClickListener buttonConnectOnClickListener2 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batt2";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener3 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batt3";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener4 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batt4";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListenerStart =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batt5";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();

                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        String btnName;
        int dstPort;
        String response = "";
        Socket socket = null;
        MyClientTask(String addr, int port, String btn){
            dstAddress = addr;
            dstPort = port;
            btnName = btn;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            OutputStream outputStream;
            String msgButton = btnName;
            try {
                socket = new Socket(dstAddress, dstPort);
                //TODO Sending data to server
                outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgButton);
                socket.shutdownOutput();
                //TODO Receive data from server
                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                Log.d("Client","Start receive");
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                    Log.d("Client", "Receive msg: " + response);
                    if (isCancelled()) break;

                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            screenPreview(response);

            super.onPostExecute(result);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }
    public void screenPreview(String screen) {
        switch (screen){
            case SCREEN_OPENING:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_opening, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_FUEL:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_fuel, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_WARNING:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_warning, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_ECO:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_eco, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_ENDING:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_ending, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT1:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_batt1, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT2:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_batt2, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT3:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_batt3, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT4:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_batt4, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT5:
                screenPre.setImageBitmap(decodeBitmapFromResource(getResources(), R.mipmap.screen_batt5, 108, 192));
                screenPre.setVisibility(View.VISIBLE);
                break;
        }

    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize <maybe we need to change it to options.inSampleSize = 1;>
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}

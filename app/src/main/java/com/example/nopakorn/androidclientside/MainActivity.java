package com.example.nopakorn.androidclientside;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.os.AsyncTask;
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

    public static final String SCREEN_BATT1 = "BATT 1";
    public static final String SCREEN_BATT2 = "BATT 2";
    public static final String SCREEN_BATT3 = "BATT 3";
    public static final String SCREEN_BATT4 = "BATT 4";
    public static final String SCREEN_BATT5 = "BATT 5";
    public static final String SCREEN_OPENING = "OPENING";
    public static final String SCREEN_ENDING = "ENDING";
    public static final String SCREEN_FUEL = "FUEL";
    public static final String SCREEN_WARNING = "WARNING";
    public static final String SCREEN_ECO = "ECO";

    TextView textResponse;
    TextView desciption;
    EditText ipEditText;
    Button buttonConnect, buttonConnect2, buttonConnect3, buttonConnect4, buttonBatteryStart;
    Button opening, ending, warning, ecobar, fuel;
    Button okConfig, openConfig, cancelConfig;
    private MyClientTask myClientTask;
    String SocketServerPORT = "8080";
    public String serverSocket = "192.168.43.1";
    ImageView screenPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
        desciption = (TextView)findViewById(R.id.description);
        ipEditText = (EditText)findViewById(R.id.ipEditText);
        okConfig = (Button)findViewById(R.id.ipOkButton);
        openConfig = (Button)findViewById(R.id.ipConfigButton);
        cancelConfig = (Button)findViewById(R.id.ipCancelButton);

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

        textResponse.setText("CONNECT TO ");
        desciption.setText("\t\t\t"+serverSocket);
        Log.d("save", "" + serverSocket);
        okConfig.setOnClickListener(buttonIpOkConfig);
        openConfig.setOnClickListener(buttonOpenIpConfig);
        cancelConfig.setOnClickListener(buttonIpCancelConfig);

//        ipEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard();
//                }
//            }
//        });
    }

//    private void hideKeyboard() {
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(ipEditText.getWindowToken(), 0);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        Log.d("save","save serverIP");
        outState.putString("IPAdress", serverSocket);
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        Log.d("save","restore serverIP");
        serverSocket = savedInstanceState.getString("IPAdress");
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.d("k", "pause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.d("k", "resume called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(ipEditText.getVisibility() == View.VISIBLE){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.d("screen"," ip edit text visibility");
            return true;
        }else{
            Log.d("screen","false");
            return false;
        }

    }

    OnClickListener buttonIpCancelConfig =
            new OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    ipEditText.setVisibility(View.INVISIBLE);
                    okConfig.setVisibility(View.INVISIBLE);
                    cancelConfig.setVisibility(View.INVISIBLE);
                    openConfig.setVisibility(View.VISIBLE);
                }};

    OnClickListener buttonOpenIpConfig =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    ipEditText.setVisibility(View.VISIBLE);
                    okConfig.setVisibility(View.VISIBLE);
                    cancelConfig.setVisibility(View.VISIBLE);
                    openConfig.setVisibility(View.INVISIBLE);
                }};

    OnClickListener buttonIpOkConfig =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String ipText = ipEditText.getText().toString();
                    if(ipText.matches("")){
                        Log.d("ip", "You did not type ip address");
                        desciption.setText("You did not type ip address");
                        return;
                    }else{
                        serverSocket = ipText;
                        ipEditText.setText("");
                    }

                    textResponse.setText("CONNECT TO ");
                    desciption.setText("\t\t\t"+serverSocket);
                    ipEditText.setVisibility(View.INVISIBLE);
                    cancelConfig.setVisibility(View.INVISIBLE);
                    okConfig.setVisibility(View.INVISIBLE);
                    openConfig.setVisibility(View.VISIBLE);
                }};
    OnClickListener buttonOpening =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "OPENING";
                    Log.d("ip","opening: "+serverSocket);
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonEnding =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "ENDING";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonWarning =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "WARNING";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonFuel =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "FUEL";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonEcobar =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "ECO";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    OnClickListener buttonConnectOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "BATT 1";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    OnClickListener buttonConnectOnClickListener2 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "BATT 2";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener3 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "BATT 3";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener4 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "BATT 4";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListenerStart =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "BATT 5";
                    myClientTask = new MyClientTask(serverSocket, Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();

                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        String btnName;
        int dstPort;
        String response = "";
        String errorDescription = "";
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
                socket = new Socket();
                socket.setReuseAddress(true);
                socket.connect(new InetSocketAddress(dstAddress, dstPort), 5000);
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
                response += "ERROR";
                errorDescription = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response += "ERROR";
                errorDescription = "IOException: " + e.toString();
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
            desciption.setText(errorDescription);

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
                desciption.setText("Opening screen (Welcome screen)");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.opening_screen));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_FUEL:
                desciption.setText("Sample screen (Fuel Consumption screen)");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.fuel_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_WARNING:
                desciption.setText("Sample screen (Master Warning screen)");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.warning_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_ECO:
                desciption.setText("Sample screen (Eco mode screen) \n Use as main/common screen");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.eco_screen));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_ENDING:
                desciption.setText("Ending screen");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.ending_screen));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT1:
                desciption.setText("Battery Normal Status");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.batt1_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT2:
                desciption.setText("Battery Charging Status");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.batt2_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT3:
                desciption.setText("Common Display");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.eco_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT4:
                desciption.setText("Battery Low Status & Abnormal");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.batt4_screen_vnew));
                screenPre.setVisibility(View.VISIBLE);
                break;
            case SCREEN_BATT5:
                desciption.setText("Battery Maintenance screen");
                screenPre.setImageDrawable(getResources().getDrawable(R.mipmap.batt5_screen_vnew));
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

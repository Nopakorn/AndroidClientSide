package com.example.nopakorn.androidclientside;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear, buttonConnect2, buttonConnect3, buttonBatteryStart;
    private MyClientTask myClientTask;
    String SocketServerPORT = "8080";
    ServerSocket serverSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonConnect2 = (Button) findViewById(R.id.connect2);
        buttonConnect3 = (Button) findViewById(R.id.connect3);
        buttonBatteryStart = (Button) findViewById(R.id.batteryStart);
        buttonClear = (Button)findViewById(R.id.clear);
        textResponse = (TextView)findViewById(R.id.response);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonConnect2.setOnClickListener(buttonConnectOnClickListener2);
        buttonConnect3.setOnClickListener(buttonConnectOnClickListener3);
        buttonBatteryStart.setOnClickListener(buttonConnectOnClickListener4);
        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });
        textResponse.setText("CONNECT TO "+getIpAddress());

    }

    @Override
    protected void onDestroy() {
        Log.d("Client","on destroy: cancel async task");
        super.onDestroy();
    }

    OnClickListener buttonConnectOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "a";
                    myClientTask = new MyClientTask("192.168.1.54", Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    OnClickListener buttonConnectOnClickListener3 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "c";
                    myClientTask = new MyClientTask("192.168.1.54", Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener2 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "b";
                    myClientTask = new MyClientTask("192.168.1.54", Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};
    OnClickListener buttonConnectOnClickListener4 =
            new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    String btn_name = "batteryStart";
                    myClientTask = new MyClientTask("192.168.1.54", Integer.parseInt(SocketServerPORT), btn_name);
                    myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        String btnName;
        int dstPort;
        String response = "response: ";
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
                //TODO: sending data to server
                outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgButton);
                socket.shutdownOutput();
                //TODO: receive data from server
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
            super.onPostExecute(result);
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
}

package com.kiros33.app1;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String serverIP = "192.168.25.25";
    private static final int serverPort = 5000;
    private String msg;
    EditText txt1;
    AccumulatorTask SocketThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt1 = (EditText)findViewById(R.id.txt1);
        SocketThread = new AccumulatorTask();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d("MAIN", "ExecuteOnExcutor");
            SocketThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 1, 2, 3);
        }
        else {
            Log.d("MAIN", "Execute");
            SocketThread.execute(1, 2, 3);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "SendDataToNetwork", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                msg = "mr.bar";
                SocketThread.SendDataToNetwork(msg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AccumulatorTask extends AsyncTask<Integer, Integer, Integer>{
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        String ReceiveMsg;

        InetAddress serverAddr;

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");

            try {
                // 소캣을 생성하고
                serverAddr = InetAddress.getByName(serverIP);
                socket = new Socket(serverAddr, serverPort);
                Log.d("TCP", "Connecting...");

                // 입출력 스트림을 생성함
                out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d("TCP", "Stream Initialization");

            } catch (Exception e) {
                Log.d("TCP", "Connecting Fail...");
            }
        }

        protected void onPostExecute() {
            try {
                // 소켓을 닫음
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }



        @Override
        // 주기적으로 실행해야 하는 작업을 doInBackground 매소드에 둔다.
        protected Integer doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            while(isCancelled() == false){
                try {
                    //  Received Message Part
                    Log.d("TCP", "C: Ready Receive Message");
                    ReceiveMsg = in.readLine(); // Wait until message receiving complete
                    Log.d("TCP", "C: Receive Message: [" + ReceiveMsg + "]");
                    publishProgress();

                } catch(Exception e) {
                    Log.d("TCP", "Exception");
                } finally {
                    Log.d("TCP", "Finally");
                }

            }
            return null;
        }

        // doInBackground 에서 주기적으로 처리하다 특정 작업을 수행해야할 경우 이 부분에서 처리함.
        // 이 매소드 안에서는 UI 맴버로의 접근이 가능한 이점이 있다.
        protected void onProgressUpdate(Integer... progress){
            txt1.setText(ReceiveMsg);
            Log.d("TCP",ReceiveMsg);
        }

        public void SendDataToNetwork(String cmd) { //You run this from the main thread.
            try {
                if (socket.isConnected()) {
                    Log.w("AsyncTask", "SendDataToNetwork: Writing received message to socket");
                    out.println(cmd); // Send Message
                } else {
                    Log.w("AsyncTask", "SendDataToNetwork: Cannot send message. Socket is closed");
                }
            } catch (Exception e) {
                Log.w("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception");
            }
        }
    }
}

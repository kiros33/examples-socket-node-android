package com.kiros33.app3;

import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String connectHost = "192.168.25.7";
    private int connectPort = 50;
    Button btnConnect, btnDisconnect, btnSend, btnServer0, btnServer1, btnServer2, btnServer3, btnServer4;
    EditText txtHost, txtPort;
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        contentView = findViewById(android.R.id.content);

        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(btnConnectListener);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnDisconnect.setOnClickListener(btnDisconnectListener);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(btnSendListener);

        btnServer0 = findViewById(R.id.btnServer0);
        btnServer0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("");
                txtPort.setText("");
                Snackbar.make(view, "Clear server information", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer1 = findViewById(R.id.btnServer1);
        btnServer1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("211.111.172.80");
                txtPort.setText("5000");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer2 = findViewById(R.id.btnServer2);
        btnServer2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("192.168.25.7");
                txtPort.setText("5000");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer3 = findViewById(R.id.btnServer3);
        btnServer3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("www.google.com");
                txtPort.setText("80");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer4 = findViewById(R.id.btnServer4);
        btnServer4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("www.naver.com");
                txtPort.setText("80");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        txtHost = findViewById(R.id.txtHost);
        txtPort = findViewById(R.id.txtPort);
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

    private boolean checkConnectionInfo() {
        connectHost = txtHost.getText().toString().trim();
        Log.d("MAIN", "[txtHost]->[" + connectHost + "]");
        if (connectHost.isEmpty()) {
            Log.d("MAIN", "Cannot allow empty hostname");
            Snackbar.make(contentView, "Cannot allow empty hostname", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return false;
        }

        try {
            connectPort = Integer.parseInt(txtPort.getText().toString().trim());
            Log.d("MAIN", "[txtPort]->[" + connectPort + "]");
        }
        catch (Exception ex) {
            Log.d("MAIN", "Incorrect port number\n" + ex.getMessage());
            Snackbar.make(contentView, "Incorrect port number", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return false;
        }

        return true;
    }

    private OnClickListener btnConnectListener = new OnClickListener() {
        public void onClick(View view){
            if (checkConnectionInfo()) {
                Snackbar.make(view, "Connect to " + connectHost + ":" + connectPort, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            //
            //textStatus.setText("Sending Message to AsyncTask.");
            //networktask.SendDataToNetwork("GET / HTTP/1.1\r\n\r\n");
        }
    };

    private OnClickListener btnDisconnectListener = new OnClickListener() {
        public void onClick(View view){
            Snackbar.make(view, "Disconnecting", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };

    private OnClickListener btnSendListener = new OnClickListener() {
        public void onClick(View view){
            Snackbar.make(view, "Send data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    };
}

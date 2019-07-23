package com.kiros33.app3;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 연결한 호스트 IP와 Port 정보, TextEdit에서 읽어와서 설정하고 AsyncTask 생성시 전달함.
    private String connectHost;
    private int connectPort;
    // 연결 및 연결종료 버튼
    Button btnConnect, btnDisconnect;
    // 서버 설정이 저장된 버튼, 0은 초기화, 1, 2, 3, 4 각각 onCreate에서 설정됨
    Button btnSend, btnServer0, btnServer1, btnServer2, btnServer3, btnServer4;

    // Activity에 설정된 컴포넌트 연결을 위한 변수
    EditText txtHost, txtPort, etMessage;
    TextView tvOutput, tvReceivedMessage;
    View contentView;

    // 소켓 클래스 변수
    SocketAsyncTask socTask;

    // 메시지 핸들러에서 사용할 메시지 유형, 핸들러의 경우 int 값만 전달 받도록 설계되어 있음
    // callback 처리하는 부분도 예제에 포함되어있으니 참고
    public static final int SHUTDOWN   = 1;
    public static final int RESTART    = 2;
    public static final int HIBERNATE  = 3;
    public static final int ERROR      = 4;
    public static final int SENDING    = 5;
    public static final int CONNECTING = 6;
    public static final int SENT       = 7;
    public static final int RECEIVE    = 8;

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

        // Snackbar 에서 사용할 view 설정
        contentView = findViewById(android.R.id.content);

        // 버튼을 설정하고 이미 구현된 리스너 함수를 이벤트에 매핑하는 방식으로 설정
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(btnConnectListener);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnDisconnect.setOnClickListener(btnDisconnectListener);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(btnSendListener);

        // 버튼을 설정하고 직접 Anonymous function을 설정하는 방식으로 이벤트 매핑
        btnServer0 = findViewById(R.id.btnServer0);
        // btnServer0 버튼 클릭 이벤트
        btnServer0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("");
                txtPort.setText("");
                Snackbar.make(view, "Clear server information", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer1 = findViewById(R.id.btnServer1);
        // btnServer1 버튼 클릭 이벤트
        btnServer1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("www.google.com");
                txtPort.setText("80");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer2 = findViewById(R.id.btnServer2);
        // btnServer2 버튼 클릭 이벤트
        btnServer2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("192.168.25.7");
                txtPort.setText("5000");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer3 = findViewById(R.id.btnServer3);
        // btnServer3 버튼 클릭 이벤트
        btnServer3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("192.168.1.163");
                txtPort.setText("5000");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnServer4 = findViewById(R.id.btnServer4);
        // btnServer4 버튼 클릭 이벤트
        btnServer4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHost.setText("www.naver.com");
                txtPort.setText("80");
                Snackbar.make(view, "Set server to " + txtHost.getText().toString().trim() + ":" + txtPort.getText().toString().trim(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        // TextEdit 및 TextView 컴포넌 설정
        txtHost = findViewById(R.id.txtHost);
        txtPort = findViewById(R.id.txtPort);
        etMessage = findViewById(R.id.etMessage);

        tvOutput = findViewById(R.id.tvOutput);
        tvReceivedMessage = findViewById(R.id.tvReceivedMessage);
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

    /*
    TextEdit 에서 서버 IP와 Port 값을 확인하고 문제가 없다면 변수에 설정한다.
     */
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

    /*
    Connect 버튼 클릭 이벤트
     */
    private OnClickListener btnConnectListener = new OnClickListener() {
        public void onClick(View view){
            if (checkConnectionInfo()) {
                Snackbar.make(view, "Connect to " + connectHost + ":" + connectPort, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

            // 서버에 특정 메시지를 전달 한 뒤 혹은 서버로부터 특정 메시지를 수신 한 경우
            // 안드로이드 프로그램에서 관련 동작을 수행해야 할 때 핸들러에 동작을 추가하면 됨
            // 예: 로그인 정보 전달 후 성공메시지를 받았다면 로그인 액티비티에서 다른 액티비티로 전환하는 동작
            //    서버에서 전달된 메시지에 따라 화면이 전환되어야 할 경우
            Handler mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    String mTag = "Handler";
                    switch(msg.what){
                        case SHUTDOWN:
                            Log.d(mTag, "In Handler's shutdown");
                            tvOutput.setText("Shutting PC...");
                            break;
                        case ERROR:
                            Log.d(mTag, "In Handler's error");
                            tvOutput.setText("Something went wrong...");
                            break;
                        case SENDING:
                            Log.d(mTag, "In Handler's sending");
                            tvOutput.setText("Sending message...");
                            break;
                        case CONNECTING:
                            Log.d(mTag, "In Handler's connecting");
                            tvOutput.setText("Connecting...");
                            break;
                        case SENT:
                            Log.d(mTag, "In Handler's sent");
                            tvOutput.setText("Waiting for command...");
                            break;
                        case RECEIVE:
                            Log.d(mTag, "In Handler's RECEIVE");
                            tvOutput.setText("Message received");
                    }
                }
            };

            socTask = new SocketAsyncTask(mHandler, new MessageReceiveCallbackInterface() {
                @Override
                public void onReceived(String msg) {
                    String mTag = "MessageReceiveCallback";
                    Log.d(mTag, msg);
                    tvReceivedMessage.setText(msg);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Log.d("btnConnectListener", "executeOnExecutor");
                socTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, connectHost, connectPort);
            }
            else {
                Log.d("btnConnectListener", "execute");
                socTask.execute(connectHost, connectPort);
            }
        }
    };

    /*
    Disconnect 클릭 이벤트
     */
    private OnClickListener btnDisconnectListener = new OnClickListener() {
        public void onClick(View view){
            Snackbar.make(view, "Disconnecting", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Log.d("btnDisconnectListener", "task cancel");
            //ftpTask.cancel(true);
            socTask.stop();
        }
    };

    /*
    Send 클릭 이벤트
    설정된 값이 없으면 "default message" String을 전달
     */
    private OnClickListener btnSendListener = new OnClickListener() {
        public void onClick(View view){
            Snackbar.make(view, "Send data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            String msg = etMessage.getText().toString();
            if (msg.length() <= 0) msg = "default message";
            socTask.sendMessage(msg);
        }
    };
}
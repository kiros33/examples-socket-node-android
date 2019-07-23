package com.kiros33.app3;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Mariusz on 15.10.14.
 *
 * AsyncTask class which manages connection with server app and is sending shutdown command.
 *
 * doInBackground       : IP 문자열과 Port 숫자를 받을 수 있도록 Object 타입으로 설정
 * onProgressUpdate     : 서버로부터 전달된 문자열을 받아서 처리하도록 String 타입으로 설정
 * onPostExecute        : 비동기처리가 종료된 후에 doInBackground에서 생성된 TCPClient 객체를 dispose 하도록 설정
 */
public class SocketAsyncTask extends AsyncTask<Object, String, TCPClient> {
    private              TCPClient  tcpClient                        ;
    private              Handler    mHandler                         ;
    private static final String     TAG         = "SocketAsyncTask";
    String connectHost;
    int connectPort;

    final MessageReceiveCallbackInterface messageReceivedCallback;

    /**
     * ShutdownAsyncTask constructor with handler passed as argument. The UI is updated via handler.
     * In doInBackground(...) method, the handler is passed to TCPClient object.
     * @param mHandler Handler object that is retrieved from MainActivity class and passed to TCPClient
     *                 class for sending messages and updating UI.
     */

    /*
    생성자: 이벤트 핸들러만 받아서 활용하는 경우
     */
    public SocketAsyncTask(Handler mHandler){
        this(mHandler, null);
    }
    /*
    생성자: 이벤트 핸들러와 콜백함수를 받아서 함께 활용하는 경우
     */
    public SocketAsyncTask(Handler mHandler, MessageReceiveCallbackInterface messageReceivedCallback) {
        this.mHandler = mHandler;
        this.messageReceivedCallback = messageReceivedCallback;
    }

    /**
     * Overriden method from AsyncTask class. There the TCPClient object is created.
     * @param params From MainActivity class empty string is passed.
     * @return TCPClient object for closing it in onPostExecute method.
     */

    /*
    백그라운드로 실행할 동작 설정
    tcpClient.run() 을 실행하면 socket의 inputstream을 while 문으로 반복하면서 읽는다. while 문은 TCPClient내의 mRun 변수로 제어
    TCPClient 객체를 생성하면서
        mHandler(이벤트핸들러)
        command(연결시 전달할 명령/메시지)
        connectHost(서버 IP)
        connectPort(서버 Port)
        callback(TCPPClinet에서 호출하여 SocketAsyncTask를 제어할 콜백함수)
     값을 전달한다.
     */
    @Override
    protected TCPClient doInBackground(Object... params) {
        Log.d(TAG, "In do in background");

        connectHost = params[0].toString();
        connectPort = ((int) params[1]);

        try{
            tcpClient = new TCPClient(mHandler,
                                      "command",
                                      connectHost,
                                      connectPort,
                                      new TCPClient.MessageCallback() {
                @Override
                public void callbackMessageReceiver(String message) {
                    Log.d("callbackMessageReceiver", "[message][" + message + "]");
                    publishProgress(message);
                }
            });

        }catch (NullPointerException e){
            Log.d(TAG, "Caught null pointer exception");
            e.printStackTrace();
        }
        tcpClient.run();
        Log.d(TAG, "tcpClient.run()");
        return null;
    }

    /**
     * Overriden method from AsyncTask class. Here we're checking if server answered properly.
     * @param values If "restart" message came, the client is stopped and computer should be restarted.
     *               Otherwise "wrong" message is sent and 'Error' message is shown in UI.
     */

    /*
    callbackMessageReceiver 메시지콜백함수에서 호출됨
    publishProgress() 함수가 수행되면 호출되는 이벤트
     */
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        for (String v: values) {
            Log.d(TAG, "In progress update, values: " + v);
        }


        // 수신된 메시지에 따라 프로그램이 동작해야 하면 핸들러에 메시지로 전달하여 수행하도록 할 수 있음

        /*
        if(values[0].equals("shutdown")){
            tcpClient.sendMessage("COMMAND");
            tcpClient.stopClient();
            mHandler.sendEmptyMessageDelayed(MainActivity.SHUTDOWN, 2000);

        }else{
            tcpClient.sendMessage("wrong");
            mHandler.sendEmptyMessageDelayed(MainActivity.ERROR, 2000);
            tcpClient.stopClient();
        }
        */

        /*
        SocketAsyncTask를 생성하면서 전달한 callback 함수가 null 이 아니면 호출
        MainActivity에서 익명함수로 생성되서 전달되며 tvReceviedMessage 객체를 제어하는데 사용됨.
         */
        if (messageReceivedCallback != null) {
            messageReceivedCallback.onReceived(values[0]);
        }
    }

    /*
    doInBackground 함수가 종료된 후 호출되는 후처리 함수
    TCPClient 객체를 종료하는데 사용
     */
    @Override
    protected void onPostExecute(TCPClient result){
        super.onPostExecute(result);
        Log.d(TAG, "In on post execute");
        if(result != null && result.isRunning()){
            result.stopClient();
        }

        // 프로세스가 중단되었을 때 전달할 메시지 처리
        // mHandler.sendEmptyMessageDelayed(MainActivity.SENT, 4000);

    }

    /*
    서버에 메시지를 보내는데 사용하는 함수
    TCPClient의 sendMessage 함수 호출
     */
    public void sendMessage(String msg) {
        try {
            if (tcpClient.isRunning()) {
                Log.w(TAG, "sendMessage, " + msg);
                tcpClient.sendMessage(msg);
            } else {
                Log.w(TAG, "sendMessage, tcpClient is stopped");
            }
        } catch (Exception e) {
            Log.w(TAG, "sendMessage raise exception\n" + e.getMessage());
        }
    }

    /*
    MainActivity에서 Disconnect 버튼을 누르면 호출하는 연결해제 처리
    TCPClient가 종료되면 doInBackground 함수가 종료되고 onPostExecute 함수가 호출된다.
     */
    public void stop() {
        tcpClient.stopClient();
    }
}

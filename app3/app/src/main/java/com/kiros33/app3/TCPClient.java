package com.kiros33.app3;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Mariusz on 09.10.14.
 *
 * TCPClient is class responsible for creating, maintaining and handling the connection between
 * android device and computer / other device that has TCP server enabled and listening for
 * connection.
 */
public class TCPClient {

    private static final String            TAG             = "TCPClient"     ;
    private final        Handler           mHandler                          ;
    private int connectPort;
    private              String            connectHost, incomingMessage, command;
                         BufferedReader    in                                ;
                         PrintWriter       out                               ;
    private              MessageCallback   listener        = null            ;
    private              boolean           mRun            = false           ;

    private Socket socket;


    /**
     * TCPClient class constructor, which is created in AsyncTasks after the button click.
     * @param mHandler Handler passed as an argument for updating the UI with sent messages
     * @param command  Command passed as an argument, e.g. "shutdown -r" for restarting computer
     * @param connectHost String retrieved from IpGetter class that is looking for ip number.
     * @param listener Callback interface object
     */
    public TCPClient(Handler mHandler, String command, String connectHost, int connectPort, MessageCallback listener) {
        this.listener         = listener;
        this.connectHost      = connectHost;
        this.connectPort = connectPort;
        this.command          = command ;
        this.mHandler         = mHandler;
    }

    /**
     * Public method for sending the message via OutputStream object.
     * @param message Message passed as an argument and sent via OutputStream object.
     */

    /*
    서버에 메시지를 전달하는 함수
    별도 Thread로 처리하지 않으면 Exception 발생
    1) 서버에 메시지를 전달한 뒤에
    2) mHandler 핸들러를 통해 MainActivity에도 SENDING 메시지를 전달한다.
     */

    public void sendMessage(final String message){
        if (out != null && !out.checkError()) {
            new Thread() {
                public void run() {
                    out.println(message);
                    out.flush();
                    mHandler.sendEmptyMessageDelayed(MainActivity.SENDING, 1000);
                    Log.d(TAG, "Sent Message: " + message);
                }
            }.start();
        }
    }

    /**
     * Public method for stopping the TCPClient object ( and finalizing it after that ) from AsyncTask
     */

    /*
    TCPClient 중지하는 함수
    readLine() 함수가 구동되면 자체 대기모드로 들어가서 서버에서 메시지를 받지 않으면 mRun 만으로 제어가 되지 않는 문제가 있음
    따라서 직접 소켓을 받아서 readLine() 함수가 간접종료되도록 처리하고 예외처리로직에서 부가처리하고 있음.
     */
    public void stopClient(){
        Log.d(TAG, "Client stopped!");
        try {
            if (mRun) {
                mRun = false;
                // readLine() 함수가 종료되지 않고 대기 중이면 멈추지 않음.
                Log.d(TAG, "closing socket by stopClient");
                if (socket.isClosed() == false) {
                    out.flush();
                    out.close();
                    in.close();
                    socket.close();
                }
            }
        }
        catch (Exception ex) {
            Log.d(TAG, "socket close", ex);
        }
    }

    /**
     * Public method for creating and handling the connection.
     */

    /*
    SocketAsyncTask 의 doInBackground에서 호출되는 함수
    내부에서 readLine() 함수와 mRun 변수로 메인루프를 구성함.
    메시지를 받으면
        1) callbackMessageReceiver 콜백함수를 호출하게 됨.
        2) SocketAsyncTask의 callbackMessageReceiver 에서 onProgressUpdate 함수를 호출하게되고
        3) onProgressUpdate에서 MainActivity에서 정의된 messageReceivedCallback.onReceived 함수를 호출하여
        4) tvReceivedMessage 에 서버에서 받은 문자열을 출력하게됨.
     */

    public void run() {

        mRun = true;

        try {
            // Creating InetAddress object from ipNumber passed via constructor from IpGetter class.
            InetAddress serverAddress = InetAddress.getByName(connectHost);

            Log.d(TAG, "Connecting...");

            /**
             * Sending empty message with static int value from MainActivity
             * to update UI ( 'Connecting...' ).
             *
             * @see com.example.turnmeoff.MainActivity.CONNECTING
             */
            mHandler.sendEmptyMessageDelayed(MainActivity.CONNECTING,1000);

            /**
             * Here the socket is created with hardcoded port.
             * Also the port is given in IpGetter class.
             *
             * @see com.example.turnmeoff.IpGetter
             */
            socket = new Socket(serverAddress, connectPort);


            try {

                // Create PrintWriter object for sending messages to server.
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //Create BufferedReader object for receiving messages from server.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Log.d(TAG, "In/Out created");

                Log.d(TAG, "sendMessage, " + command);
                //Sending message with command specified by AsyncTask
                this.sendMessage(command);

                Log.d(TAG, "incoming loop, mRun is " + mRun);
                Log.d(TAG, "incoming loop, listener is " + ((listener != null)?"NOT NULL":"NULL"));
                //Listen for the incoming messages while mRun = true
                while (mRun) {
                    incomingMessage = in.readLine();

                    if (incomingMessage != null && listener != null) {

                        Log.d(TAG, "message incoming");

                        /**
                         * Incoming message is passed to MessageCallback object.
                         * Next it is retrieved by AsyncTask and passed to onPublishProgress method.
                         *
                         */
                        listener.callbackMessageReceiver(incomingMessage);

                    }
                    incomingMessage = null;

                }

                Log.d(TAG, "Received Message: " +incomingMessage);

            } catch (Exception e) {

                Log.d(TAG, "Error", e);
                mHandler.sendEmptyMessageDelayed(MainActivity.ERROR, 2000);

            } finally {
                Log.d(TAG, "closing socket after readline");
                if (socket.isClosed() == false) {
                    out.flush();
                    out.close();
                    in.close();
                    socket.close();
                }

                //mHandler.sendEmptyMessageDelayed(MainActivity.SENT, 3000);
                Log.d(TAG, "Socket Closed");
            }

        } catch (Exception e) {

            Log.d(TAG, "Error", e);
            mHandler.sendEmptyMessageDelayed(MainActivity.ERROR, 2000);

        }

    }

    /**
     * Method for checking if TCPClient is running.
     * @return true if is running, false if is not running
     */
    public boolean isRunning() {
        return mRun;
    }

    /**
     * Callback Interface for sending received messages to 'onPublishProgress' method in AsyncTask.
     *
     */
    public interface MessageCallback {
        /**
         * Method overriden in AsyncTask 'doInBackground' method while creating the TCPClient object.
         * @param message Received message from server app.
         */
        public void callbackMessageReceiver(String message);
    }
}


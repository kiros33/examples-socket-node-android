package com.kiros33.app3;

/*
Handler 방식과 비교 할 수 있도록 만든
콜백 인터페이스
 */
public interface MessageReceiveCallbackInterface {
    void onReceived(String msg);
}

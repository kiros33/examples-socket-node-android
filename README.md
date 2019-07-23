# Node ↔ Android 소켓 통신 예제

## 조건

### Client
 1) Andoird 구성
 2) Java로 소스 개발
 3) AsyncTask 클래스 활용
### Server
  1) Node.js 기반
  2) net 패키지 사용
  
### 개발환경
* macOS Mojave 10.14.5
* node v12.6.0
* Android Studio 3.4.2
* AVD 기반 테스트 with HAXM

## 테스트 방법

### 서버
```
$ cd node
$ node git:(master) node srv.js
linsteing on 5000..
```

<img width="356" alt="server-001" src="https://user-images.githubusercontent.com/1563133/61701346-f505f580-ad78-11e9-97e2-72a7096a440d.png">

### 클라이언트

1) 실행
1) 연결하면서 서버에 전송할 메시지를 세번째 텍스트 박스에 입력, 미입력시 "default message" 사용
1) server ip 와 port 를 입력하고 connect 버튼을 클릭
1) 서버에 전송할 메시지를 세번째 텍스트 박스에 입력, 미입력시 "default message" 사용
1) send 버튼을 눌러 메시지 전송
1) disconnect를 눌러 연결 정료

<img width="490" alt="client-001" src="https://user-images.githubusercontent.com/1563133/61701257-d43da000-ad78-11e9-8c54-11261babd3fc.png">

### 출력정보
* 명령 관련 정보는 Output에 출력
* 서버에서 전달된 메시지는 Received Message에 출력

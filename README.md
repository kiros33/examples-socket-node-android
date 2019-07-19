# examples-socket-node-android

## Node ↔ Android 소켓 통신 예제

### Virtualbox
  * 설정

| 1 | 2 |
| --- | --- |
| 1 | 2 |

        <img width="400" alt="General - Basic" src="https://user-images.githubusercontent.com/1563133/61503833-2a76b000-aa14-11e9-9414-1fddeb627672.png">
        <img width="400" alt="General - Advanced" src="https://user-images.githubusercontent.com/1563133/61503834-2a76b000-aa14-11e9-8764-ee06881240bd.png">
        <img width="400" alt="General - Description" src="https://user-images.githubusercontent.com/1563133/61503835-2a76b000-aa14-11e9-8294-a4caf97729e1.png">
        <img width="400" alt="General - Disk Encryption" src="https://user-images.githubusercontent.com/1563133/61503836-2b0f4680-aa14-11e9-997d-e8be29b5b227.png">
        <img width="400" alt="System - Motherboard" src="https://user-images.githubusercontent.com/1563133/61503837-2b0f4680-aa14-11e9-8904-b7f32e53fdaf.png">
        <img width="400" alt="System - Processor" src="https://user-images.githubusercontent.com/1563133/61503838-2b0f4680-aa14-11e9-8014-2090632d5ba1.png">
        <img width="400" alt="System - Acceleration" src="https://user-images.githubusercontent.com/1563133/61503839-2ba7dd00-aa14-11e9-8e63-08af6e5c6397.png">
        <img width="400" alt="Display - Screen" src="https://user-images.githubusercontent.com/1563133/61503840-2ba7dd00-aa14-11e9-9074-779f0bf28200.png">
        <img width="400" alt="Display - Remote Display" src="https://user-images.githubusercontent.com/1563133/61503841-2ba7dd00-aa14-11e9-8e2f-a1b89402a301.png">
        <img width="400" alt="Display - Recording" src="https://user-images.githubusercontent.com/1563133/61503843-2ba7dd00-aa14-11e9-9102-858c8c17e063.png">
        <img width="400" alt="Storage - Hard Disk" src="https://user-images.githubusercontent.com/1563133/61503844-2c407380-aa14-11e9-8ff2-ea625de8a898.png">
        <img width="400" alt="Storage - Installation DVD" src="https://user-images.githubusercontent.com/1563133/61503845-2c407380-aa14-11e9-96f7-8725ca6169e4.png">
        <img width="400" alt="Audio" src="https://user-images.githubusercontent.com/1563133/61503846-2c407380-aa14-11e9-9d42-70a8e28a060d.png">
        <img width="400" alt="Network - Adapter 1" src="https://user-images.githubusercontent.com/1563133/61503847-2cd90a00-aa14-11e9-8efb-cbe9406d5aaa.png">
        <img width="400" alt="Ports" src="https://user-images.githubusercontent.com/1563133/61503848-2cd90a00-aa14-11e9-9ad7-ce99a4fa5802.png">
        <img width="400" alt="Shared Folders" src="https://user-images.githubusercontent.com/1563133/61503849-2cd90a00-aa14-11e9-8dca-eda2f7e46763.png">
        <img width="400" alt="User Interface" src="https://user-images.githubusercontent.com/1563133/61503850-2cd90a00-aa14-11e9-852e-2589aa6b4cce.png">

### OS

1. Download ISO
  * Download, go to [Offcial page](https://www.centos.org/download/)
  * ISO - [CentOS-7-x86_64-DVD-1810.iso](http://mirror.navercorp.com/centos/7.6.1810/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso)

### Android Studio

1. Download compressed file
  * Download, go to [Official page]( https://developer.android.com/studio/#downloads)
  * for Linux
    - [android-studio-ide-183.5692245-linux.tar.gz](https://dl.google.com/dl/android/studio/ide-zips/3.4.2.0/android-studio-ide-183.5692245-linux.tar.gz)
    - [sdk-tools-linux-4333796.zip](https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip)

### Chrome

1. 저장소 파일 생성
```
sudo vi /etc/yum.repos.d/google-chrome.repo
```

2. 저장소 내용 추가
```
[google-chrome]
name=google-chrome
baseurl=http://dl.google.com/linux/chrome/rpm/stable/$basearch
enabled=1
gpgcheck=1
gpgkey=https://dl-ssl.google.com/linux/linux_signing_key.pub
```

3. 설치
```
sudo yum install google-chrome-stable
```

### Visual Studio Code
1. key 설치
```
sudo rpm --import https://packages.microsoft.com/keys/microsoft.asc
```

2. 저장소 파일 추가
```
sudo sh -c 'echo -e "[code]\nname=Visual Studio Code\nbaseurl=https://packages.microsoft.com/yumrepos/vscode\nenabled=1\ngpgcheck=1\ngpgkey=https://packages.microsoft.com/keys/microsoft.asc" > /etc/yum.repos.d/microsoft-vscode.repo'
```

3. 설치
```
yum check-update
sudo yum install code
```

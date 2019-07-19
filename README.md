# examples-socket-node-android

## Node ↔ Android 소켓 통신 예제

### Virtualbox
  * 설정
      - General - Basic
<img width="400" alt="VirtualBox-Settings-001" src="https://user-images.githubusercontent.com/1563133/61503833-2a76b000-aa14-11e9-9414-1fddeb627672.png">
      - General - Advanced
<img width="400" alt="VirtualBox-Settings-002" src="https://user-images.githubusercontent.com/1563133/61503834-2a76b000-aa14-11e9-8764-ee06881240bd.png">
      - General - Description
<img width="400" alt="VirtualBox-Settings-003" src="https://user-images.githubusercontent.com/1563133/61503835-2a76b000-aa14-11e9-8294-a4caf97729e1.png">
      - General - Disk Encryption
<img width="400" alt="VirtualBox-Settings-004" src="https://user-images.githubusercontent.com/1563133/61503836-2b0f4680-aa14-11e9-997d-e8be29b5b227.png">
      - System - Motherboard
<img width="400" alt="VirtualBox-Settings-005" src="https://user-images.githubusercontent.com/1563133/61503837-2b0f4680-aa14-11e9-8904-b7f32e53fdaf.png">
      - System - Processor
<img width="400" alt="VirtualBox-Settings-006" src="https://user-images.githubusercontent.com/1563133/61503838-2b0f4680-aa14-11e9-8014-2090632d5ba1.png">
      - System - Acceleration
<img width="400" alt="VirtualBox-Settings-007" src="https://user-images.githubusercontent.com/1563133/61503839-2ba7dd00-aa14-11e9-8e63-08af6e5c6397.png">
      - Display - Screen
<img width="400" alt="VirtualBox-Settings-008" src="https://user-images.githubusercontent.com/1563133/61503840-2ba7dd00-aa14-11e9-9074-779f0bf28200.png">
      - Display - Remote Display
<img width="400" alt="VirtualBox-Settings-009" src="https://user-images.githubusercontent.com/1563133/61503841-2ba7dd00-aa14-11e9-8e2f-a1b89402a301.png">
      - Display - Recording
<img width="400" alt="VirtualBox-Settings-010" src="https://user-images.githubusercontent.com/1563133/61503843-2ba7dd00-aa14-11e9-9102-858c8c17e063.png">
      - Storage - Hard Disk
<img width="400" alt="VirtualBox-Settings-011" src="https://user-images.githubusercontent.com/1563133/61503844-2c407380-aa14-11e9-8ff2-ea625de8a898.png">
      - Storage - Installation DVD
<img width="400" alt="VirtualBox-Settings-012" src="https://user-images.githubusercontent.com/1563133/61503845-2c407380-aa14-11e9-96f7-8725ca6169e4.png">
      - Audio
<img width="400" alt="VirtualBox-Settings-013" src="https://user-images.githubusercontent.com/1563133/61503846-2c407380-aa14-11e9-9d42-70a8e28a060d.png">
      - Network - Adapter 1
<img width="400" alt="VirtualBox-Settings-014" src="https://user-images.githubusercontent.com/1563133/61503847-2cd90a00-aa14-11e9-8efb-cbe9406d5aaa.png">
      - Ports
<img width="400" alt="VirtualBox-Settings-015" src="https://user-images.githubusercontent.com/1563133/61503848-2cd90a00-aa14-11e9-9ad7-ce99a4fa5802.png">
      - Shared Folders
<img width="400" alt="VirtualBox-Settings-016" src="https://user-images.githubusercontent.com/1563133/61503849-2cd90a00-aa14-11e9-8dca-eda2f7e46763.png">
      - User Interface
<img width="400" alt="VirtualBox-Settings-017" src="https://user-images.githubusercontent.com/1563133/61503850-2cd90a00-aa14-11e9-852e-2589aa6b4cce.png">

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

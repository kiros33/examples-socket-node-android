# examples-socket-node-android

## Node ↔ Android 소켓 통신 예제

### Virtualbox
  * 설정: [상세보기](https://github.com/kiros33/examples-socket-node-android/wiki/Virtualbox-Settings)

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

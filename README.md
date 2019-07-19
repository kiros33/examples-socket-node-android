# examples-socket-node-android

## Node ↔ Android 소켓 통신 예제

### Virtualbox
  * 설정: [상세보기](https://github.com/kiros33/examples-socket-node-android/wiki/Virtualbox-Settings)

### OS

1. Download ISO
  * Download, go to [Offcial page](https://www.centos.org/download/)
  * ISO - [CentOS-7-x86_64-DVD-1810.iso](http://mirror.navercorp.com/centos/7.6.1810/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso)

```
sudo nmcli d

vi /etc/sysconfig/network-scripts/ifcfg-enp0s3

onboot=yes

sudo vi /etc/resolv.conf
nameserver 8.8.8.8
nameserver 8.8.4.4

sudo systemctl restart network

ip address
```

```
sudo yum check-update
sudo yum update

sudo yum groupinstall "GNOME Desktop" "Graphical Administration Tools" "Development Tools"
sudo yum install kernel-devel
sudo ln -sf /lib/systemd/system/runlevel5.target /etc/systemd/system/default.target
sudo reboot

```

```
-bash: warning: setlocale: LC_CTYPE: cannot change locale (UTF-8): 그런 파일이나 디렉터리가 없습니다
-bash: warning: setlocale: LC_CTYPE: cannot change locale (UTF-8): No such file or directory

  1) 편집기 실행: sudo vi /etc/environment
  2) 설정 추가:
    export LANG=en_US.UTF-8
    export LC_ALL=en_US.UTF-8

ssh-keygen -t rsa

chmod 700 ~/.ssh
touch ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
vi ~/.ssh/authorized_keys

```

* GNOME 해상도 변경
* Virtualbox Guest Additions 설치

* sudo yum install p7zip*
* 
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
sudo sh -c 'echo -e "[vscode]\nname=Visual Studio Code\nbaseurl=https://packages.microsoft.com/yumrepos/vscode\nenabled=1\ngpgcheck=1\ngpgkey=https://packages.microsoft.com/keys/microsoft.asc" > /etc/yum.repos.d/microsoft-vscode.repo'
```

3. 설치
```
yum check-update
sudo yum install code
```

* Font를 "D2Coding"으로 변경

### Git

```
sudo yum remove git*
sudo yum -y install  https://centos7.iuscommunity.org/ius-release.rpm
sudo yum -y install  git2u-all
```

```
git config --global user.name "Sangyong Bae"
git config --global user.email kiros33@gmail.com

git config --global credential.helper 'store --file ~/.git-credentials' 
``

* github에 SSH 공개키 등록

### Oh My Zsh
* Web: https://github.com/robbyrussell/oh-my-zsh
* Command: `sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"`

### alias-tips
* git clone https://github.com/djui/alias-tips.git ${ZSH_CUSTOM:-${ZSH}/custom}/plugins/alias-tips

### zsh-syntax-highlighting
* git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-${ZSH}/custom}/plugins/zsh-syntax-highlighting

### zsh-autosuggestions
* git clone https://github.com/zsh-users/zsh-autosuggestions.git ${ZSH_CUSTOM:-${ZSH}/custom}/plugins/zsh-autosuggestions

### tmux 설치
* sudo yum -y install tmux

### plugin 설정
```
git
alias-tips
tmux
zsh-syntax-highlighting
zsh-autosuggestions
```
### Android Studio

1. Download compressed file
  * Download, go to [Official page]( https://developer.android.com/studio/#downloads)
  * for Linux
    - [android-studio-ide-183.5692245-linux.tar.gz](https://dl.google.com/dl/android/studio/ide-zips/3.4.2.0/android-studio-ide-183.5692245-linux.tar.gz)
    - [sdk-tools-linux-4333796.zip](https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip)


```
cat <<EOF | sudo tee /usr/local/share/applications/android-studio.desktop
[Desktop Entry]
Type=Application
Name=Android Studio
Icon=/opt/android-studio/bin/studio.png
Exec=/opt/android-studio/bin/studio.sh
Terminal=false
Categories=Development;IDE;
EOF
```

var net = require('net');

// 서버 5000번 포트로 접속
//var socket = net.connect({port : 5000});
//var socket = net.connect({port : 5000, host : "192.168.25.7"});
//var socket = net.connect({port : 21, host : "211.111.172.251"});
var socket = net.connect({port : 5000, host : "211.111.172.80"});
socket.on('connect', function(){
  console.log('connected to server!');
  
  // 1000ms의 간격으로 banana hong을 서버로 요청
  setInterval(function(){
    socket.write('banana hong!');
  }, 2000);
});

// 서버로부터 받은 데이터를 화면에 출력
socket.on('data', function(chunk){
  console.log('recv:' + chunk);
});
// 접속이 종료됬을때 메시지 출력
socket.on('end', function(){
  console.log('disconnected.');
});
// 에러가 발생할때 에러메시지 화면에 출력
socket.on('error', function(err){
  console.log(err);
});
// connection에서 timeout이 발생하면 메시지 출력
socket.on('timeout', function(){
  console.log('connection timeout.');
});

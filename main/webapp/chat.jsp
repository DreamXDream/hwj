<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'socket.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="resource/bootstrap.css" rel="stylesheet" type="text/css"
          media="all" />
    <script type="text/javascript" src="resource/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="resource/bootstrap.js"></script>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

    <style type="text/css">
        input#chat {
            width: 600px
        }
    body,html{

        background-image: url("images/chat.jpeg");
    }
        #console-container {
            margin-left: 400px;
            width: 700px;
            height:400px;

        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 170px;
            overflow-y: scroll;
            padding: 5px;
            height:400px;
            width: 100%;
        }
        #console img{
             display: inline-block;
            width:35px;
            height: 35px;
            border-radius:50%;
            margin-top: -35px;
        }
        #console div{
            margin-top: 15px;
            font-size:25px;
            color:white;
            width:400px;
            border-radius:25px;
            margin-left: 50px;

            text-align: left;
        text-indent: 20px;

        }

    </style>
    <script type="application/javascript">
        "use strict";

        var Chat = {};

        Chat.socket = null;

        Chat.connect = (function(host) {
            if ('WebSocket' in window) {
                Chat.socket = new WebSocket(host);
            } else if ('MozWebSocket' in window) {
                Chat.socket = new MozWebSocket(host);
            } else {
                Console.log('Error: 浏览器不支持WebSocket');
                return;
            }

            Chat.socket.onopen = function () {

                document.getElementById('chat').onkeydown = function(event) {
                    if (event.keyCode == 13) {
                        Chat.sendMessage();
                    }
                };
            };

            Chat.socket.onclose = function () {
                document.getElementById('chat').onkeydown = null;
                Console.log('Info: webcocket关闭.');
            };

            Chat.socket.onmessage = function (message) {
                Console.log(message.data);
            };
        });

            var username="<%=session.getAttribute("loginname")%>";



        Chat.initialize = function() {
            if (window.location.protocol == 'http:') {
                Chat.connect('ws://' + window.location.host + '/xuenian/chat/'+username);
            } else {
                Chat.connect('wss://' + window.location.host + '/xuenian/chat/'+username);
            }
        };

        Chat.sendMessage = (function() {
            var message = document.getElementById('chat').value+"-"+username;
            if (message != '') {

                Chat.socket.send(message);
                document.getElementById('chat').value = '';
            }
        });

        var Console = {};

        Console.log = (function(message) {
            var console = document.getElementById('console');
            var p = document.createElement('div');
            var img = document.createElement('img');

         var length=username.length;



            function getCaption(obj){
                var index=obj.lastIndexOf("\-");
                obj=obj.substring(index+1,obj.length);
                return obj;
            }
            var username1= getCaption(message);
            var imgsrc;
            $
                .ajax({
                    url : "User/getheadimg1.do",
                    type : 'post',
                    async : false,
                    data:{
                        "username1":username1
                    },
                    success : function(data) {
                        imgsrc=data;
                    },
                    error:function (data) {

                    }
                });
            img.src=imgsrc;
            p.id="message";
            p.style.backgroundColor="blue";
            p.style.wordWrap = 'break-word';
            var  s= message.lastIndexOf('-');

            if(s==-1){

                p.innerHTML = message;
                console.appendChild(p);
            }
            else {

                message = message.substring(0, message.lastIndexOf('-'));
                p.innerHTML = message;
                console.appendChild(p);
                console.appendChild(img);
            }


            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        });

        Chat.initialize();

        document.addEventListener("DOMContentLoaded", function() {

            var noscripts = document.getElementsByClassName("noscript");
            for (var i = 0; i < noscripts.length; i++) {
                noscripts[i].parentNode.removeChild(noscripts[i]);
            }
        }, false);
    </script>

</head>
<body>
<div class="noscript"><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></div>
<div>

    <div id="console-container">
    <div id="console">


    </div>
</div>

    <div class="container " style="margin-left: 400px;">
        <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
            <input type="text" id="chat" class="form-control">
        </div><!-- /input-group -->
        注意：输入  消息to用户名   发送给指定用户   比如：  你好to用户1<br>
        输入   消息     直接发送给全体用户
    </div>
</div>
</body>
</html>

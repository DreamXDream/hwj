<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, shrink-to-fit=no">
    <title>腾讯云视频点播示例</title>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/jquery-1.9.1.js"></script>
    <script src="resource/bootstrap.js"/></script>

    <link href="//imgcache.qq.com/open/qcloud/video/tcplayer/tcplayer.css" rel="stylesheet">

        <script src="//imgcache.qq.com/open/qcloud/video/tcplayer/ie8/videojs-ie8.js"></script>

    <!-- 如果需要在 Chrome Firefox 等现代浏览器中通过H5播放hls，需要引入 hls.js -->
    <script src="//imgcache.qq.com/open/qcloud/video/tcplayer/lib/hls.min.0.8.8.js"></script>
    <!-- 引入播放器 js 文件 -->
    <script src="//imgcache.qq.com/open/qcloud/video/tcplayer/tcplayer.min.js"></script>
    <!-- 示例 CSS 样式可自行删除 -->
</head>
<style>
    html, body {
        font-family: 微软雅黑;

        height: 100%;
        background-color: black;
    }

    #liuyan {
        background-image: url(images/star.jpeg);
        background-repeat: no-repeat;
        background-size: 100% 100%;
        -moz-background-size: 100% 100%;
    }

    #collect {
        color: white;
    }

    #collect:hover {
        cursor: pointer;
        color: red;
    }

    #append {
        color: white;
    }

    #append:hover {
        cursor: pointer;
        color: red;
    }

    #download {
        color: white;
    }

    #download:hover {
        cursor: pointer;
        color: red;
    }

    #video-list h1 {
        font-size: 20px;
    }

    #video-list div {
        border: 1px grey solid;
        margin-left: 15px;
    }

    .row div {
        margin-top: 15px;
    }

    .row {
        margin-top: 15px;
    }

    #top div a {
        text-decoration: none;
        font-size: 1.3em;
    }

    #top div {
        margin-top: 15px;
        margin-bottom: 15px;
    }

    #video-list div:hover {
        border: 1px blue solid;
        cursor: pointer
    }


    input#chat {
        width: 600px
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
<body>


<div class="col-md-12 container">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed"
                        data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span> <span
                        class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a id="load" class="navbar-brand" href="#"></a>
            </div>
            <input id="session" type="hidden" value="">
            <input id="videoid" type="hidden" value="">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li id="jumphome" class="active"><a>首页 <span
                            class="sr-only">(current)</span></a></li>
                    <li id="jumplesson"><a>课程</a></li>
                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false">帮助 <span class="caret"></span></a>
                        <ul class="dropdown-menu">

                            <li id="login"><a href="#">登入</a></li>
                            <li role="separator" class="divider"></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">技术协助</a></li>
                        </ul>
                    </li>
                </ul>
                <ul style="margin-right: 40px" class=" nav navbar-nav navbar-right">


                    <li class="dropdown"><a href="#" class="dropdown-toggle"
                                            data-toggle="dropdown" role="button" aria-haspopup="true"
                                            aria-expanded="false"> <img id="headimg"
                                                                        style="width: 40px; height: 40px; margin-top: -10px;"
                                                                        class=" img-circle pull-right"
                                                                        src="images/alien.jpg"> </span></a>
                        <ul class="dropdown-menu">

                            <li id="personmessage"><a href="#">个人信息</a></li>
                            <li role="separator" class="divider"></li>
                            <li id="exit"><a href="#">退出登入</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">技术协助</a></li>
                        </ul>
                    </li>


                </ul>

                <form class=" navbar-form navbar-right ">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<br>
<br>

<div style="margin-top: 40px; width: 100%; background-color: black;"
     class="container">

    <div class="container ">

        <video style="margin-left: 60px;" id="player-container-id" preload="auto" width="900" height="480" playsinline
               webkit-playinline x5-playinline></video>

    </div>


    <div style="height: 35px;" class="container col-md-10 col-md-offset-2 ">
			<span id="collect" class="col-md-1 glyphicon glyphicon-heart "
                  style="margin-top: 5px; font-size: 20px;"></span> <span id="append"
                                                                          class=" col-md-1 glyphicon glyphicon-plus"
                                                                          style="margin-top: 5px; font-size: 20px;"></span>
        <span
                id="download" class="col-md-1 glyphicon glyphicon-film"
                style="margin-top: 5px; font-size: 20px;"></span>

    </div>

</div>
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

<div id="liuyan" class="container "
     style="background-color: black; width: 100%;">

    <div>
        <center>
            <div id="leavemessage" style="width: 70%;" class="form-group ">
                <div class="text-center" style="font-size: 25px;">留言内容</div>


                <textarea id="message" style="margin-top: 30px;"
                          class="form-control " rows="6" cols="20"></textarea>

            </div>
            <button type="button" id="sendmessage"
                    class="btn btn-default text-center">提交
            </button>
        </center>
    </div>

    <div style="margin-top: 40px;" class="container" id="leavemessagefind">

    </div>

    <div class="text-center btn-group btn-group-justified">
        <button style="width: 30%;"
                class="btn btn-default btn-block text-center">查看更多
        </button>
    </div>
</div>


</body>
<script>
    window.onload = function () {

        $("#jumphome").click(function () {
            window.location.href = "home1.html"
        });
        $("#jumplesson").click(function () {
            window.location.href = "lesson.html"
        });
        var Ocollect = document.getElementById('collect');
        var session;
        var videoid;
        //获取登入名
        $.ajax({
            url: "User/cookies1.do",
            type: "post",
            success: function (data) {
                document.getElementById("session").value = data;
                document.getElementById("session").html = data;
                session = data;

                $("#load").html("欢迎您!" + data);
                $
                    .ajax({
                        url: "User/getheadimg.do",
                        type: 'post',
                        async: false,
                        success: function (data) {

                            $("#headimg").attr("src", data);

                        },
                        error: function (data) {
                            alert("erroe!");

                        }
                    });
            },
            error: function (data) {
                alert("error");
            }
        });

        $.ajax({
            url: "User/getvideoid.do",
            type: "post",
            success: function (data) {
                videoid = data;
                document.getElementById("videoid").value = data;
                var player = TCPlayer("player-container-id", { // player-container-id 为播放器容器ID，必须与html中一致


                    fileID: data, // 请传入需要播放的视频filID 必须
                    appID: "1256510878", // 请传入点播账号的appID 必须
                    autoplay: false //是否自动播放
                    //其他参数请在开发文档中查看
                });
            },
            error: function (data) {
                alert("error");
            }
        });


        $("#collect").click(function () {
            if (session == 'null' || session == "") {//!=不为字符串判断
                alert("未登入！");
            }
            else {
                $
                    .ajax({
                        url: 'User/collect.do',
                        type: "post",
                        data: {
                            "session": session,
                            "videoid": videoid
                        },
                        async: false,
                        success: function (data) {
                            if (data == 'Yes') {
                                alert("收藏成功!");

                            } else if (data == "No") {
                                alert("收藏过了！");
                            }
                        }
                    });
            }
        });

//留言
        var osendmessage = document.getElementById('sendmessage');
        var opersonmessage = document.getElementById('personmessage');
        var ologin = document.getElementById('login');
        osendmessage.onclick = function () {

            if (session == "null" || session == "") {
                alert("先登入才能留言哦！");
            } else {

                var message = $("#message").val();
                if(message==""){
                    alert("留言不能为空！");
                }
                $
                    .ajax({
                        url: 'User/leavemessage.do',
                        type: "post",
                        data: {
                            "session": session,
                            "videoid": videoid,
                            "message": message

                        },
                        async: false,
                        success: function (data) {
                            if (data == 'Yes') {
                                alert("留言成功!");
                                window.location.href = "video1.html";
                            }
                        }
                    });
            }
        };

        //留言板显示

        $
            .ajax({
                url: 'User/findleavemessage.do',
                type: "post",
                dataType: "json",
                async: false,
                success: function (data) {

                    var str2;
                    $
                        .ajax({
                            url: 'User/findrepeatmessage.do',
                            type: "post",
                            dataType: "json",
                            async: false,
                            success: function (data1) {

                                for (var i = 0; i < data.length; i++) {
                                    str2="";
                                    for(var j=0;j<data1.length;j++){
                                        if(data[i]['id']==data1[j]['id']){

                                            str2 =str2+
                                                "<div style='margin-top: 15px;'><img class='img-circle' style='width:35px;height: 35px;' src='" + data1[j]['img'] + "'>"
                                                + data1[j]['username']+ "&nbsp;回复"+data1[j]['nickname']+":"+"<p style='display: inline-block;'>" + data1[j]['content'] + "</p>"
                                                + "<a  style='cursor:pointer;display: inline-block;margin-left: 15px;'  onclick='messaged(this," + data1[j]['username'] + "," +  document.getElementById("session").value + ");'  style='width:40px;color: blue;cursor: pointer;'>回复</a>"+

                                                "<div class='container'style='display: none;'><textarea  style='width:800px; margin-top: 30px;'" +
                                                "class='form-control' rows='4' cols='15'></textarea><button   onclick='repeatmessaged(this,"+data[i]['id']+")' type='button' class=' btn btn-info '>发表</button></div>" +"</div>";

                                        }


                                    }
                                    str = "<div class='media' ><div class='media-left'><img class='img-circle' style='width:80px;height:80px;' src='" + data[i]['img'] + "' >"
                                        + " <h4  style='color: white;margin-left: 27px;'  class='media-heading'>" + data[i]['username'] + "</div><div  style='color: white;'class='media-body'>"
                                        + "</h4><p style='margin-top: 15px;'>"
                                        + data[i]['message']
                                        + "</p><p>"
                                        + data[i]['time']
                                        //"+data[i]['id']+ "
                                        + "发表</p><p   onclick='message(this," + data[i]['id'] + "," + data[i]['username'] + ");'  style='width:40px;color: blue;cursor: pointer;'>回复</p><div class='container'style='display: none;'><textarea  style='width:800px; margin-top: 30px;'" +
                                        "class='form-control' rows='4' cols='15'></textarea><button   onclick='repeatmessage(this,"+data[i]['id']+")' type='button' class=' btn btn-info '>发表</button> </div><div class='container'>"+   str2+"<hr></div></div>";
                                    $("#leavemessagefind").append(str);

                                }

                            }
                        });
                }
            });

        opersonmessage.onclick = function () {
            if (session == 'null' || session == "") {
                alert("你还没登录哦!");
                location.reload();
            } else {
                window.location.href = "person.html";
            }
        };


        ologin.onclick = function () {

            if (session != 'null' || session != "") {//!=不为字符串判断
                alert('已登录');
            }

            else {
                window.location.href = "resource/login.html";
            }
        };


    };
    var reusername;
    var reusername1;
    function messaged(e,s,b){
        reusername1 = s;
        if ($(e).next().css('display') == 'block') {

            $(e).next().css('display', 'none');
        }
        else {
            $(e).next().css('display', 'block');
        }

    }



    function message(e, s, b) {
        reusername = b;
        if ($(e).next().css('display') == 'block') {

            $(e).next().css('display', 'none');
        }
        else {
            $(e).next().css('display', 'block');
        }
    }

    function repeatmessaged(e,id){

        $.ajax({
            url: 'User/repeatmessaged.do',
            type: "post",
            data: {
                "session": document.getElementById("session").value,
                "repeatmessage": $(e).prev().val(),
                "videoid": document.getElementById("videoid").value,
                "reusername": reusername1,
                "id":id
            },
            async: false,
            success: function (data) {
                alert("回复成功!");
            }
        });
    }


    function repeatmessage(e,id) {


        $.ajax({
            url: 'User/repeatmessage.do',
            type: "post",
            data: {
                "session": document.getElementById("session").value,
                "repeatmessage": $(e).prev().val(),
                "videoid": document.getElementById("videoid").value,
                "reusername": reusername,
                "id":id
            },
            async: false,
            success: function (data) {

                alert("回复成功!");

            }
        });

    }


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
</html>
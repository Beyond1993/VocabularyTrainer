<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/websocket.js"></script>
<style type="text/css">
	#console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 170px;
            overflow-y: scroll;
            padding: 5px;
            width: 100%;
        }
</style>
</head>
<body>

<%
	response.setHeader("Cache-Control","no-cache");
    response.setHeader("Cache-Control","no-store");
    response.setDateHeader("Expires", -1);
    response.setHeader("Pragma","no-cache");

    String username = (String)session.getAttribute("username");
	if(username == null){
		response.sendRedirect("login.jsp");
	}else{
		//response.sendRedirect("login.jsp");
	}
%>



<div >
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="#">Home</a>
		</li>
		<li>
			<a href="#">Deck</a>
		</li>
		<li class="disabled">
			<a href="#">Information</a>
		</li>
		<li class="dropdown pull-right">

			 <a href="#" data-toggle="dropdown" class="dropdown-toggle"><%=session.getAttribute("username")%><strong class="caret"></strong></a>
			<ul class="dropdown-menu">
				<li>
					<a href="/VocabularyTrainer/loginOut">logout</a>
				</li>

			</ul>
		</li>
	</ul>
</div>


<div  style="margin-bottom: 15%;margin-top:10%; ">
	<p id="word" style="margin-left: 40%; font-size:12em">好</p>

<div  id="hint" style="margin-top:  10%">
	<p style="margin-left: 40% ; display:inline"> &nbsp this is help infomation :)</p>
</div>
<hr style="border:1px dashed #987cb9" width="100%" color=#987cb9 SIZE=1>

<div id="answer">
	<p style="margin-left: 40% ; display:inline"> answer</p>
</div>
<div  style="margin-left: 2%;margin-bottom: 1%">
<button   class="btn" id="connect" onclick="connect();">start</button> <span><button   class="btn" id="disconnect" onclick="disconnect();">end</button></span>

</div>
<div class="btn-group navbar-fixed-bottom" style=" margin-left:35% ; margin-top:10% ; margin-bottom: 10%">
	<input class="btn" type="submit" value="not know" onclick="echo(1)" id="message1"/>
	<input class="btn" type="submit" value="hard"     onclick="echo(2)" id="message2"/>
	<input class="btn" type="submit" value="normal"   onclick="echo(3)" id="message3"/>
	<input class="btn" type="submit" value="easy"     onclick="echo(4)" id="message4"/>
</div>

	<div class="span4" >
		<div id="console"/>

		<p>
			<a class="btn" href="#">see more »</a>
		</p>
	</div>
</body>
</html>

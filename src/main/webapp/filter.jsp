<%--
  Created by IntelliJ IDEA.
  User: wanye
  Date: 16-3-24
  Time: 下午3:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Cache-Control","no-store");  //和上面的参数不一样
    response.setDateHeader("Expires", -1);
    response.setHeader("Pragma","no-cache");
    String username = (String)session.getAttribute("username");
    out.println(username);
    if(username == null){
        out.println("error");
        //response.sendRedirect("login.jsp");
    }else{
        out.println("login");
        response.sendRedirect("index.jsp");
    }
%>
</body>
</html>

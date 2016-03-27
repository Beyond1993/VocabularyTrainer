<%@ page contentType="text/html; charset=UTF-8" %>
<%
	response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Cache-Control","no-store");
    response.setDateHeader("Expires", -1); 
    response.setHeader("Pragma","no-cache"); 

session.removeAttribute("username"); 

out.print("<script>window.location.href='index.jsp'</script>");
%> 
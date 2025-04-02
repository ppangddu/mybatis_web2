<%@page import="pack.business.DataDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="processDao" class="pack.business.ProcessDao" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>회원 정보(Mybatis)</h2>
<a href="ins.jsp">회원 추가</a>
<br>
<table border="1">
	<tr>
	<th>id</th><th>name</th><th>passwd</th><th>regdate</th>
	</tr>
	<%
		ArrayList<DataDto> list = (ArrayList<DataDto>)processDao.selectMemberAll();
	%>
	<c:set var="list" value="<%=list %>" />
	<c:if test="${empty list}">
	<tr>
	<td colspan="4">자료 없음</td>
	</tr>
	</c:if>
	<c:forEach var="mem" items="<%=list%>">
		<tr>
		<td><a href="del.jsp?id=${mem.id}">${mem.id}</a></td>
		<td><a href="up.jsp?id=${mem.id}">${mem.name}</a></td>
		<td>${mem.passwd}</td>
		<td>${fn:substring(mem.regdate, 0, 10)}</td>
		</tr>
	</c:forEach>
	<tr>
	<td colspan="4">id 클릭은 삭제, name 클릭은 수정</td>
	</tr>
</table>
</body>
</html>
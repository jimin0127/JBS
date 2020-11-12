<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO" %>
<%@ page import = "java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="user.User" scope="page"/> <!-- 객체 생성 -->
<%-- <jsp:setProperty name="user" property="userID"/> <!-- userID의 getter, setter 사용 가능 -->
<jsp:setProperty name="user" property="userPW"/>
<jsp:setProperty name="user" property="userName"/>
<jsp:setProperty name="user" property="userGender"/>
<jsp:setProperty name="user" property="userEmail"/> --%>

<jsp:setProperty name="user" property="*" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>jsp 게시판</title>
</head>
<body>	
	<% 
	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
	}


	if(userID != null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인이 되어 있습니다. ')");
		script.println("location.href = 'main.jsp'");
		script.println("</script>");
	}
	

		UserDAO userDAO = new UserDAO();//디비 연결 
		int result = userDAO.login(user.getUserID(), user.getUserPW());
		if(result == 1) { //로그인이 되면 main.jsp로 이동
			session.setAttribute("userID", user.getUserID());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		} else if (result == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다. ')");
			script.println("history.back()");
			script.println("</script>");
		} else if (result == -1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else if (result == -2) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
%>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>
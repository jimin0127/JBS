<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jbs.JbsDAO"%>
<%@ page import="jbs.Jbs"%>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("UTF-8"); %>


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
	
	
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인이 필요합니다. ')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		}
		
		int jbsID = 0;
		if(request.getParameter("jbsID") != null) {
			jbsID = Integer.parseInt(request.getParameter("jbsID"));
		}
		if(jbsID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다. ')");
			script.println("location.href = 'jbs.jsp'");
			script.println("</script>");
		}
		
		Jbs jbs = new JbsDAO().getJbs(jbsID);
		
		
		if(!userID.equals(jbs.getUserID())){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'jbs.jsp'");
			script.println("</script>");
		}else{
			JbsDAO jbsDAO= new JbsDAO();
			int result = jbsDAO.delete(jbsID);
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글 삭제에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}else{
				PrintWriter script = response.getWriter();
				script.println("<script>");				
				script.println("location.href='jbs.jsp'");
				script.println("</script>");
			}		
	}
	%>	


	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>
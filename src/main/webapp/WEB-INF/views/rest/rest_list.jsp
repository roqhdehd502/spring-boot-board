<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MY REST BOARD: LIST</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="jumbotron jumbotron-fluid">
  		<h1 align="center">
  			
  			<span><img src="<c:url value="/img/myboard_logo.png"/>" width="112" height="28"></span>
  			<span>MY BOARD</span>
  		</h1>
	</div>

	<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
		<h1 align="center">BOARD LIST</h1>		
	</nav>
	
	<div>
		<button type="button" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}/restful/board/write'">
				글쓰기
		</button>	
	</div>

	<table class="table table-hover">
		<tr>
			<th>번호</th>
			<th>글쓴이</th>
			<th>제목</th>
			<th>날짜</th>
			<th>조회수</th>
		</tr>
			
		<c:forEach items="${list}" var="dto">
        	<tr>
            	<td>${dto.bId}</td>
				<td>${dto.bName}</td>
				<td>
					<c:forEach begin="1" end="${dto.bIndent}">[Re]</c:forEach>
					<a href="${pageContext.request.contextPath}/restful/board/${dto.bId}">${dto.bTitle}</a></td>
				<td>${dto.bDate}</td>
				<td>${dto.bHit}</td>
       	   </tr>
       </c:forEach>  	   
	</table>
	
	<div class="container" align="center"><!--  -->
		<ul class="pagination">
			<c:if test="${pageMaker.prev}">
				<li class="page-item">
					<a href="board${pageMaker.makeQuery(pageMaker.startPage - 1)}">이전</a>
				</li>
			</c:if>
		
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
				<c:out value="${pageMaker.cri.pageNum == idx?'':''}" />
				<li class="page-item">
					<a href="board${pageMaker.makeQuery(idx)}">${idx}</a>
				</li>
			</c:forEach>
				
			<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
				<li class="page-item">
				 	<a href="board${pageMaker.makeQuery(pageMaker.endPage +1)}">다음</a>
				</li>
			</c:if>
		</ul>
	</div>
	
	<div align="center">
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
    		검색
  		</button><br>
	</div>
	
	<div align="center" class="jumbotron jumbotron-fluid">
  		<p>Copyrights&copy;All rights reserved by bootstrap.
	</div>	
</body>
</html>
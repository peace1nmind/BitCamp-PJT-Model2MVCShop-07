<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="com.model2.mvc.service.TranCodeMapper"%>
<%@page import="java.util.Map"%>
<%@page import="com.model2.mvc.service.product.impl.ProductServiceImpl"%>
<%@page import="com.model2.mvc.service.product.ProductService"%>
<%@ page contentType="text/html; charset=EUC-KR" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	Map<String, String> tranCodeMap = (TranCodeMapper.getInstance()).getMap();
%>

<html>
	<head>
	
		<title>��� ��ǰ ����</title>
	
	</head>
	
	<body>
	
		<center><h2>[ �ֱ� �� ��ǰ ]</h2></center>
		
	<br>
	
	<%
		request.setCharacterEncoding("euc-kr");
		response.setCharacterEncoding("euc-kr");
		
		System.out.println("\n>> history.jsp");
		
		String history = null;
		Cookie[] cookies = request.getCookies();
	%>
	
	
		
	<%	if (cookies!=null && cookies.length > 0) { 
		
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
					System.out.println("\thistory= "+ ((history==null||history.equals(""))? "null" : history));
				}
			}
			
			if (history != null) { %>
					
		<center>
		<table border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th>No.</th>
			<th>��ǰ��</th>
			<th>����</th>
			<th style="width: 70px;">�������</th>
		</tr>			
			
	<%			String[] historys = (history.trim()).split("&");
				int no = 1;
				for (String h : historys) {
					if (no>5) break;
					System.out.println("\th= "+ ((h==null||h.equals(""))? "null" : h));
					if (!h.equals("null") && !h.equals("")) {
						ProductService service = new ProductServiceImpl();
						Product product = service.getProduct(Integer.parseInt(h));
						System.out.println("\tprodName= "+product.getProdName());
	%>
		<tr>
			<td><%= no++ %></td>
			<td><a href="/getProduct.do?prodNo=<%= h %>&menu=search"	target="rightFrame"><%= product.getProdName() %></a></td>
			<td><%= product.getPrice() %></td>
			<td><%= tranCodeMap.get(product.getProTranCode().trim()) %></td>
		</tr>
	<%
					}
				}
			} else { %>
				<p>�ֱ� ��ȸ�� ��ǰ�� �����ϴ�.</p>
				
	<%		} %>
	
		</table>
		</center>
	
	<%	} %>
	
	</body>
</html>

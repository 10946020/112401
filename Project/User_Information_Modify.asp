
<%

	Session("UserMail") = Request("UserMail")

'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
	
	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"


	SQL = "Select * From User_Information Where 使用者帳號 = '" & Session("UserMail") & "'"
	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3
	
	If rs.EOF Then
		Response.Write "您所查詢的使用者帳號不存在。"
		rs.Close
		Set rs = Nothing
		Set Conn = Nothing
		Response.End
	End If

'session.codepage="65001"
'Response.Write rs("使用者名稱")
session.codepage="950"
'response.write server.urlencode(rs("使用者名稱"))
%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>修改使用者</title>
</head>

<body>

<p align="center"><font size="4" face="標楷體"><b>Modify User Information</b></font></p>

	<form method="POST" action="User_Information_Modify_Save.asp">
		<table style="width: 60%" align="center">
			<tr>
				<td><font size="2">User ID</font></td>
				<td><font face="新細明體"><textarea rows="2" name="UserMail" cols="32"><%=rs("使用者帳號")%></textarea></font></td>
			</tr>
			<tr>
				<td><font SIZE="2">User Name</font></td>
				<td><font face="新細明體"><textarea rows="2" name="UserName" cols="32"><%=rs("使用者名稱")%></textarea></font></td>
			</tr>
			<tr>
				<td><font SIZE="2">User Password</font></td>
				<td><font face="新細明體"><textarea rows="2" name="UserPassword" cols="32"><%=rs("使用者密碼")%></textarea></font></td>
			</tr>
			<tr>
				<td><font SIZE="2">User Nickname</font></td>
				<td><font face="新細明體"><textarea rows="2" name="UserNickname" cols="32"><%=rs("使用者ID")%></textarea></font></td>
			</tr>
		</table>

		<p align="center"><input type="submit" value="Modify" name="B3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input type="reset" value="Clean" name="B4"></p>
	</form>
<%
	rs.Close
	Set rs = Nothing
	Set Conn = Nothing
%>
</body>

</html>
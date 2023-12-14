<%
'	Session("TestUrl") = Request.ServerVariables("URL")
	Session("ID") = Request("ID")

'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
	
	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"


	SQL = "Select * From Open_Device_Information Where ID = " & Session("ID")
	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3
	
	If rs.EOF Then
		Response.Write "您所查詢的ID不存在。"
		rs.Close
		Set rs = Nothing
		Set Conn = Nothing
		Response.End
	End If


%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>修改Open Device Information</title>
</head>

<body>

<p align="center"><font size="4" face="標楷體"><b>修改Open Device Information</b></font></p>

	<form method="POST" action="Open_Device_Information_Modify_Save.asp">
		<table style="width: 60%" align="center">
			<tr>
				<td><font size="2">User ID</font></td>
				<td><font face="新細明體"><textarea rows="2" name="UserID" cols="32"><%=rs("使用者ID")%></textarea></font></td>
			</tr>
			<tr>
				<td><font size="2">Device ID</font></td>
				<td><font face="新細明體"><textarea rows="2" name="DeviceID" cols="32"><%=rs("DeviceID")%></textarea></font></td></td>
			</tr>

		</table>
		<p align="center"><input type="submit" value="Modify 修改" name="B3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input type="reset" value="Clean 清除" name="B4"></p>
	</form>
<%
	rs.Close
	Set rs = Nothing
	Set Conn = Nothing
%>
</body>

</html>
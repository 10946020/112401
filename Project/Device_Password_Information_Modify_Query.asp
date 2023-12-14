<%
'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
'	'開啟資料庫
'	Set Conn = Server.CreateObject("ADODB.Connection")
'	Conn.Open "MESCP","albert","ftie"
%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>修改電子鎖密碼</title>
</head>

<body>

<p align="center"><font size="4" face="標楷體"><b>修改電子鎖密碼</b></font></p>

	<form method="POST" action="Device_Password_Information_Modify_QueryResult.asp">
		<table style="width: 40%" align="center">
			<tr>
				<td><font SIZE="2">Device ID</font></td>
				<td><font face="新細明體"><textarea rows="2" name="DeviceID" cols="32"></textarea></font></td>
			</tr>
			<tr>
				<td><font SIZE="2">解鎖密碼</font></td>
				<td><font face="新細明體"><textarea rows="2" name="DevicePWD" cols="32"></textarea></font></td>
			</tr>
			<tr>
				<td><font SIZE="2">設備額外使用ID</font></td>
				<td><font face="新細明體"><textarea rows="2" name="ExtraID" cols="32"></textarea></font></td>
			</tr>

		</table>
		<p align="center"><input type="submit" value="Query 查詢" name="B1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		<input type="reset" value="Clean 清除" name="B2"></p>
	</form>
<%
%>
</body>

</html>
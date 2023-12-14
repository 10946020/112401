<%
'	Session("TestUrl") = Request.ServerVariables("URL")
	DeviceID = Request("DeviceID")
		
'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%


	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"

	
	SQL = "Select * From Device_Information Where DeviceID = '" & DeviceID & "'"

	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3
	Set Field = rs.Fields
%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>刪除電子鎖</title>
</head>

<body>

<p>　</p>

<p align="center"><b><font size="5" face="標楷體">您確定要刪除此電子鎖嗎？</font></b></p>

<%
Response.Write "<table border='1' width='100%'>"
Response.Write "<tr>"
For I =0 to Field.Count -1
	Response.Write "<td width='3%' bgcolor='#0000FF'><font color='#FFFFFF' size='2'>" & Field(I).Name & "</font></td>"
Next
Response.Write "</tr>"

Do While Not rs.EOF
	Response.Write  "<tr>"
	For I =0 to Field.Count -1
		Response.Write  "<td width='3%'><font size='2'>" & Field(I).Value & "</font></td>"
	Next
	
	rs.Movenext
Loop
Response.Write "</table>"
rs.close
Set Conn = Nothing

%>


<p align="center"><font size="4"><a href="Device_Information_Delete_Save.asp?DeviceID=<%=DeviceID%>">
確定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<a href="Device_Information_Delete_Query.asp">後悔</a></font></p>

</body>

</html>

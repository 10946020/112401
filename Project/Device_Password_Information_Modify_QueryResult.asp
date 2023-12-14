<%
'	If LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
	Session("DeviceID") = Request("DeviceID")
	Session("DevicePWD") = Request("DevicePWD")
	Session("ExtraID") = Request("ExtraID")

	
	Dim NowTime
	
	If Datepart("m", Now) < 10 Then
		NowTime = Datepart("yyyy", Now) & "0" & Datepart("m", Now)
	Else
		NowTime = Datepart("yyyy", Now) & Datepart("m", Now)
	End If
	If Datepart("d", Now) < 10 Then
		NowTime = NowTime & "0" & Datepart("d", Now)
	Else
		NowTime = NowTime & Datepart("d", Now)
	End If
	If Datepart("h", Now) < 10 Then
		NowTime = NowTime & " 0" & Datepart("h", Now)
	Else
		NowTime = NowTime & " " & Datepart("h", Now)
	End If
	If Datepart("n", Now) < 10 Then
		NowTime = NowTime & "0" & Datepart("n", Now)
	Else
		NowTime = NowTime & Datepart("n", Now)
	End If
	If Datepart("s", Now) < 10 Then
		NowTime = NowTime & "0" & Datepart("s", Now)
	Else
		NowTime = NowTime & Datepart("s", Now)
	End If
	
	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"

	
	SQL = "Select * From Device_Password_Information Where 1 = 1"

	If Session("DeviceID") <> "" Then
		SQL = SQL + " And DeviceID Like '%" & Session("DeviceID") & "%'"
	End If

	If Session("DevicePWD") <> "" Then
		SQL = SQL + " And 解鎖密碼 Like '%" & Session("DevicePWD") & "%'"
	End If

	If Session("ExtraID") <> "" Then
		SQL = SQL + " And 設備額外使用ID Like '%" & Session("ExtraID") & "%'"
	End If

'	Response.Write SQL
'	Response.End

	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3
	Set Field = rs.Fields
	
%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>修改電子鎖密碼</title>
</head>

<body>
<p align="center"><b><font face="標楷體" size="5" color="#0000FF">修改電子鎖密碼</font></b></p>
<%
Response.Write "<table border='1' width='100%'>"
Response.Write "<tr>"
For I =0 to Field.Count -1
	Response.Write "<td bgcolor='#0000FF'><font color='#FFFFFF' size='2'>" & Field(I).Name & "</font></td>"
Next
	Response.Write "<td bgcolor='#0000FF'><font color='#FFFFFF' size='2'>" & "View" & "</font></td>"
Response.Write "</tr>"

Do While Not rs.EOF
	Response.Write  "<tr>"
	For I =0 to Field.Count -1
		Response.Write  "<td><font size='2'>" & Field(I).Value & "</font></td>"
	Next
	Response.Write "<td><font color='#FFFFFF' size='2'><a href='Device_Password_Information_Modify.asp?DeviceID=" & rs("DeviceID") & "'>" & "Modify 修改" & "</font></td>"
	rs.Movenext
Loop
Response.Write "</table>"
rs.close
Set Conn = Nothing

%>

</body>

</html>


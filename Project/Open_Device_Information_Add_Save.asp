<%
'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
	Session("UserID") = Request("UserID")
	Session("DeviceID") = Request("DeviceID")

	
	
	If Session("UserID") = "" Then
		Response.Write "User ID ���i�ťաC"
		Response.End
	End If
	If Session("DeviceID") = "" Then
		Response.Write "Device ID ���i�ťաC"
		Response.End
	End If
	

	
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


	'�}�Ҹ�Ʈw
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"


	SQL="Select * From Open_Device_Information"
	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3

	'�s�W���
	rs.AddNew
	rs("�ϥΪ�ID") = Session("UserID")
	rs("�}��ɶ�") = Now
	'rs("DeviceID") = 1
	rs("DeviceID") = Session("DeviceID")
	rs.update
	rs.close


SET CONN = NOTHING
Response.Write "�s�W��Ƨ����C"
'Response.Redirect "Open_Device_Information_Add.asp"

%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>�s����1</title>
</head>

<body>

</body>

</html>

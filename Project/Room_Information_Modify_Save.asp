
<%
'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If
%>
<%
	Session("UserMail") = Request("UserMail")
	Session("RoomName") = Request("RoomName")
	Session("RoomHC") = Request("RoomHC")
	Session("DeviceID") = Request("DeviceID")
	Session("DeviceCode") = Request("DeviceCode")


	'�ˬd���L�ť�
	If Session("UserMail") = "" Then
		Response.Write "�ϥΪ̱b�� ���i�ťաC"
		Response.End
	End If
	If Session("RoomName") = "" Then
		Response.Write "�ж��W�� ���i�ťաC"
		Response.End
	End If
	If Session("RoomHC") = "" Then
		Response.Write "�ж��H�� ���i�ťաC"
		Response.End
	End If	
	If Session("DeviceID") = "" Then
		Response.Write "DeviceID ���i�ťաC"
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

	SQL ="Select * From Room_Information Where RoomID = " & Session("RoomID")
	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3

	'�ק���
	rs("�ϥΪ̱b��") = Session("UserMail")
	rs("�ж��W��") = Session("RoomName")
	rs("�ж��H��") = Session("RoomHC")
	rs("DeviceID") = Session("DeviceID")
	rs("�]�ƱK�X����") = Session("DeviceCode")
	rs.update
	rs.close


SET CONN = NOTHING
Response.Write "�ק��Ƨ����C"
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


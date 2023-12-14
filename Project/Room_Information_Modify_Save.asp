
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


	'檢查有無空白
	If Session("UserMail") = "" Then
		Response.Write "使用者帳號 不可空白。"
		Response.End
	End If
	If Session("RoomName") = "" Then
		Response.Write "房間名稱 不可空白。"
		Response.End
	End If
	If Session("RoomHC") = "" Then
		Response.Write "房間人數 不可空白。"
		Response.End
	End If	
	If Session("DeviceID") = "" Then
		Response.Write "DeviceID 不可空白。"
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


	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"

	SQL ="Select * From Room_Information Where RoomID = " & Session("RoomID")
	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3

	'修改資料
	rs("使用者帳號") = Session("UserMail")
	rs("房間名稱") = Session("RoomName")
	rs("房間人數") = Session("RoomHC")
	rs("DeviceID") = Session("DeviceID")
	rs("設備密碼對應") = Session("DeviceCode")
	rs.update
	rs.close


SET CONN = NOTHING
Response.Write "修改資料完成。"
'Response.Redirect "Open_Device_Information_Add.asp"

%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>新網頁1</title>
</head>

<body>

</body>

</html>


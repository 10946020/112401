<%
	UserMail = Request("UserMail")

'	If Session("LoginType") <> "OK" Then
'		Response.Redirect "Login.asp"
'	End If

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


	SQL = "Select * From User_Information Where 使用者帳號 = '" & UserMail & "'"

	Set rs = Server.CreateObject("ADODB.Recordset")
	rs.Open SQL,Conn,1,3

	rs.delete
	rs.update
	rs.close

	'紀錄歷史資料
'	SQL1 = "Select * From ETCM_HIST"

'	Set rs1 = Server.CreateObject("ADODB.Recordset")
'	rs1.Open SQL1,Conn,1,3
	
'	rs1.AddNew
'	rs1("TCMID") = TCMID
'	rs1("USERBADGE") = Session("Badge")
'	rs1("ACTIVITY") = "Delete"
'	rs1("UPD") = NowTime
	
'	rs1.update
'	rs1.close


SET CONN = NOTHING
Response.Redirect "User_Information_Delete_Query.asp"
%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>新網頁1</title>
</head>

<body>

<p>　</p>

</body>

</html>
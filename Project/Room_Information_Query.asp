
<%

	'開啟資料庫
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "112_112401"


'Response.Write "Conn successfully"
'Response.End
	
	SQL = "SELECT * "
	SQL = SQL + " FROM Room_Information"

'Response.Write SQL 
'Response.End

	Set rs = Server.CreateObject("ADODB.Recordset")


	rs.Open SQL,Conn,1,3

	Set Field = rs.Fields
	


%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 12.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Room Information</title>
</head>

<body>
<p align="center"><b><font face="標楷體" size="5" color="#0000FF">Room Information</font></b></p>
<%
Response.Write "<table border='1' width='100%'>"
Response.Write "<tr>"
For I =0 to Field.Count -1
	Response.Write "<td bgcolor='#0000FF'><font color='#FFFFFF' size='2'>" & Field(I).Name & "</font></td>"
Next
Response.Write "</tr>"


Do While Not rs.EOF
	Response.Write  "<tr>"
	For I =0 to Field.Count -1
		Response.Write  "<td><font size='2'>" & Field(I).Value & "</font></td>"
	
	Next
	rs.Movenext
Loop
Response.Write "</table>"

rs.close
Set Conn = Nothing

%>

</body>

</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>免费定制天气服务</title>
</head>
<body>

<div align="center">
<a href="index.jsp">定制天气服务</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="sendMessage.jsp">发送短信服务</a>
<form id="form1" name="form1" method="post" action="serviceServlet">
<table width="368" height="151" border="0">
 <tr>
    <td colspan="3" align="left" valign="middle" nowrap="nowrap">
<p>请在下面填写上自己的手机号和飞信密码，就能免费定制每天8点天气预报的服务</p>
<p>包括今天的天气详情和预计明天的天气概况（暂时只支持杭州和绍兴地区，其它地区如有需求添加）</p>
<p>&nbsp;</p>
</td>
    </tr>
  <tr>
    <td width="97" align="left" valign="middle" nowrap="nowrap">手机号</td>
    <td colspan="2" align="left" valign="middle" nowrap="nowrap">
    <input name="phone" type="text" id="phone" value="" style="height:30px;width:370px;line-height:30px;color:#333;font-size: 24px;vertical-align:middle" /></td>
  </tr>
  <tr>
    <td align="left" valign="middle" nowrap="nowrap">飞信密码</td>
    <td colspan="2" align="left" valign="middle" nowrap="nowrap">
    <input name="fetion_password" type="password" id="fetion_password" value="" style="height:30px;width:370px;line-height:30px;color:#333;font-size: 24px;vertical-align:middle" /></td>
  </tr>
  <tr>
    <td align="left" valign="middle" nowrap="nowrap">区域</td>
    <td colspan="2" align="left" valign="middle" nowrap="nowrap">
    <select name="area">
        <option value="1" selected="selected">杭州</option>
        <option value="2">绍兴</option>
    </select>
  </tr>
  <tr>
    <td align="center" valign="middle"><br /></td>
    <td width="381" align="center" valign="middle"><br />      <input type="submit" name="button" id="button" value="      定制服务     " /></td>
    <td width="250" align="center" valign="middle">&nbsp;</td>
  </tr>
</table>
</form>
</div>
   
</body>
</html>
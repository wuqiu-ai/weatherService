<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发送短信服务</title>
</head>
<body>

<div align="center">
<a href="index.jsp">定制天气服务</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="sendMessage.jsp">发送短信服务</a>
<form id="form1" name="form1" method="post" action="SendServlet">
<table width="368" height="151" border="0">
 <tr>
    <td colspan="3" align="left" valign="middle" nowrap="nowrap">
<p>可以发送给其它手机<u>(只需在飞信中添加其为好友并通过对方验证，对方不一定要开通飞信</u>)，例如A,若A<br /><br />未开通飞信，可以在飞信中添加A为好友，则A会收到添加请求，若同意，则能收到短信。</p>
<p>请在下面填写上自己的手机号和飞信密码。这个页面只做测试用，请放心你所填写的手机号不会被记录。</p>
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
    <td>发送方手机号 </td>
    <td >
    <input type="text" name="tophone" id="tophone" value="" style="height:30px;width:370px;line-height:30px;color:#333;font-size: 24px;vertical-align:middle" /></td>
  </tr> 
  <tr>
      <td align="left" valign="middle" nowrap="nowrap">短信内容</td>
    <td colspan="2" align="left" valign="middle" nowrap="nowrap"><textarea name="message" id="message" style="height:150px;width:370px"></textarea>
      *字符不要太多</td>

  </tr>
  <tr>
    <td align="center" valign="middle"><br /></td>
    <td width="381" align="center" valign="middle"><br />      <input type="submit" name="button" id="button" value="      发送短信     " /></td>
    <td width="250" align="center" valign="middle">&nbsp;</td>
  </tr>
</table>
</form>
</div>
    
</body>
</html>
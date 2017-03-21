<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core" xmlns:ui="http://java.sun.com/jsf/facelets" xmlns:p="http://primefaces.org/ui"
   xmlns:a="http://www.ambientinformatica.com.br/jsf2">
<head>

<link href="css/estilo.css" rel="stylesheet" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<script>
	function focar() {
		document.getElementById("usuario").focus();
	}
</script>

</head>

<body>

   <div class="topCenter"></div>

   <div class="divLogo"></div>

   <div class="topRight"></div>

   <div class="loginLeft">
      <iframe id="loginFrame" name="loginFrame" frameborder="0" scrolling="no"></iframe>
   </div>

   <div class="loginRight">
      <form action="j_spring_security_check" method="post">

         <%
               if (request.getParameter("msg") != null) {
                     out.print("<span style='color: red;font-weight: bold;'>Usuário ou senha incorretos</span>");
                  }
            %>
         <br/>
         <font class="loginText">USUÁRIO</font><br /> <input class="loginBars" type="text" id="usuario" name="j_username" class="span3" /><br /> <font class="loginText">SENHA</font><br /> <input
            class="loginBars" type="password" name="j_password" class="span3" /><br /> <br /> <input class="loginButton" type="submit" value="Entrar" /><br /> <br />
      </form>
      <button class="cadastroButton" onclick="window.open('./cadastroUsuario.xhtml', 'loginFrame')">Cadastre-se</button>
   </div>

   <div class="bFooter">
      <font id="tradeMark" size="2" face="Arial Narrow" color="#FFFFFF" style="">© 2015 Ambient Informática</font>
   </div>

</body>
</html>
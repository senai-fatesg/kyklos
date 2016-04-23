<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://java.sun.com/jsf/html" 
	xmlns:f="http://java.sun.com/jsf/core" xmlns:ui="http://java.sun.com/jsf/facelets" xmlns:p="http://primefaces.org/ui"
   	xmlns:a="http://www.ambientinformatica.com.br/jsf2">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
	<link href="css/estilo.css" rel="stylesheet" type="text/css" />
    <link href="css/kyklos.css" rel="stylesheet" type="text/css" />
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
  	
  	<script>
		function focar() {
			document.getElementById("usuario").focus();
		}
	</script>
<title>Login</title>
</head>
<body>

	<div class="navbar navbar-dark bg-inverse"
		style="background-color: rgb(4,127,40)"></div>
	<div class="container-fluid">
	
	<div id="loginbox" style="margin-top: -15px;margin-left: -7px">
		<img src="img/logoPrimaria.png" width="185" />
	</div>
		<div id="loginbox" style="margin-top: -65px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<div class="panel panel-info">
			
				<div class="panel-heading" style="background-color: rgb(0,220,99)">
					<div class="panel-title">Kyklos | Login</div>
					<div
						style="float: right; font-size: 80%; position: relative; top: -10px">
						<a href="#">Esqueceu a senha?</a>
					</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form id="loginform" class="form-horizontal" role="form"
						action="j_spring_security_check" method="post">
						<%
					               if (request.getParameter("msg") != null) {
					                     out.print("<span style='color: red;font-weight: bold;'>Usuário ou senha incorretos</span>");
					                  }
           						 %>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> <input id="login-username"
								type="text" class="form-control" name="j_username" value="" placeholder="Usuário" />
						</div>
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
								type="password" class="form-control" name="j_password" placeholder="Senha" />
						</div>
						<div class="input-group">
							<div class="checkbox">
								<label> <input id="login-remember" type="checkbox"
									name="remember" value="1" /> Lembrar
								</label>
							</div>
						</div>
						<div style="margin-top: 10px" class="form-group">
							<!-- Button -->

							<div class="col-sm-12 controls">
								<a><input id="btn-login" href="#" type="submit"
									class="btn btn-success" style="background-color: rgb(4,127,40)"/></a>

							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12 control">
								<div class="toggle"
									style="border-top: 1px solid #888; padding-top: 15px; font-size: 85%">
									Ainda não é cadastrado! <i class="fa fa-times fa-pencil"></i> <a
										href="#"
										onclick="window.open('./cadastroUsuario.xhtml', '_parent');" />
									Cadastre-se já.
								</div>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>

	</div>
</body>
</html>
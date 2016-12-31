<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="http://panfeng.info/images/favicon.ico" />
<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap-dialog.css" rel="stylesheet">
<link href="css/theme/cupertino/jquery-ui.css" rel="stylesheet">
<link href="css/theme/cupertino/theme.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="css/custom.css" rel="stylesheet">
</head>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <!-- <a class="navbar-brand" href="http://panfeng.info">Darren's Home</a> -->
            <a class="navbar-brand" href="http://panfeng.info">Project</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <form class="navbar-form navbar-right" role="form">
                <c:if test="${user != null}">
                    <label class="login-name" id="name">${user.name}</label>
                    <button type="button" id="signin" class="btn btn-success hide">Sign in</button>
                    <button type="button" id="signout" class="btn btn-success">Sign out</button>
                </c:if>
                <c:if test="${user == null}">
                    <label class="login-name" id="name"></label>
                    <button type="button" id="signin" class="btn btn-success">Sign in</button>
                    <button type="button" id="signout" class="btn btn-success hide">Sign out</button>
                </c:if>
            </form>
        </div>
    </div>
</nav>
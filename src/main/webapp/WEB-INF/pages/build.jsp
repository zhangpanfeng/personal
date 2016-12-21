<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact Me</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
</head>
<body>
    <!--header-->
    <jsp:include page="../../header.jsp"></jsp:include>
    <!--contact-->
    <div class="jumbotron">
        <div class="container">
            <button type="button" id="build" class="btn btn-success">build</button>
            <input type="hidden" id="token" /> <a href="execShell.html">build</a>
        </div>

    </div>

    <div class="container">
        <hr>
    </div>
    <!-- footer -->
    <jsp:include page="../../footer.jsp"></jsp:include>

    <!-- Bootstrap core JavaScript -->
    <script src="js/jquery-1.11.1.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="js/websocket.js"></script>
</body>
</html>
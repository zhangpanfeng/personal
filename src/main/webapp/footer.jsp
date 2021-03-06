<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="container">
    <footer>
        <div class="pull-right col-lg-4">
            <a href="contact.html">Contact Me</a>
        </div>
        <c:if test="${user != null}">
            <div class="pull-right col-lg-4" id="buildLink">
                <a href="build.html">Build</a>
            </div>
        </c:if>
        <c:if test="${user == null}">
            <div class="pull-right col-lg-4 hide" id="buildLink">
                <a href="build.html">Build</a>
            </div>
        </c:if>
        <div class="pull-left col-lg-4">
            <p>&copy; 2016</p>
        </div>

    </footer>
</div>
<!-- Bootstrap core JavaScript -->
<script src="js/jquery-1.11.1.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script src="bootstrap/js/bootstrap-dialog.js"></script>
<script src="js/jquery-ui-1.11.1.js"></script>
<script src="js/globalize.js" type="text/javascript"></script>
<script src="js/globalize.culture.de-DE.js" type="text/javascript"></script>
<script src="js/jquery.mousewheel.js" type="text/javascript"></script>
<script src="js/custom-util.js"></script>
<script src="js/custom-login.js"></script>

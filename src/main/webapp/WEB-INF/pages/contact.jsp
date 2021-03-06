<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact Me</title>
</head>
<body>
    <!--header-->
    <jsp:include page="../../header.jsp"></jsp:include>
    <!--contact-->
    <div class="jumbotron">
        <div class="container">
            <div class="row myCenter ">
                <div class="col-md-6 col-center-block">
                    <div class="text-center">
                        <h2>Contact Me</h2>
                    </div>
                    <form role="form" id="mailForm">
                        <div class="form-group">
                            <label for="name">*Name</label> <input type="text" class="form-control"
                                name="name" placeholder="Your Name">
                        </div>
                        <div class="form-group">
                            <label for="email">*Email</label> <input type="text"
                                class="form-control" name="to" placeholder="Your Email">
                        </div>
                        <div class="form-group">
                            <label for="subject">*Subject</label> <input type="text"
                                class="form-control" name="subject" placeholder="Your Subject">
                        </div>
                        <div class="form-group">
                            <label for="subject">*Content</label>
                            <textarea rows="7" cols="" class="form-control" name="text"
                                placeholder="Your Content"></textarea>
                        </div>
                        <button type="submit" id="sendButton" class="btn btn-default pull-right">Send</button>
                    </form>
                    <div id="contactMessage" class="message"></div>
                </div>
            </div>
        </div>


    </div>

    <div class="container">
        <hr>
    </div>
    <!-- footer -->
    <jsp:include page="../../footer.jsp"></jsp:include>
    <script src="js/custom-mail.js"></script>
</body>
</html>
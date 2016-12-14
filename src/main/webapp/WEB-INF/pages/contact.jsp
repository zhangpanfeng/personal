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
      <div class="row myCenter ">
      <div class="col-md-6 col-center-block">
        <div class="text-center">
          <h2>Contact Me</h2>
        </div>
        <form role="form" >
          <div class="form-group">
            <label for="name">*Name</label> <input type="text"
              class="form-control" id="name" placeholder="Your Name">
          </div>
                    <div class="form-group">
            <label for="email">*Email</label> <input type="text"
              class="form-control" id="name" placeholder="Your Email">
          </div>
          <div class="form-group">
            <label for="subject">*Subject</label> <input type="text"
              class="form-control" id="name" placeholder="Your Subject">
          </div>
           <div class="form-group">
            <label for="subject">*Content</label> <textarea 
              rows="7" cols="" class="form-control" id="name" placeholder="Your Content"></textarea>
          </div>
          <button type="submit" class="btn btn-default pull-right">Send</button>
        </form>
        </div>
      </div>
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
</body>
</html>
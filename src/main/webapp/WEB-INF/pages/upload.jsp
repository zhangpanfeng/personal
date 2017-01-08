<!DOCTYPE html>
<html>
<head>
</head>
<body>
    <jsp:include page="../../header.jsp"></jsp:include>
    <div class="jumbotron">
        <div class="container">
            <form action="execUpload.html" method="POST" enctype="multipart/form-data" class="upload-form" target="hidden">
                <a href="javascript:;" class="file">
                    <input type="file" name="file" id="file"/> 
                </a>
                <input class="custom-input col-md-8" disabled="disabled" id="fileName">

                <div class="clear" style="height:10px;"></div>
                <div class="col-center-block message" id="uploadMessage"></div>
                <div style="height:10px;"></div>
                <div class="custom-progressbar outer">
                    <div class="custom-progressbar inner" id="progressbar"></div>
                </div>
                <div style="height:30px;"></div>
                <input type="submit" value="Submit" id="uploadFile" class="btn btn-success"/>
            </form>
            
        </div>
    </div>
    <iframe name="hidden"></iframe>
    <jsp:include page="../../footer.jsp"></jsp:include>
    <script type="text/javascript" src="js/custom-websocket.js"></script>
    <script type="text/javascript" src="js/custom-upload.js"></script>
</body>
</html>

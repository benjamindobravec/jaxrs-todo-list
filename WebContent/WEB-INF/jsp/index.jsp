<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&amp;subset=latin-ext" rel="stylesheet">
<link href="assets/index.css" rel="stylesheet">
<title>TODO</title>
</head>
<body>
  <h1>TODO</h1>
  <div id="root" class="items"></div>
  <div class="new-item">
    <input id="name" type="text" placeholder="Name" />
    <input id="description" type="text" placeholder="Description" />
    <button id="add">Add</button>
  </div>
  <script src="assets/index.js"></script>
</body>
</html>
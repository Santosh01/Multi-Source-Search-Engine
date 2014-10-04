<?php
require("phpsqlajax_dbinfo.php");
//Start XML file
$doc = new DOMDocument("1.0", "UTF-8");
$node = $doc->createElement("markers");
$parnode = $doc->appendChild($node);
//Open a new connection to MySql database
$connection = mysql_connect('localhost', $username, $password);
if (!$connection) {
	die('Not Connected :' . mysql_error());
}
//Set the MySql active
$db_selected = mysql_select_db($database, $connection);
if (!$db_selected) {
	die('Cannot use DB: ' . mysql_error());
}
//Select all rows from the database
$query = "SELECT * FROM markers";
$result = mysql_query($query);
if (!$result) {
	die('Invalid query!! ' . mysql_error());
}
//Iterate through the rows, adding XML nodes for each
while ($row = @mysql_fetch_assoc($result)) {
	$node = $doc->createElement("marker");
	$newnode = $parnode->appendChild($node);
	$newnode->setAttribute("name", $row['name']);
	$newnode->setAttribute("lat", $row['lat']);
	$newnode->setAttribute("lng", $row['lng']);
	$newnode->setAttribute("type", $row['type']);
	$newnode->setAttribute("q_wikipedia", $row['q_wikipedia']);
	$newnode->setAttribute("q_wikinews", $row['q_wikinews']);
	$newnode->setAttribute("q_wikivoyage", $row['q_wikivoyage']);
}
echo $doc->saveXML();
?>

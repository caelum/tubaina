<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="stylesheet" href="includes/css/yui-3.3-reset-min.css" type="text/css">
	<link rel="stylesheet" href="includes/css/yui-3.3-base-min.css" type="text/css">
	<link rel="stylesheet" href="includes/css/yui-3.3-fonts-min.css" type="text/css">

	<link rel="stylesheet" href="includes/css/structure.css" type="text/css" media="screen, print">
	<link rel="stylesheet" href="includes/css/layout.css" type="text/css" media="screen, print">
	<link rel="stylesheet" href="includes/css/screen.css" type="text/css" media="screen">
	<link rel="stylesheet" href="includes/css/print.css" type="text/css" media="print">
	
	<link rel="stylesheet" href="includes/js/syntax-highlighter/styles/shCoreEclipse.css" type="text/css">
	<link rel="stylesheet" href="includes/js/syntax-highlighter/styles/shThemeEclipse.css" type="text/css">
	<link rel="stylesheet" href="includes/css/highlight.css" type="text/css" media="screen">
	
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shCore.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushJava.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushXml.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushRuby.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushJScript.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushGroovy.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushCss.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushScala.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushSql.js"></script>
	<script type="text/javascript" src="includes/js/syntax-highlighter/scripts/shBrushPython.js"></script>
	
	<script type="text/javascript" src="includes/js/jquery-1.6.1.min.js"></script>
	<script type="text/javascript" src="includes/js/tubaina.js"></script>
	<script>
	window.onload = function() {
		Tubaina.init({
			title: '${booktitle}',
			footer: 'Capítulo {chapterNumber} - {chapter} - {section} - Página {page}',
			debug: true
		});
	}
	</script>
</head>
<body>

	<div id="tubaina">
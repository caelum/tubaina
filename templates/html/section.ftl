<#assign relative = "../..">
<#include "header.ftl">
	
	<!-- ======================================================== -->
	<!-- = Java SourceCode on this page was automatically converted to HTML using = -->
	<!-- =   Java2Html Converter 5.0 [2006-02-26] by Markus Gebhard  markus@jave.de   = -->
	<!-- =     Further information: http://www.java2html.de     = -->
	<!-- ======================================================== -->
	
<#include "navigation.ftl">
	
		<h1 class="sectionChapter">${title}</h1>

		<h2 class="section">${curchap}.${cursec} - ${section.title}</h2>

	   	
	   	<#list section.chunks as chunk>
	    	${chunk.getContent(parser)!""}
	   	</#list>
		
		<br/>
	
<#include "navigation.ftl">
<#include "footer.ftl">
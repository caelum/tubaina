<#assign relative = "../..">
<#include "header.ftl">
	
<#include "navigation.ftl">
	
		<h1 class="sectionChapter">${title}</h1>

		<h2 class="section">${curchap}.${cursec} - ${section.title}</h2>
	   	
	   	<#list section.chunks as chunk>
	    	${chunk.getContent(parser)!""}
	   	</#list>
		
		<br/>
	
<#include "navigation.ftl">
<#include "footer.ftl">
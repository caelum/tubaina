<#include "begin.ftl">

<#list book.parts as part>
	<#if part.printable>
		\tubainapart{${part.title}}
	</#if>
	<#list part.chapters as chapter>
		\npnchapter{${parser.parse(chapter.title)}}
		\setcounter{codecounter}{0}
		${chapter.getIntroduction(parser)}
		<#list chapter.sections as section>
			\section{${parser.parse(section.title!"")}}			
			<#list section.chunks as chunk>
				${chunk.getContent(parser)!""}
			</#list>
		</#list>
	</#list>
</#list>	

<#include "answerTemplate.ftl">

<#include "end.ftl">

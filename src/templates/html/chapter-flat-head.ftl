<#assign relative = "..">
<#include "header.ftl">
<#include "navigation.ftl">
	
		<div class="chapterNumber">chapter <span class="chapterNumber">${curchap}</span></div>
		
		<h1 class="chapter">${chapter.title}</h1>
		
		<br />

    	${chapter.getIntroduction(parser)}
	
	<!-- Table of contents of the chapter-->
	<#if 0 < chapter.sections.size()>
	
			<div class="contentBox">
				<h2>${chapter.title}</h2>
				
				<hr />
						
				<#list chapter.sections as section>
					<#assign title = section.title!"" >
					<#if title != "">
						<#assign secdir = dirTree[curdir + section_index + 1]>
						<h3 class="indexSection"><a href="../../${secdir}">${curchap}.${section_index + 1} - ${title}</a></h3>
					</#if>
				</#list>
	
				<hr />
													
			</div><!-- contentBox -->
				
	</#if>
	<br/>

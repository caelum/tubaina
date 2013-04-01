	<div class='referenceable'>
		<h2 class='referenceableTitle'>${chapter.chapterNumber} - ${sanitizer.sanitize(chapter.title)}</h2>
		<a id="${chapter.label}" class='label'></a>

		${chapter.getIntroduction()}
    	
		<#assign sectionCount = 1>
		
    	<#list chapter.sections as section>
		<div class='referenceable'>
			<h3 class='referenceableTitle'>
				${chapter.chapterNumber}.${sectionCount} - ${sanitizer.sanitize(section.title)}
			</h3>
		   	
		   	<#list section.chunks as chunk>
		    	${chunk.asString()!""}
		   	</#list>
			
			<br/>
		</div>
    		<#assign sectionCount = sectionCount + 1>
	   	</#list>
	   	
   	</div>
				
						
		
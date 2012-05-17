		<div class='referenceable'>
			<h1 class='referenceableTitle'>${chapter.chapterNumber} - ${sanitizer.sanitize(chapter.title)}</h1>
	
	    	${chapter.getIntroduction(parser)}
	    	
			<#assign sectionCount = 1>
	    	<#list chapter.sections as section>
    			<div class='referenceable'>
					<h2 class='referenceableTitle'>${chapter.chapterNumber}.${sectionCount} - ${sanitizer.sanitize(section.title)}</h2>
				   	
				   	<#list section.chunks as chunk>
				    	${chunk.getContent(parser)!""}
				   	</#list>
					
					<br/>
		    	</div>
		    	<#assign sectionCount = sectionCount + 1>
		   	</#list>
    	</div>
				
						
		
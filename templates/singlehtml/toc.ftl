<div class="toc">
	<#assign chapterCount = 1>
    <ul>
	<#list book.chapters as chapter>
		<li>${chapterCount} - ${sanitizer.sanitize(chapter.title)}</li>
		<#assign sectionCount = 1>
		<ul>
			<#list chapter.sections as section>
				<li>${sectionCount} - ${sanitizer.sanitize(section.title)}</li>
			</#list>
		</ul>
		<#assign chapterCount = chapterCount + 1>
   	</#list>
    </ul>
</div>	

<div>
    <h2 class='referenceable'>Bibliography</h2>
    <ol>
    <#assign entryCount = 1>
    <#list bibliography.entries as entry>
        <li position="${entryCount}"
			<#if entry.label?? && !entry.label.empty>
				id="${entry.label}"
			</#if>
		>
        	${sanitizer.sanitize(entry.author)}, ${sanitizer.sanitize(entry.title)}
        	<#if entry.journal??>
        		, ${sanitizer.sanitize(entry.journal)}
        	</#if> 
        	<#if entry.year?? && !entry.year.empty>
	        	, (${sanitizer.sanitize(entry.year)})
        	</#if>
    	</li>
        <#assign entryCount = entryCount + 1>
    </#list>
    </ol>
</div>
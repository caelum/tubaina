<div>
    <h2 class='referenceable'>Bibliography</h2>
    <ol>
    <#assign entryCount = 1>
    <#list bibliography.entries as entry>
        <li id="${entry.label}" position="${entryCount}">
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
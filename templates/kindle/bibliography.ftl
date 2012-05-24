<div>
    <h2 class='referenceable'>Bibliography</h2>
    <ol>
    <#assign entryCount = 1>
    <#list bibliography.entries as entry>
        <li id="${entry.label}" position="${entryCount}">${sanitizer.sanitize(entry.author)}, ${sanitizer.sanitize(entry.title)}, (${sanitizer.sanitize(entry.year)})</li>
        <#assign entryCount = entryCount + 1>
    </#list>
    </ol>
</div>
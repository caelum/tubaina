<div>
    <h2>Bibliography</h2>
    <ol>
    <#list bibliography.entries as entry>
        <li>${sanitizer.sanitize(entry.author)}, ${sanitizer.sanitize(entry.title)}, (${sanitizer.sanitize(entry.year)})</li>
    </#list>
    </ol>
</div>
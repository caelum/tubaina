<#assign relative = '..'>
<#include "header.ftl">
	
	<h1 class=index> Index </h1>
	
		<#assign keys = indexes.keySet()>
		<#list keys as key>
			<#assign index = indexes[key]>
			<a class="index" href="../${relative}/${dirTree[index]?split("#")[0]}#${key}">
				${key}
			</a><br />
		</#list>

<#include "footer.ftl">

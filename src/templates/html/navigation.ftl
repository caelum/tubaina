	<div class="navigation">
		<a href="../${relative}/${dirTree[curdir - 1]}"><img src="${relative}/includes/images/previous-arrow.png" alt="Previous" /></a>
		<a href="../"><img src="${relative}/includes/images/up-arrow.png" alt="Up" /></a>
		<#assign next=dirTree[curdir + 1]!"">
		<#if next != "">
			<a href="../${relative}/${next}"><img src="${relative}/includes/images/next-arrow.png" alt="Next" /></a>
		</#if>
	</div> <!-- navigation -->

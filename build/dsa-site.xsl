<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	version="2.0">

	<xsl:output method="html" indent="yes" name="html"/>

	<xsl:template match="/course">
		<xsl:variable name="course_name"  select="@name" />
		<xsl:result-document href="index.html" format="html">
		        <xsl:value-of select="$course_name"/>
		</xsl:result-document>
				
		<xsl:for-each select="//lecture">
			<xsl:variable name="filename"  select="concat('lectures/',@fs_name,'.html')" />
			<xsl:value-of select="$filename" />
			<xsl:result-document href="{$filename}" format="html">
				<html><body>
			        <h1><xsl:value-of select="@name"/></h1>
			        <h2>‹ҐЄжЁп Ї® ЇаҐ¤¬Ґвг &laquo;<xsl:value-of select="$course_name"/>&raquo;</h2>
				
				</body></html>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>

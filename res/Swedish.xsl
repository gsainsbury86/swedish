<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <h2>Army</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>Unit</th>
      <th>Size</th>
    </tr>
    <xsl:for-each select="document/squads/squad">
    <tr>
      <td><xsl:value-of select="@name"/></td>
      <td><xsl:value-of select="unit/@count"/></td>
    </tr>
    </xsl:for-each>
  </table>

  <h2>Swedish</h2>
  <table border="1">
        <tr bgcolor="#9acd32">
  <th>Rule</th>
  <th>Cost</th>
      </tr>


  <xsl:for-each select="document('he-swedish.xml')/rules/rule">

<tr>
  <td><xsl:value-of select="@name"/></td>
  <td><xsl:value-of select="@cost"/></td>
</tr>
</xsl:for-each>


</table>


  </body>
  </html>
</xsl:template>

</xsl:stylesheet>
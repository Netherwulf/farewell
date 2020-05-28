<?xml version="1.0" ?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="3.0"
>

    <xsl:output method="html" indent="yes" media-type="text/html" encoding="UTF-8" />
    <xsl:template match="/">
        <html>
            <head>
                <title>
                    Grave Report
                </title>
            </head>
            <body>
                <h1>
                    Grave Report
                </h1>
                <div name="averageReservationToPurchaseTime">
                    <h2>Average time from reservation to purchase time</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageReservationToPurchaseTime"/></p>
                </div>
                <div name="averageGravesPerDay">
                    <h2>Average number of graves per day</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageGravesPerDay"/></p>
                </div>
                <div name="medianGravesPerDay">
                    <h2>Median of graves per day</h2>
                    <p><xsl:value-of select="GraveReportDTO/medianGravesPerDay"/></p>
                </div>
                <div name="modeGravesPerDay">
                    <h2>Mode of graves per day</h2>
                    <p><xsl:value-of select="GraveReportDTO/modeGravesPerDay"/></p>
                </div>
                <div name="averageGravesPerMonth">
                    <h2>Average number of graves per month</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageGravesPerMonth"/></p>
                </div>
                <div name="medianGravesPerMonth">
                    <h2>Median of graves per month</h2>
                    <p><xsl:value-of select="GraveReportDTO/medianGravesPerMonth"/></p>
                </div>
                <div name="modeGravesPerMonth">
                    <h2>Mode of graves per month</h2>
                    <p><xsl:value-of select="GraveReportDTO/modeGravesPerMonth"/></p>
                </div>
                <div name="averageGravesPerYear">
                    <h2>Average number of graves per year</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageGravesPerYear"/></p>
                </div>
                <div name="medianGravesPerYear">
                    <h2>Median of graves per year</h2>
                    <p><xsl:value-of select="GraveReportDTO/medianGravesPerYear"/></p>
                </div>
                <div name="modeGravesPerYear">
                    <h2>Mode of graves per year</h2>
                    <p><xsl:value-of select="GraveReportDTO/modeGravesPerYear"/></p>
                </div>
                <div name="deceasedPerGrave">
                    <h2>Number of deceased per grave</h2>
                    <ul>
                        <xsl:for-each select="/GraveReportDTO/deceasedPerGrave/deceasedPerGrave">
                            <li><xsl:value-of select="."/></li>
                        </xsl:for-each>
                    </ul>
                </div>
                <div name="averageDeceasedPerGrave">
                    <h2>Average number of deceased per grave</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageDeceasedPerGrave"/></p>
                </div>
                <div name="medianDeceasedPerGrave">
                    <h2>Median of deceased per grave</h2>
                    <p><xsl:value-of select="GraveReportDTO/medianDeceasedPerGrave"/></p>
                </div>
                <div name="modeDeceasedPerGrave">
                    <h2>Mode of deceased per grave</h2>
                    <p><xsl:value-of select="GraveReportDTO/modeDeceasedPerGrave"/></p>
                </div>
                <div name="gravesPerUser">
                    <h2>Number of graves per user</h2>
                    <ul>
                        <xsl:for-each select="/GraveReportDTO/gravesPerUser/gravesPerUser">
                            <li><xsl:value-of select="."/></li>
                        </xsl:for-each>
                    </ul>
                </div>
                <div name="averageGravesPerUser">
                    <h2>Average number of graves per user</h2>
                    <p><xsl:value-of select="GraveReportDTO/averageGravesPerUser"/></p>
                </div>
                <div name="medianGravesPerUser">
                    <h2>Median of graves per user</h2>
                    <p><xsl:value-of select="GraveReportDTO/medianGravesPerUser"/></p>
                </div>
                <div name="modeGravesPerUser">
                    <h2>Mode of graves per user</h2>
                    <p><xsl:value-of select="GraveReportDTO/modeGravesPerUser"/></p>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
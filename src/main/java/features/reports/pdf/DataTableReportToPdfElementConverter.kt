package features.reports.pdf

import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.UnitValue
import core.data.medimage.ImageSeriesData
import core.data.report.DataTableAlgorithmReport
import features.reports.PdfElementData
import features.repositoryaccessors.AlgorithmExecutionException
import java.util.*
import javax.imageio.ImageIO


//----------------------------------------------------------------------------------------------------------

fun DataTableAlgorithmReport.asPdfElementData() : PdfElementData{
    val font: PdfFont? = PdfFontFactory.createFont(StandardFonts.HELVETICA)
    val bold: PdfFont? = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)

    val table = Table(this.table.columns.size)

    table.width = UnitValue.createPercentValue(100f)

    addHeaders(table, this.table.columns, bold)
    for (row in this.table.rows)
        addRow(table, row, font, this.table.columns)

    return PdfElementData(table)
}

private fun addRow(table: Table, items: Dictionary<String, String>, font: PdfFont?, headers: Collection<String>) {
    for (header in headers)
        table.addCell(Cell().add(Paragraph(items.get(header)).setFont(font)))
}

private fun addHeaders(table: Table, items: Collection<String>, font: PdfFont?) {
    for (item in items)
        table.addHeaderCell(Cell().add(Paragraph(item).setFont(font)))
}

//----------------------------------------------------------------------------------------------------------

fun ImageSeriesData.asPdfElementData() : PdfElementData{
    val images = this.images.map { x -> x.image}

    val result = Paragraph()
    try {
        for (image in images) {
            val stream = ByteArrayOutputStream()
            ImageIO.write(image, "jpg", stream)

            val pdfImage = Image(ImageDataFactory.create(stream.toByteArray()))
            pdfImage.width = UnitValue.createPercentValue(50f)
            pdfImage.setMargins(10f, 10f, 10f, 10f)
            pdfImage.setHeight(UnitValue.createPercentValue(50f))

            result.add(pdfImage)
        }
    } catch (e: Exception) {
        throw AlgorithmExecutionException(e)
    }

    return PdfElementData(result)
}



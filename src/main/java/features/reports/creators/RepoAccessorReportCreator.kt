package features.reports.creators

import core.algorithm.Algorithm
import core.data.DataTable
import core.data.medimage.ImageSeriesData
import core.data.attribute.MirfAttributes
import core.data.report.AlgorithmReport
import core.data.report.DataTableAlgorithmReport
import features.repositoryaccessors.RepoAccessorsAttributes

import java.util.ArrayList
import java.util.Dictionary
import java.util.Hashtable

/**
 * [AlgorithmReport] creator for RepositoryAccessors
 */
class RepoAccessorReportCreator : Algorithm<ImageSeriesData, AlgorithmReport> {

    private val headers: ArrayList<String>
        get() = object : ArrayList<String>() {
            init {
                add(REPOSITORY_HEADER)
                add(LINK_HEADER)
                add(TOTAL_LOADED)
                add(IMAGE_TYPE_HEADER)
            }
        }

    private fun getRows(medImages: ImageSeriesData): List<Dictionary<String, String>> {

        val repositoryInfo = medImages.attributes.findAttributeValue(MirfAttributes.REPO_INFO)

        val requestInfo = medImages.attributes.findAttributeValue(RepoAccessorsAttributes.REPOSITORY_REQUEST_INFO)

        val totalLoaded = medImages.images.size.toString()

        val row = Hashtable<String, String>()
        row[REPOSITORY_HEADER] = repositoryInfo!!.repositoryName
        row[LINK_HEADER] = requestInfo!!.link
        row[TOTAL_LOADED] = totalLoaded
        row[IMAGE_TYPE_HEADER] = medImages.images[0].extension

        return object : ArrayList<Dictionary<String, String>>() {
            init {
                add(row)
            }
        }
    }

    override fun execute(input: ImageSeriesData): DataTableAlgorithmReport {
        val reportTable = DataTable()
        reportTable.columns.addAll(headers)
        reportTable.rows.addAll(getRows(input))

        return DataTableAlgorithmReport(reportTable)
    }

    companion object {

        //TODO: (avlomakin) replace constants with resource variables
        private const val REPOSITORY_HEADER = "Repository"
        private const val LINK_HEADER = "Link"
        private const val TOTAL_LOADED = "Total loaded"
        private const val IMAGE_TYPE_HEADER = "Image type"
    }
}

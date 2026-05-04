package com.deepseek.fullystacked.ui.screens.pdf

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/**
 * Copies the bundled PDF from assets to the app's cache directory (once),
 * then opens it at the requested page using an external PDF viewer.
 */
object PdfUtils {

    private const val PDF_ASSET  = "the_full_stack_developer.pdf"
    private const val PDF_CACHE  = "fullstack_book.pdf"

    fun openPdfAtPage(context: Context, pageIndex: Int) {
        val file = ensureCached(context)
        val uri  = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // Page extras understood by Google PDF Viewer, Adobe Acrobat, etc.
            putExtra("page", pageIndex)
            putExtra("PDF_PAGE", pageIndex)
        }
        context.startActivity(Intent.createChooser(intent, "Open with PDF viewer"))
    }

    private fun ensureCached(context: Context): File {
        val cached = File(context.cacheDir, PDF_CACHE)
        if (!cached.exists()) {
            context.assets.open(PDF_ASSET).use { input ->
                FileOutputStream(cached).use { output -> input.copyTo(output) }
            }
        }
        return cached
    }
}

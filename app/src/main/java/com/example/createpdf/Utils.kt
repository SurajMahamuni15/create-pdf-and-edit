package com.example.createpdf

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


fun Activity.checkPermission(permissionsList: List<String>) {
    permissionsList.forEach {
        if (checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(it), 112)
        }
    }
}

fun Activity.createPdf(editPdf: ( paint: Paint, pageInfo: PdfDocument.PageInfo, pdfDocument : PdfDocument, page: PdfDocument.Page,file:File) -> Unit) {
    val pdfDocument = PdfDocument().apply {
        val paint = Paint()
        val pageInfo = PdfDocument.PageInfo.Builder(210, 297, 1).create()
        val firstPage = startPage(pageInfo)
        val file = File(
            getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "PdfCreator_${SimpleDateFormat("ddMyyyyhhmmss").format(Date())}.pdf"
        )
        editPdf(paint, pageInfo, this,firstPage,file)
        try {
            writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        finishPage(firstPage)
        close()
        Toast.makeText(this@createPdf,"pdf Created successfully!",Toast.LENGTH_SHORT).show()
    }
}

fun Canvas.drawMultiline(str: String, x: Int, y: Int, paint: Paint) {
    var y = y
    for (line in str.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
        drawText(line, x.toFloat(), y.toFloat(), paint)
        y += (-paint.ascent() + paint.descent()).toInt()
    }
}
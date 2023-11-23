package com.example.createpdf

import android.Manifest
import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.createpdf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission(ArrayList<String>().apply { add(Manifest.permission.WRITE_EXTERNAL_STORAGE) })

        binding.createPdf.setOnClickListener {
            createPdf(editPdf = { paint, pageInfo, pdfDoc, page, file ->

                //invoice title
                paint.apply {
                    textAlign = Paint.Align.LEFT
                    textSize = 12f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
                page.canvas.drawText(
                    resources.getString(R.string.invoice_title),
                    10f,
                    25f,
                    paint
                )

                //company title
                paint.apply {
                    typeface = Typeface.DEFAULT
                    textSize = 6f
                    textAlign = Paint.Align.RIGHT
                }
                page.canvas.drawText(
                    resources.getString(R.string.phn_title),
                    (pageInfo.pageWidth - 10).toFloat(),
                    35f,
                    paint
                )


                //line
                paint.apply {
                    textAlign = Paint.Align.CENTER
                }
                //draw line
                page.canvas.drawLine(10f, 55f, (pageInfo.pageWidth - 10).toFloat(), 55f, paint)


                pdfDoc.finishPage(page)
//                pdfDoc.close()
                binding.pdfView.fromFile(file).load()
            })

        }
    }

}
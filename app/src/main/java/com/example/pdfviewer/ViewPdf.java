package com.example.pdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class ViewPdf extends AppCompatActivity {

    PDFView pdfView;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        getSupportActionBar().hide();

        position = getIntent().getIntExtra("position",-1);

        displayPDF();
    }

    private void displayPDF() {
        pdfView.fromFile(MainActivity.fileList.get(position))
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}
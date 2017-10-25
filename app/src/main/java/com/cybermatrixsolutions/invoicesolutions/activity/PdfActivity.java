package com.cybermatrixsolutions.invoicesolutions.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.cybermatrixsolutions.invoicesolutions.R;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

public class PdfActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener{

    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String TAG="PDFViewActivity";
    public static final String SAMPLE_FILE = "sample.pdf";
    int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        init();
    }

    private void init(){
        pdfView= (PDFView)findViewById(R.id.pdfview);
        position = getIntent().getIntExtra("position",-1);
        displayFromAsset(SAMPLE_FILE);
    }

    private void displayFromSdcard() {
        pdfFileName ="/sdcard/test.pdf";
        File file = new File(pdfFileName);
        Log.e("File path",file.getAbsolutePath());
        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
       // printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

/*
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
*/
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
}

package com.cybermatrixsolutions.invoicesolutions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_web_view);
        WebView webview=new WebView(WebViewActivity.this);
       // WebView webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setWebViewClient(new Callback());
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        setContentView(webview);
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}
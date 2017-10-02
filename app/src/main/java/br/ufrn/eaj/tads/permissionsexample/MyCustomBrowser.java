package br.ufrn.eaj.tads.permissionsexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyCustomBrowser extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_browser);

        webview = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();


        if (intent.getAction().equals(Intent.ACTION_VIEW)) {

            Uri uri = intent.getData();

            webview.loadUrl(uri.toString());
            webview.setWebViewClient(new WebViewClient());
        }
    }
}

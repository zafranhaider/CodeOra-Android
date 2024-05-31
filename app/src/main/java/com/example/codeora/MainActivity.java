package com.example.codeora;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        if (!NetworkUtil.isNetworkAvailable(this)) {
            showErrorPage();
            return;
        }

        webView.setWebViewClient(new CustomWebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Add JavaScript interface
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.loadUrl("https://zafran.pythonanywhere.com/index/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void injectLazyLoadingScript() {
        webView.evaluateJavascript(
                "(function() {" +
                        "   window.addEventListener('scroll', function() {" +
                        "       var loader = document.getElementById('loader');" +
                        "       if (!loader) {" +
                        "           loader = document.createElement('div');" +
                        "           loader.id = 'loader';" +
                        "           loader.style.position = 'fixed';" +
                        "           loader.style.bottom = '10px';" +
                        "           loader.style.left = '50%';" +
                        "           loader.style.transform = 'translateX(-50%)';" +
                        "           loader.style.display = 'none';" +
                        "           loader.innerHTML = '<div style=\"width: 30px; height: 30px; border: 4px solid #f3f3f3; border-top: 4px solid #3498db; border-radius: 50%; animation: spin 2s linear infinite;\"></div>';" +
                        "           document.body.appendChild(loader);" +
                        "           var style = document.createElement('style');" +
                        "           style.type = 'text/css';" +
                        "           style.innerHTML = '@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }';" +
                        "           document.head.appendChild(style);" +
                        "       }" +
                        "       var content = document.getElementById('content');" +
                        "       if (!content) {" +
                        "           content = document.body;" +
                        "       }" +
                        "       var lastSection = content.lastElementChild;" +
                        "       var rect = lastSection.getBoundingClientRect();" +
                        "       if (rect.bottom <= window.innerHeight) {" +
                        "           loader.style.display = 'block';" +
                        "           setTimeout(function() {" +
                        "               var newSection = document.createElement('div');" +
                        "               newSection.className = 'section';" +
                        "               newSection.innerHTML = 'New Section Content';" +
                        "               content.appendChild(newSection);" +
                        "               loader.style.display = 'none';" +
                        "           }, 1000); // Simulate network delay" +
                        "       }" +
                        "   });" +
                        "})()", null);
    }

    private void showErrorPage() {
        Intent intent = new Intent(MainActivity.this, internet.class);
        startActivity(intent);
        finish();
    }

    public class WebAppInterface {
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            Log.d(TAG, "Page started loading: " + url);

            // Set a timeout to hide the progress bar in case onPageFinished is not called
            new Handler().postDelayed(() -> {
                if (progressBar.getVisibility() == ProgressBar.VISIBLE) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Log.d(TAG, "Progress bar hidden due to timeout");
                }
            }, 4000); // 4 seconds timeout
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(ProgressBar.GONE);
            Log.d(TAG, "Page finished loading: " + url);

            // Inject JavaScript for lazy loading
            injectLazyLoadingScript();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.e(TAG, "WebView error: " + error.getDescription());
            if (request.isForMainFrame()) {
                showErrorPage();
            }
        }

        @Override
        public void onReceivedHttpError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.e(TAG, "HTTP error: " + errorResponse.getReasonPhrase());
            if (request.isForMainFrame()) {
                showErrorPage();
            }
        }
    }
}

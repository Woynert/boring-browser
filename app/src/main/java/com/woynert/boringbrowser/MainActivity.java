package com.woynert.boringbrowser;
import com.woynert.boringbrowser.databinding.ActivityMainBinding;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.webkit.WebViewCompat;
import com.google.android.material.snackbar.Snackbar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private WebView webView;
    private EditText tbxUrl;
    private String homepage = "https://google.com";
    private static final String TAG = "BoringBrowserDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Startup");

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webView = findViewById(R.id.webview);
        tbxUrl = findViewById(R.id.tbx_url);
        findViewById(R.id.btn_home).setOnClickListener(v -> onGoHome());

        setupUrlBar();
        setupWebView();
        webView.loadUrl(homepage);

        Snackbar.make(webView, "Welcome", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
    }

    public void setupWebView(){
        
        // Enabling features.

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadsImagesAutomatically(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Load JS script.
                String fileContent = readTextFileFromAssets("script_disable_media.js");
                webView.loadUrl("javascript:" + fileContent);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                tbxUrl.setText(url);
                super.doUpdateVisitedHistory(view, url, isReload);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String message = consoleMessage.message();
                int lineNumber = consoleMessage.lineNumber();
                String sourceId = consoleMessage.sourceId();
                ConsoleMessage.MessageLevel level = consoleMessage.messageLevel();

                Log.d(TAG, "[" + level + "] " + message + " -- Line " + lineNumber + " of " + sourceId);
                return true;
            }
        });

        // Disables shadow DOM.
        // In case DOCUMENT_START_SCRIPT is not supported let it crash.

        String js_disable_shadows = readTextFileFromAssets("script_disable_shadows.js");
        WebViewCompat.addDocumentStartJavaScript(
            webView,
            js_disable_shadows,
            Collections.singleton("*") // All web domains/origins.
        );
    }

    public void setupUrlBar(){
        tbxUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT ||
                    actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    // navigate
                    String enteredText = tbxUrl.getText().toString();
                    webView.loadUrl(enteredText);

                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tbxUrl.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    public void onGoHome() {
        webView.loadUrl(homepage);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String readTextFileFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return stringBuilder.toString();
    }
}

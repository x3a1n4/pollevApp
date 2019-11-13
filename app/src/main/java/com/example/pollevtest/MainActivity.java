package com.example.pollevtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String pollEvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WebView myWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        //dismiss networkonmainthread error
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout urlWindow = (LinearLayout) getLayoutInflater().inflate(R.layout.url_window, null);
                final EditText url = (EditText) urlWindow.findViewById(R.id.pollevUrl);

                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            myWebView.clearCache(true);
                            myWebView.clearFormData();

                            CookieManager.getInstance().removeAllCookies(null);
                            CookieManager.getInstance().flush();


                            String website = url.getText().toString();
                            System.out.println(website);

                            myWebView.loadUrl(website);

                            pollEvUrl = website;
                        }catch(Exception e){
                            System.out.println(e);
                        }

                    }
                };

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Set url")
                        .setView(urlWindow)
                        .setPositiveButton("Ok", onClickListener)
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        FloatingActionButton reload = findViewById(R.id.reload);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    myWebView.clearCache(true);
                    myWebView.clearFormData();

                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();

                    myWebView.loadUrl(pollEvUrl);
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

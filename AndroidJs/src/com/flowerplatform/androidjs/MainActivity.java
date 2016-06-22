package com.flowerplatform.androidjs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	
	WebView myBrowser;
	EditText editTextMsg;
	Button btnSendMsg;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myBrowser = (WebView)findViewById(R.id.mybrowser);
	    final JavascriptBridge jsBridge
	        = new JavascriptBridge(this);
       myBrowser.addJavascriptInterface(jsBridge, "AndroidFunction");    
       myBrowser.getSettings().setJavaScriptEnabled(true); 
       myBrowser.loadUrl("file:///android_asset/mypage.html"); 
       
       editTextMsg = (EditText)findViewById(R.id.message);
       btnSendMsg = (Button)findViewById(R.id.btnMessage);
       btnSendMsg.setOnClickListener(new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    String msgToSend = editTextMsg.getText().toString();
		    myBrowser.loadUrl("javascript:callFromActivity(\""+msgToSend+"\")");
		}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class JavascriptBridge {
		Context mContext;

		JavascriptBridge(Context c) {
			mContext = c;
		}
		@JavascriptInterface 
		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		}

	}
	
}

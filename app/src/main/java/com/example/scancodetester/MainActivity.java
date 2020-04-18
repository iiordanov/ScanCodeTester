package com.example.scancodetester;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnKeyListener {
    TextView textView1 = null;
    Toast prevToast = null;
    
    private void showToast (String text) {
        if (prevToast != null) {
            prevToast.cancel();
        }
        Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        prevToast = toast;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout r = (LinearLayout) findViewById(R.id.layout);
		r.setOnKeyListener(this);
		r.setFocusableInTouchMode(true);
        r.setOnTouchListener(new OnTouchListener () {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showToast(event.toString() + "\nSource: " + event.getSource());
                refreshTextView();
                return true;
            }
            
        });
        
		ImageButton b = (ImageButton)findViewById(R.id.imageButton1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                refreshTextView();
				InputMethodManager inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMgr.toggleSoftInput(0, 0);
			}
		});
		
        //final View rootView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
		//rootView.setOnKeyListener(this);
		//EditText t = (EditText) this.findViewById(R.id.editText1);
		//t.setOnKeyListener(this);
        textView1 = (TextView) findViewById(R.id.textView1);
        
        refreshTextView();
	}
	
    // Send e.g. mouse events like hover and scroll to be handled.
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        showToast(event.toString() + "\nSource: " + event.getSource());
        android.util.Log.e("MainActivity", event.toString());
        refreshTextView();
        return true;
    }
    
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        showToast(event.toString() + "\nSource: " + event.getSource());
        android.util.Log.e("MainActivity", event.toString());
        refreshTextView();
        return true;
    }

	private void refreshTextView() {
        String sdkVersion = Integer.toString(android.os.Build.VERSION.SDK_INT);
        boolean hasPermanentMenuKey = android.os.Build.VERSION.SDK_INT < 14 || ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasQwertyKeyboard = getResources().getConfiguration().keyboard == Configuration.KEYBOARD_QWERTY;
        boolean isPhone = new WebView(this).getSettings().getUserAgentString().contains("Mobile");
        
	    textView1.setText("SDK: " + sdkVersion + "\n\n");
        
        textView1.append("Permanent menu key: ");
        if (hasPermanentMenuKey) {
            textView1.append("YES\n");
        } else {
            textView1.append("NO\n");         
        }
        
        textView1.append("Physical back key: ");
        if (hasBackKey) {
            textView1.append("YES\n");
        } else {
            textView1.append("NO\n");         
        }
        
        textView1.append("QWERTY keyboard present: ");
        if (hasQwertyKeyboard) {
            textView1.append("YES\n");
        } else {
            textView1.append("NO\n");         
        }
        
        textView1.append("Is a phone: ");
        if (isPhone) {
            textView1.append("YES\n");
        } else {
            textView1.append("NO\n");         
        }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent event) {
	    showToast(event.toString() + "\nSource: " + event.getSource() + "\nCharacters: " + event.getCharacters());
		android.util.Log.e("MainActivity", event.toString());
        refreshTextView();
		return true;
	}
}

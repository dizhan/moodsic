package com.LMFM.moodsic;




import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.neurosky.thinkgear.*;

import android.bluetooth.*;
import android.content.Intent;

public class 	FirstActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		MainActivity main = new MainActivity();
		//TGDevice tgDevice = main.tgDevice;
		//Handler handler = ((Object) tgDevice).handler();

		int attentionValue = main.returnAttention();
		System.out.println(attentionValue);
		System.out.println("try");
  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


    
        
}
package com.LMFM.moodsic;




import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;




import com.neurosky.thinkgear.*;
import android.bluetooth.*;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private static final int REQUEST_ENABLE_BT = 1;
	
	BluetoothAdapter bluetoothAdapter;
	TGDevice tgDevice;
	Boolean playing = false;
	ArrayList<String> AttList= new ArrayList();
	int att;
	//ViewFlipper mViewFlipper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
        final Button button1 = (Button)findViewById(R.id.button1);
        button1.setEnabled(true);
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(MainActivity.this, R.raw.seagull);

        button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				button1.setEnabled(false);
				if(bluetoothAdapter == null) {
                	button1.setEnabled(true);
                	button1.setText("Re-connect");

		        }else {
		            //button1.setText("connected");
		            //button1.getBackground().setColorFilter(0xFF0000FF,android.graphics.PorterDuff.Mode.MULTIPLY);
		        	tgDevice = new TGDevice(bluetoothAdapter, handler);
		        	tgDevice.connect(true);
		        	tgDevice.start();
		        	
		   
		        }  
			}
		});


     //   button1.setEnabled(true);
      //  button1.setText("connected");
      //  button1.getBackground().setColorFilter(0xFF00FF00,android.graphics.PorterDuff.Mode.MULTIPLY);
	
        final Button button2 = (Button)findViewById(R.id.button2);
        button2.setEnabled(false);
        final ViewFlipper mViewFlipper = (ViewFlipper) findViewById(R.id.details); 
        button2.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				//button2.setEnabled(false);
				mViewFlipper.showNext(); 
				System.out.println("change");

				//jumpToLayoutb();
				//setContentView(R.layout.activity_first);
				/*setContentView(R.layout.activity_main);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FirstActivity.class);
				startActivity(intent);
				MainActivity.this.finish();	*/
	       
 
			}
		});
        
        final ImageButton buttonPlay = (ImageButton)findViewById(R.id.play);
        buttonPlay.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		System.out.println("playingplaying");
				mViewFlipper.showNext(); 

        		button1.setEnabled(false);
        		mediaPlayer1.start();
        		playing = true;
        	}
        });
        
        final ImageButton buttonNextsong = (ImageButton)findViewById(R.id.nextsong);
        buttonNextsong.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		
        	}
        });
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public final Handler handler = new Handler() {
        @Override
            public void handleMessage(Message msg) {

            	Button button1 = (Button)findViewById(R.id.button1);
            	Button button2 = (Button)findViewById(R.id.button2);


    			switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                	//tv.append("get message");
                    switch (msg.arg1) {
    	                case TGDevice.STATE_IDLE:
    	                    break;
    	                case TGDevice.STATE_CONNECTING:
    	                	button1.setText("Connecting");
    	                    button1.getBackground().setColorFilter(0xFFFF0000,android.graphics.PorterDuff.Mode.MULTIPLY);
    	                	break;		                    
    	                case TGDevice.STATE_CONNECTED:
       	                	button1.setText("Connected");
    	                    button1.getBackground().setColorFilter(0xFF00FF00,android.graphics.PorterDuff.Mode.MULTIPLY);
    	                	tgDevice.start();
    	                    break;
    	                case TGDevice.STATE_NOT_FOUND:
    	                	button1.setEnabled(true);
    	                	button1.setText("Re-connect");
    	                	System.out.println("state not found");

    	                	break;
    	                case TGDevice.STATE_NOT_PAIRED:
    	                	button1.setEnabled(true);
    	                	button1.setText("Re-connect");

    	                	break;
    	                case TGDevice.STATE_DISCONNECTED:
    	                	button1.setEnabled(true);
    	                	button1.setText("Re-connect");

                    }

                    break;
                case TGDevice.MSG_POOR_SIGNAL:
                    break;
                    
                case TGDevice.MSG_RAW_DATA:	  

                	break;
                case TGDevice.MSG_HEART_RATE:
                    break;
                case TGDevice.MSG_ATTENTION:
            		att = msg.arg1;
            		String attString = Integer.toString(att);
            		if (att >0){
            			button2.setEnabled(true);
            			//Intent intent = new Intent(); 
            			//returnAttention();
            			//("att",);
            			if (playing == true){
                			//button1.setEnabled(true);
                			//Arrays.fill(AttArray, att);
                			AttList.add(attString);
                   			//Method for Array
                			//int lengthArray = AttArray.length;
                			//int indexAtt = lengthArray;
                			//AttArray[indexAtt] = att;
                			//System.out.println(returnAttention());
                			System.out.println(attString); 
            			}
            		}
                	break;
                case TGDevice.MSG_MEDITATION:
                	break;
                case TGDevice.MSG_BLINK:
                	break;
                case TGDevice.MSG_RAW_COUNT:
                	break;
                case TGDevice.MSG_LOW_BATTERY:
                	Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
                	break;
                case TGDevice.MSG_RAW_MULTI:
                default:
                	break;
            }
            }
        };        	

    public int returnAttention(){

		return att;
    }
    
    public void jumpToLayoutb(){
    	  setContentView(R.layout.activity_first);
    	  ImageButton playsong  = (ImageButton)findViewById(R.id.play);
    	  playsong.setOnClickListener(new Button.OnClickListener(){
    	  
    		  public void onClick(View v) {
    			  
    	  
    	  }
    	  });
    }
    
    
        
}

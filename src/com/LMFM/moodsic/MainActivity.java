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
import android.graphics.Color;

public class MainActivity extends Activity {
	
	private static final int REQUEST_ENABLE_BT = 1;
	
	BluetoothAdapter bluetoothAdapter;
	TGDevice tgDevice;
	Boolean playing = false;
	ArrayList<String> AttList= new ArrayList();
	int att;
	float[] attentionVale = {0,0,0,0,0,0,0,0,0,0,0,0};



    
	//ViewFlipper mViewFlipper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
        final Button button1 = (Button)findViewById(R.id.button1);
        button1.setEnabled(true);
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(MainActivity.this, R.raw.seagull);

        // Draw the graph
        LineChartView lineChart = (LineChartView) findViewById(R.id.linechart);
        lineChart.setChartData(getRandomData());
        
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
        //button2.setEnabled(false);
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
        final ImageButton buttonNextsong = (ImageButton)findViewById(R.id.nextsong);        
        final ImageButton buttonPause = (ImageButton)findViewById(R.id.pause);
        buttonPause.setVisibility(View.INVISIBLE);
 
        buttonPause.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		buttonPlay.setEnabled(true);
        		buttonPause.setVisibility(View.INVISIBLE);
        		buttonPlay.setVisibility(View.VISIBLE);
        		buttonPause.setEnabled(false);
        		
        		mediaPlayer1.pause();
        		
        	}
        });
        

        buttonPlay.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		System.out.println("playingplaying");
        		//mViewFlipper.showPrevious();
        		buttonPause.setEnabled(true);
        		buttonPlay.setVisibility(View.INVISIBLE);
        		buttonPause.setVisibility(View.VISIBLE);
        		

        		mediaPlayer1.start();
        		playing = true;
        		buttonPlay.setEnabled(false);
        	}
        });
        

        buttonNextsong.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View v) {
        		
        	}
        });
        
        // try to do the dynamic plot



        
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
            			//button1.setEnabled(true);
            			//Arrays.fill(AttArray, att);
            			AttList.add(attString);

            	        LineChartView lineChart = (LineChartView) findViewById(R.id.linechart);
            	        
            	        lineChart.setChartData(getMindwave(AttList));
               			//Method for Array
            			//int lengthArray = AttArray.length;
            			//int indexAtt = lengthArray;
            			//AttArray[indexAtt] = att;
            			//System.out.println(returnAttention());
            			System.out.println(attString);
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

        private float[] getRandomData() {
            return new float[] { 0,0,0,0,0,0,0,0,100,100,100,29,30,21,29,30,41,60,48,40,43,35,40,41,38,38,26,14,14,11,23,29,29,47,51,48,43,24,10,13,4
            		//11,17,24,29,20,24,30,43,63,57,64,74,63,69,66,41,40,41,35,54,61,63,60,53,50,63,63,57,57,47,37,44,44,40,69,63,57,64,41,34,34,26,34,43,53,53,50,38,41,35,35,47,27,37,37,27,35,44,30,44,54,51
            		//66,48,47,38,43,43,40,38,26,40,40,57,70,77,74,56,48,43,40,53,56,54,69,60,56,60,48,41,47,41,40,56,51,50,43,26,29,23,21 
            		};
        }
        
        // this the function to get the data for the graph
        
        public float[] getMindwave(ArrayList<String> AttList){
        	// update instantaneous data:
        	
			String[] mStringAtt = new String[AttList.size()];
			mStringAtt = AttList.toArray(mStringAtt);
			float[] attfloat = new float[AttList.size()];
		
			
			for (int i = 0; i<mStringAtt.length; i++){
				attfloat[i] = Float.parseFloat(mStringAtt[i]);
			}
        	
        	// get rid the oldest sample in history:
        	

        	// add the latest history sample:
        	
        	// redraw the Plots:
        	

        	return attfloat;
        }
        
        public void redraw(){
        	
        }
  
    
        
}

package main.root.tyoushinseirunningmania;

import java.math.BigDecimal;
import java.util.Arrays;

import android.net.Uri;
import android.os.Bundle;
//import android.preference.PreferenceFragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {
	public static String[] autocompletedata;

	static String inputeddata = null;
	static double distance = 0.0,Akph = 0.0,result = 0.0;
	static int seconds = 0,minutes = 0,hours = 0,viewparam = 0,inputtype = 1,currentdesc = 3;
	static boolean flagofKM,flagofAnimation,flagofStart;
	static Spinner spinner;
	static SharedPreferences pref1 = null;
	//static String crlf = System.getProperty("line.separator");
	private static Context mContext;
	static String[] description = new String[7];
	private AdView mAdView;
	/*viewparam
	 *1 activity_main
	 *2 activity_input
	 *3 activity_animation
	 *4 activity_result
	 *5 activity_description
	 *6 activity_input2
	 *7 activity_result_more
	 *	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobileAds.initialize(this, getString(R.string.appid));
		mContext = this;
		pref1 = PreferenceManager.getDefaultSharedPreferences(this);
		flagofStart = pref1.getBoolean("start",true);
		flagofAnimation = pref1.getBoolean("animation",true);

		if(!flagofStart){
			viewparam = 2;
			flagofKM = true;
			showinput();
		}
		else{
			setContentView(R.layout.activity_main);
			viewparam = 1;
		}
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		description[0] = getString(R.string.description1);
		description[1] = getString(R.string.description2);
		description[2] = getString(R.string.description3);
		description[3] = getString(R.string.description4);
		description[4] = getString(R.string.description5);
		description[5] = getString(R.string.description6);
		description[6] = getString(R.string.description7);
	}

	public void OnButtonClick_start(View view){
		if(inputtype == 1){
			viewparam = 2;
			flagofKM = true;
			showinput();

			AutoCompleteTextView edittext1 = findViewById(R.id.editText1);
			EditText edittext2 = findViewById(R.id.editText2);
			EditText edittext3 = findViewById(R.id.editText3);
			EditText edittext4 = findViewById(R.id.editText4);

			edittext1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
						EditText t = findViewById(R.id.editText2);
						t.requestFocus();
						return true;
					}
					return false;
				}
			});

			edittext2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
						EditText t = findViewById(R.id.editText3);
						t.requestFocus();
						return true;
					}
					return false;
				}
			});

			edittext3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
						EditText t = findViewById(R.id.editText4);
						t.requestFocus();
						return true;
					}
					return false;
				}
			});

			edittext4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
						OnButtonClick_calculation(findViewById(R.id.button1));
						return true;
					}
					return false;
				}
			});
		}else if(inputtype == 2){
			setContentView(R.layout.activity_input2);
			mAdView = findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder().build();
			mAdView.loadAd(adRequest);
			viewparam = 6;
		}
	}

	public void OnButtonClick_fivekm(View view){
		AutoCompleteTextView edittext = findViewById(R.id.editText1);
		Button button = findViewById(R.id.button1);
		button.setText(getString(R.string.km));
		flagofKM = true;
		edittext.setText("5");
		edittext.setSelection(1);
	}

	public void OnButtonClick_tenkm(View view){
		AutoCompleteTextView edittext = findViewById(R.id.editText1);
		Button button = findViewById(R.id.button1);
		button.setText("km");
		flagofKM = true;
		edittext.setText("10");
		edittext.setSelection(2);
	}

	public void OnButtonClick_hurf(View view){
		AutoCompleteTextView edittext = findViewById(R.id.editText1);
		Button button = findViewById(R.id.button1);
		button.setText("km");
		flagofKM = true;
		edittext.setText("21.0975");
	}

	public void OnButtonClick_full(View view){
		AutoCompleteTextView edittext = findViewById(R.id.editText1);
		Button button = findViewById(R.id.button1);
		button.setText("km");
		flagofKM = true;
		edittext.setText("42.195");
	}


	public void OnButtonClick_dot(View view){
		AutoCompleteTextView edittext = findViewById(R.id.editText1);
		edittext.append(".");
	}

	public void OnButtonClick_changeunit(View view){
		Button button = findViewById(R.id.button1);
		flagofKM = !flagofKM;
		if(getString(R.string.kirometers).equals(button.getText().toString()))
			button.setText(getString(R.string.meter));
		else
			button.setText(getString(R.string.km));
	}

	public void OnButtonClick_changeinput(View view){
		if(inputtype == 1){
			inputtype = 2;
			setContentView(R.layout.activity_input2);
			mAdView = findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder().build();
			mAdView.loadAd(adRequest);

			viewparam = 6;
		}
		else{
			inputtype = 1;
			viewparam = 2;
			showinput();
		}
	}

	/*public void OnButtonClick_sercleone(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[0]);
	}

	public void OnButtonClick_sercletwo(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[1]);
	}

	public void OnButtonClick_serclethree(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[2]);
	}

	public void OnButtonClick_serclefour(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[3]);
	}

	public void OnButtonClick_serclefive(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[4]);
	}

	public void OnButtonClick_serclesix(View view){
		TextView textview = findViewById(R.id.textView1);
		textview.setText(description[5]);
	}*/

	public void OnButtonClick_gotodesc(View view){
		setContentView(R.layout.activity_description);
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		viewparam = 5;
		TextView textview = findViewById(R.id.textView2);
		textview.setText(description[0]);
		currentdesc = 3;
	}
	public void OnButtonClick_backdesc(View view){
		switch(currentdesc){
		case 4:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[0]);
			TextView textview1 = findViewById(R.id.textView3);
			TextView textview2 = findViewById(R.id.textView4);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		case 5:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[1]);
			TextView textview1 = findViewById(R.id.textView4);
			TextView textview2 = findViewById(R.id.textView5);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		case 6:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[2]);
			TextView textview1 = findViewById(R.id.textView5);
			TextView textview2 = findViewById(R.id.textView6);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		case 7:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[3]);
			TextView textview1 = findViewById(R.id.textView6);
			TextView textview2 = findViewById(R.id.textView7);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		case 8:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[4]);
			TextView textview1 = findViewById(R.id.textView7);
			TextView textview2 = findViewById(R.id.textView8);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		case 9:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[5]);
			TextView textview1 = findViewById(R.id.textView8);
			TextView textview2 = findViewById(R.id.textView9);
			textview1.setTextColor(Color.WHITE);
			textview2.setTextColor(Color.BLACK);
			currentdesc--;
			break;
		}
		}
	}
	public void OnButtonClick_nextdesc(View view){
		switch(currentdesc){
		case 3:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[1]);
			TextView textview1 = findViewById(R.id.textView3);
			TextView textview2 = findViewById(R.id.textView4);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		case 4:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[2]);
			TextView textview1 = findViewById(R.id.textView4);
			TextView textview2 = findViewById(R.id.textView5);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		case 5:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[3]);
			TextView textview1 = findViewById(R.id.textView5);
			TextView textview2 = findViewById(R.id.textView6);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		case 6:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[4]);
			TextView textview1 = findViewById(R.id.textView6);
			TextView textview2 = findViewById(R.id.textView7);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		case 7:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[5]);
			TextView textview1 = findViewById(R.id.textView7);
			TextView textview2 = findViewById(R.id.textView8);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		case 8:{
			TextView textview = findViewById(R.id.textView2);
			textview.setText(description[6]);
			TextView textview1 = findViewById(R.id.textView8);
			TextView textview2 = findViewById(R.id.textView9);
			textview2.setTextColor(Color.WHITE);
			textview1.setTextColor(Color.BLACK);
			currentdesc++;
			break;
		}
		}
	}

	public void OnButtonClick_calculation(View view){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		for(;;){
			//AutoCompleteTextView edittext1 = findViewById(R.id.autocompletetextView);
			AutoCompleteTextView edittext1 = findViewById(R.id.editText1);
			EditText edittext2 = findViewById(R.id.editText2);
			EditText edittext3 = findViewById(R.id.editText3);
			EditText edittext4 = findViewById(R.id.editText4);

			if("".equals(edittext1.getText().toString()) || ".".equals(edittext1.getText().toString())) {
				Toast.makeText(this, getString(R.string.pleasedistance), Toast.LENGTH_SHORT).show();
				break;
			}

			boolean[] typedtime = {true,true,true};
			if("".equals(edittext4.getText().toString())){
				edittext4.requestFocus();
				edittext4.setText("0");
				typedtime[0] = false;
			}
			if("".equals(edittext2.getText().toString())){
				edittext2.requestFocus();
				edittext2.setText("0");
				typedtime[1] = false;
		}
		if("".equals(edittext3.getText().toString())){
			edittext3.requestFocus();
			edittext3.setText("0");
			typedtime[2] = false;
		}
		if(!typedtime[0] && !typedtime[1] && !typedtime[2]){
			Toast.makeText(this,getString(R.string.pleasetime), Toast.LENGTH_SHORT).show();
			edittext2.requestFocus();
	   		edittext2.setText("");
	   		edittext3.requestFocus();
		   	edittext3.setText("");
			edittext4.requestFocus();
			edittext4.setText("");
			break;
		}
		if(flagofKM)
			distance = Double.parseDouble(edittext1.getText().toString());
		else
			distance = Double.parseDouble(edittext1.getText().toString()) / 1000;

			hours = Integer.parseInt(edittext2.getText().toString());
			minutes = Integer.parseInt(edittext3.getText().toString());
			seconds = Integer.parseInt(edittext4.getText().toString());

			BigDecimal big = new BigDecimal((hours * 3600 + minutes * 60 + seconds) / distance);
			result = big.setScale(3,BigDecimal.ROUND_DOWN).doubleValue();

			//Akph = Math.floor((3600 / result) * 100) / 100;
			BigDecimal big2 = new BigDecimal(3600/result);
			Akph = big2.setScale(2,BigDecimal.ROUND_DOWN).doubleValue();

			if(flagofKM){
				if(hours == 0){
					inputeddata = getString(R.string.inputteddata) + distance + "km / " +
							+ minutes + getString(R.string.minutes) + seconds + getString(R.string.seconds);
				}
				else {
					inputeddata = getString(R.string.inputteddata) + distance + "km / " + hours + getString(R.string.hour)
							+ minutes + getString(R.string.minutes) + seconds + getString(R.string.seconds);
				}
			}
			else{
				if(hours == 0) {
					inputeddata = getString(R.string.inputteddata) + distance * 1000 + "m / " +
							minutes + getString(R.string.minutes) + seconds + getString(R.string.seconds);
				}
				else{
					inputeddata = getString(R.string.inputteddata) + distance * 1000  + "m / " + hours + getString(R.string.hour) +
							minutes + getString(R.string.minutes) + seconds + getString(R.string.seconds);
				}
			}

			pref1 = PreferenceManager.getDefaultSharedPreferences(this);
			String str = pref1.getString(getString(R.string.autotexts), "");
			str = str + String.valueOf(distance) + " ";
			if(str.split(" ").length > 3) {
				str = str.substring(str.indexOf(" ") + 1, str.length());
			}
			SharedPreferences.Editor editor = pref1.edit();
			editor.putString(getString(R.string.autotexts), str);
			editor.apply();

			if(!flagofAnimation) {
				gotoresult();
			}
			else {
				animation(view);
			}
			break;
		}
	}

	public void OnButtonClick_calculation2(View view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		for(;;){
			EditText edittext1 = findViewById(R.id.editText1);
			EditText edittext2 = findViewById(R.id.editText2);

			if("".equals(edittext1.getText().toString()) && "".equals(edittext2.getText().toString())){
				Toast.makeText(this, getString(R.string.pleasetime2),Toast.LENGTH_SHORT).show();
				break;
			}
			String e1 = edittext1.getText().toString();
			String e2 = edittext2.getText().toString();
			if("".equals(e1))
				e1 = "0";
			if("".equals(e2))
				e2 = "0";

			inputeddata = getString(R.string.inputteddata) + e1 + getString(R.string.minutes) + e2 + getString(R.string.seconds) + getString(R.string.perkm);
			result = Integer.parseInt(e1) * 60 + Integer.parseInt(e2);

			BigDecimal big = new BigDecimal(3600/result);
			Akph = big.setScale(2,BigDecimal.ROUND_DOWN).doubleValue();

			if(!flagofAnimation){
				gotoresult();
			}else{
				animation(view);
			}
			break;
		}
	}

	public void animation(View view){
		setContentView(R.layout.activity_animation);//animation
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		viewparam = 3;
		ImageView imageview = findViewById(R.id.imageView1);
		TranslateAnimation translate = new TranslateAnimation(0,650,0,0);
		translate.setFillAfter(true);
		translate.setDuration(getanirunspeed());
		translate.setAnimationListener(new AnimationListener() {
			@Override
    	  	public void onAnimationEnd(Animation animation) {
    	    	// TODO Auto-generated method stub
				TextView textview = findViewById(R.id.textView1);
				textview.setText(getString(R.string.endcalculation));
				Button button = findViewById(R.id.button1);
				button.setText(getString(R.string.next));
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
    	});
		imageview.startAnimation(translate);
		}


	private static long getanirunspeed() {
		// TODO Auto-generated method stub
		//min 978 2'30" max 3450 6'00"[ms]
		long r = 0;
		if(result * 8.51 > 3450)
		r = 3450;
		else if(result * 8.51 < 978)
		r = 978;
		else
		r = (int)(result * 8.51);
		return r;
	}

	public static String calculatetodistance(double distance){
        if((int)(result*distance) < 60){
			BigDecimal big = new BigDecimal(result*distance);
			return big.setScale(2,BigDecimal.ROUND_DOWN).doubleValue() + MainActivity.getContext().getString(R.string.seconds);
		}
		long result_this = Math.round(result * distance);
		/*if(result_this <= 60){
	    return result_this + "�b";
	    }*/
		if(result_this <= 3600){
			int minutes = (int)Math.floor(result_this/60);result_this -= minutes * 60;
			int seconds = (int)result_this;
			return minutes + MainActivity.getContext().getString(R.string.minutes) +
					seconds + MainActivity.getContext().getString(R.string.seconds);
		}
		if(result_this <= 86400){
			int hour = (int)Math.floor(result_this/3600);
			int minutes = (int)Math.floor((result_this - 3600*hour)/60);
			int seconds = Math.round(result_this - (3600*hour + 60*minutes));
			return hour + MainActivity.getContext().getString(R.string.hours) +
					minutes + MainActivity.getContext().getString(R.string.minutes) +
					seconds + MainActivity.getContext().getString(R.string.seconds);
		}
		else{
			int day = (int)Math.floor(result_this/86400);result_this -= day*86400;
			int hour = (int)Math.floor(result_this/3600);result_this -= hour*3600;
			int minutes = (int)Math.floor(result_this/60);result_this -= minutes*60;
			int seconds = (int)result_this;

			if(day>=365){
				int year = (int)(Math.floor(day/365));
				day -= 365 * year;

				day += Math.floor(year/4);
				if(day>=365)
					year += (int)(Math.floor(day/365));

				return year + MainActivity.getContext().getString(R.string.year) +
						day + MainActivity.getContext().getString(R.string.days) +
						hour + MainActivity.getContext().getString(R.string.hours) +
						minutes + MainActivity.getContext().getString(R.string.minutes) +
						seconds + MainActivity.getContext().getString(R.string.seconds);
			}

			return day + MainActivity.getContext().getString(R.string.days) +
					hour + MainActivity.getContext().getString(R.string.hours) +
					minutes + MainActivity.getContext().getString(R.string.minutes) +
					seconds + MainActivity.getContext().getString(R.string.seconds);
		}
	}

	public void OnButtonClick_gotoresult(View view){
		setContentView(R.layout.activity_result);
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		flagofKM = true;
		viewparam = 4;
		TextView textview1 = findViewById(R.id.textView1);
		TextView textview2 = findViewById(R.id.textView3);
		TextView textview3 = findViewById(R.id.textView5);
		TextView textview4 = findViewById(R.id.textView8);
		TextView textview5 = findViewById(R.id.textView10);
		TextView textview6 = findViewById(R.id.textView12);
		TextView textview7 = findViewById(R.id.textView14);
		TextView textview8 = findViewById(R.id.textView16);
		TextView textview9 = findViewById(R.id.textView18);
		TextView textview10 = findViewById(R.id.textView21);

		textview1.setText(inputeddata);
		textview2.setText(calculatetodistance(1));
		textview3.setText(Akph + "km/h");
		textview4.setText(calculatetodistance(0.1));
		textview5.setText(calculatetodistance(0.4));
		textview6.setText(calculatetodistance(5));
		textview7.setText(calculatetodistance(10));
		textview8.setText(calculatetodistance(21.0975));
		textview9.setText(calculatetodistance(42.195));
		textview10.setText(getString(R.string.youcancalculate));

	}

	//activity_result
	public void gotoresult(){
		setContentView(R.layout.activity_result);
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		flagofKM = true;
		viewparam = 4;
		TextView textview1 = findViewById(R.id.textView1);
		TextView textview2 = findViewById(R.id.textView3);
		TextView textview3 = findViewById(R.id.textView5);
		TextView textview4 = findViewById(R.id.textView8);
		TextView textview5 = findViewById(R.id.textView10);
		TextView textview6 = findViewById(R.id.textView12);
		TextView textview7 = findViewById(R.id.textView14);
		TextView textview8 = findViewById(R.id.textView16);
		TextView textview9 = findViewById(R.id.textView18);
		TextView textview10 = findViewById(R.id.textView21);

		textview1.setText(inputeddata);
		textview2.setText(calculatetodistance(1));
		textview3.setText(Akph + "km/h");
		textview4.setText(calculatetodistance(0.1));
		textview5.setText(calculatetodistance(0.4));
		textview6.setText(calculatetodistance(5));
		textview7.setText(calculatetodistance(10));
		textview8.setText(calculatetodistance(21.0975));
		textview9.setText(calculatetodistance(42.195));
		textview10.setText(getString(R.string.youcancalculate));

	}

	public void OnButtonClick_gotormotr(View view){
		viewparam = 7;
		setContentView(R.layout.activity_result_more);
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		TextView textview1 = findViewById(R.id.textView2);
		TextView textview2 = findViewById(R.id.textView4);
		TextView textview3 = findViewById(R.id.textView6);
		TextView textview4 = findViewById(R.id.textView8);
		TextView textview5 = findViewById(R.id.textView10);
		textview1.setText(calculatetodistance(34.5));
		textview2.setText(calculatetodistance(545));
		textview3.setText(calculatetodistance(40000));
		textview4.setText(calculatetodistance(384400));
		textview5.setText(calculatetodistance(1));
	}

	public void OnButtonClick_backfromrmotr(View view){
		viewparam = 4;
		gotoresult();
	}

	public void OnButtonClick_valuation(View view){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=root.main"));
		startActivity(intent);                                  //market://details?id=raising.simako
	}

	//result
	public void OnButtonClick_dot2(View view){
		EditText edittext = findViewById(R.id.editText1);
		edittext.append(".");
	}

	public void OnButtonClick_chengeunit2(View view){
		Button button = findViewById(R.id.button2);
		flagofKM = !flagofKM;
		if(flagofKM){
			button.setText("km");
		}else{
			button.setText("m");
		}
	}

	public void OnButtonClick_calculate(View view){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		TextView textview1 = findViewById(R.id.textView19);//����
		TextView textview2 = findViewById(R.id.textView20);//����
		EditText edittext = findViewById(R.id.editText1);

		for(;;){
			if("".equals(edittext.getText().toString())){
				Toast.makeText(this, getString(R.string.notext), Toast.LENGTH_SHORT).show();
				break;
			}
			if((edittext.getText().toString()).split(".").length > 2){
				Toast.makeText(this, getString(R.string.twodots), Toast.LENGTH_SHORT).show();
				break;
			}
			if(flagofKM){
				textview1.setText(calculatetodistance(Double.parseDouble(edittext.getText().toString())));
				textview2.setText(edittext.getText().toString() + "km");
			}
			else{
				textview1.setText(calculatetodistance(Double.parseDouble(edittext.getText().toString())/1000));
				textview2.setText(Double.parseDouble(edittext.getText().toString()) + "m");
			}
			textview1.setTextSize(21);
			textview2.setTextSize(21);
			break;
		}
	}

	public void OnButtonClick_copy(View view){
		TextView textview = findViewById(R.id.textView3);
		ClipData.Item item = new ClipData.Item(textview.getText() + "/km");
		String[] mineType = {ClipDescription.MIMETYPE_TEXT_URILIST};
		ClipData clipdata = new ClipData(new ClipDescription("resultofavarageone",mineType),item);
		ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		cm.setPrimaryClip(clipdata);
		Toast.makeText(this, getString(R.string.copied1) + textview.getText() +
				getString(R.string.copied2), Toast.LENGTH_SHORT).show();
	}

	public void showinput(){
		setContentView(R.layout.activity_input);
		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		spinner = findViewById(R.id.spinner1);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
				this,
				R.array.action_list,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapter);

    	spinner.setSelection(0);
    	spinner.setOnItemSelectedListener(new
            AdapterView.OnItemSelectedListener(){
    //	    public void OnItemSelected(AdapterView<?> parent,View view,int position,long id){}
    //		public void OnNothingSelected(AdapterView parent){}

    		@Override
    		public void onItemSelected( AdapterView<?> arg0,  View arg1, int arg2,long arg3) {
    			    Spinner spinner = (Spinner)arg0;
				String item = (String)spinner.getSelectedItem();
				EditText edittext = findViewById(R.id.editText1);
				edittext.selectAll();
				edittext.requestFocus();
				if("800m".equals(item)){
					if(flagofKM)
						edittext.setText("0.8");
					else
						edittext.setText("800");
				}else if("1500m".equals(item)){
					if(flagofKM)
						edittext.setText("1.5");
					else
						edittext.setText("1500");
				}else if("3000m".equals(item)){
					if(flagofKM)
						edittext.setText("3.0");
					else
						edittext.setText("3000");
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
    	});

    	pref1 = PreferenceManager.getDefaultSharedPreferences(this);
    	autocompletedata = pref1.getString(getString(R.string.autotexts), "").split(" ");
    	AutoCompleteTextView edittext1 = findViewById(R.id.editText1);
		ArrayAdapter<String> edittextadapter= new ArrayAdapter<String>(this, R.layout.listitem, autocompletedata);
		edittext1.setAdapter(edittextadapter);
		edittext1.setThreshold(1);

	}

	@Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode==KeyEvent.KEYCODE_BACK){
	       if(viewparam == 2){
	    	  setContentView(R.layout.activity_main);
	    	  viewparam = 1;
	      }
	       else if(viewparam == 1){
	    	  moveTaskToBack(true);
	      }
	       else if(viewparam == 3){
	    	   viewparam = 2;
		       inputtype = 1;
		       showinput();

	    	  EditText edittext1 = findViewById(R.id.editText1);
	       	  EditText edittext2 = findViewById(R.id.editText2);
	      	  EditText edittext3 = findViewById(R.id.editText3);
	      	  EditText edittext4 = findViewById(R.id.editText4);
	      	  if(distance != 0)
	      	  edittext1.setText(distance + "");
	      	  if(hours != 0)
	      	  edittext2.setText(hours + "");
	      	  if(minutes != 0)
	      	  edittext3.setText(minutes + "");
	      	  if(seconds != 0)
	      	  edittext4.setText(seconds + "");
	      }else if(viewparam == 7){
	    	  viewparam = 4;
	    	  gotoresult();
	      }
	       else{
               if(inputtype == 1){
                   viewparam = 2;
                   inputtype = 1;
                   showinput();
               }else{
                   setContentView(R.layout.activity_input2);
                   viewparam = 2;
                   inputtype = 2;
               }
           }
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == R.id.setting){
            //Intent intent = new Intent(this,MyPreferenceActivity.class);
            //startActivity(intent);
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new MainActivity.SettingFragment())
                    .commit();
        }
        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(this, MyPreferenceActivity.class);
                startActivity(intent);
                break;

            case R.id.about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
	protected void onResume() {
	    //TODO Auto-generated method stub
		super.onResume();
		pref1 = PreferenceManager.getDefaultSharedPreferences(this);
		flagofStart = pref1.getBoolean("start", true);
		pref1 = PreferenceManager.getDefaultSharedPreferences(this);
		flagofAnimation = pref1.getBoolean("animation",true);
	}

	public static Context getContext(){
		return mContext;
	}

    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }

}
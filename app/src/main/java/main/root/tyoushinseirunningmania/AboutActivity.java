package main.root.tyoushinseirunningmania;

//import root.main.R;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		PackageManager pm = getPackageManager();
		try{	
			PackageInfo info = pm.getPackageInfo(getPackageName(),0);

			TextView textview1 = findViewById(R.id.textView1);
			textview1.setText("AppName:" + pm.getApplicationLabel(getApplicationInfo()));

			TextView textview2 = findViewById(R.id.textView2);
			textview2.setText("Version:" + info.versionName);
		}catch(NameNotFoundException e){
			//	e.printStackTrace();
		}
	}	
}

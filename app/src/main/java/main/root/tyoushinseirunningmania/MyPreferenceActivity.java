package main.root.tyoushinseirunningmania;

//import root.main.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreferenceActivity extends  PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainActivity.SettingFragment())
                .commit();
	}
}
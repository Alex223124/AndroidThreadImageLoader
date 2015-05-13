package com.example.threadloading;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.widget.AdapterView.OnItemClickListener;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {

	Button btn1;
	ProgressBar prg1;
	EditText edt1;
	LinearLayout loadingSection;
	ListView lst1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn1=(Button)findViewById(R.id.button1);
        edt1=(EditText)findViewById(R.id.edt1);
        lst1=(ListView)findViewById(R.id.lst1);
        prg1=(ProgressBar)findViewById(R.id.prg1);
        loadingSection=(LinearLayout)findViewById(R.id.loader);
        lst1.setOnItemClickListener(this);
    }


    public void downloadImage(View view)
    {
    	
    	
    	Thread myThread = new Thread(new DownloadImageThread());
    	myThread.start();
    }
    private boolean downloadImagefromURL(String urlName) {
		// TODO Auto-generated method stub
		boolean successful = false;
		URL downloadURL = null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		FileOutputStream fileStream = null;
		File file = null;
		try{
			file = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile()
					+"/"+"naruto.png");
			downloadURL = new URL(urlName);
			connection = (HttpURLConnection) downloadURL.openConnection();
			inputStream=connection.getInputStream();
			fileStream = new FileOutputStream(file);
			int read = -1;
			byte[] buffer = new byte[1024];
			while((read=inputStream.read(buffer))!=-1)
			{
				fileStream.write(buffer, 0, read);
			}
			successful = true;
		}catch(Exception e)
		{
			
		}finally{
			MainActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				loadingSection.setVisibility(View.GONE);	
				}
			});
			if(connection!=null)
			{
				connection.disconnect();
			}
			if(inputStream!=null)
			{
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileStream!=null)
			{
				try {
					fileStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return successful;
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



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	} 

	public class DownloadImageThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			MainActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadingSection.setVisibility(View.VISIBLE);
				}
			});
			String urlName = "http://4.bp.blogspot.com/-LCRR03zm0X0/VQdQA6kmIiI/AAAAAAAAAn8/PT6Rrq4-5G8/s1600/NarutoShippuden.png";
	    	downloadImagefromURL(urlName);
	    	for(int i = 0; i < 100; i++)
	    		Log.e("Sandeep", "Hello man");
		}
	}
}

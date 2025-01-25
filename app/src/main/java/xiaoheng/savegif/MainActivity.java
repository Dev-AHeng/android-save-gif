package xiaoheng.savegif;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.net.*;


// 小亨
// 10.12.8

public class MainActivity extends Activity
{

	private EditText editText;
	private Button button;
	private ImageView imageView;

	private Bitmap bitmap;
	//手柄更新的作用
	Handler handler=new Handler(){
		public void handleMessage(Message msg)
		{
			if (msg.what == 111)
			{
				imageView.setImageBitmap(bitmap);
			}
		};
	};
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//初始化组件
		editText = (EditText) findViewById(R.id.mainEditText1);
		button = (Button) findViewById(R.id.mainButton1);
		imageView = (ImageView) findViewById(R.id.mainImageView1);

		//给下载按钮添加一个监听
		button.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0)
				{
					new Thread(t).start();
				}
			});
	}

	//为了下载图片资源，开辟一个新的子线程
	Thread t=new Thread(){
		public void run()
		{
			//下载图片的路径
			String iPath=editText.getText().toString();
			try
			{
				//对资源链接
				URL url=new URL(iPath);
				//打开输入流
				InputStream inputStream=url.openStream();
				//对网上资源进行下载转换位图图片
				bitmap = BitmapFactory.decodeStream(inputStream);
				handler.sendEmptyMessage(111);
				inputStream.close();

				//再一次打开
				inputStream = url.openStream();
				// 根目录
				File file=new File(Environment.getExternalStorageDirectory() + "/tp.gif");
				// File file=new File("/storage/emulated/0/saveImage/"+iPath+".gif");
				FileOutputStream fileOutputStream=new FileOutputStream(file);
				int hasRead=0;
				while ((hasRead = inputStream.read()) != -1)
				{
					fileOutputStream.write(hasRead);
				}
				fileOutputStream.close();
				inputStream.close();
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		};
	};

}

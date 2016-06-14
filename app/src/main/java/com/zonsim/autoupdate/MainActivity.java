package com.zonsim.autoupdate;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkUpdate();
	}
	
	private void checkUpdate() {
		DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		Uri uri = Uri.parse("http://192.168.1.57:8080/app.apk");
		DownloadManager.Request request = new DownloadManager.Request(uri);
		// 设置下载路径和文件名
		System.out.println(uri);
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
		request.setDescription("自动更新应用新版本下载");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		
		request.setMimeType("application/vnd.android.package-archive");
		// 设置为可被媒体扫描器找到
		request.allowScanningByMediaScanner();
		// 设置为可见和可管理
		request.setVisibleInDownloadsUi(true);
		long refernece = dManager.enqueue(request);
		// 把当前下载的ID保存起来
		SharedPreferences sPreferences = getSharedPreferences("downloadcomplete", 0);
		sPreferences.edit().putLong("refernece", refernece).apply();
	}
}

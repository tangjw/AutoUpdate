package com.zonsim.autoupdate.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

/**
 * CopyRight
 * Created by tang-jw on 2016/6/14.
 */
public class UpdateReceiver extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println(context);
		long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		System.out.println(myDwonloadID);
		SharedPreferences sPreferences = context.getSharedPreferences("downloadcomplete", 0);
		long refernece = sPreferences.getLong("refernece", 0);
		if (refernece == myDwonloadID) {
			System.out.println("哈哈哈哈,收到广播");
			String serviceString = Context.DOWNLOAD_SERVICE;
			DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
//			Intent install = new Intent(Intent.ACTION_VIEW);
			Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
			install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
			
			
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//		   	
				DownloadManager.Query query = new DownloadManager.Query();
				query.setFilterById(myDwonloadID);
				Cursor c = dManager.query(query);
				if (c.moveToFirst()) {
					int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
					// 下载失败也会返回这个广播，所以要判断下是否真的下载成功
					if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
						// 获取下载好的 apk 路径
						String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
						// 提示用户安装
						downloadFileUri = Uri.parse("file://" + uriString);
					}
				}
				
			}
			
			install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
			try {
				context.startActivity(install);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
}

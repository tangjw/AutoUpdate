# AutoUpdate
调用DownloadManager下载, 实现应用自动更新
不用写下载模块了
##Android 6.0
    Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadId);
		
	Android 6.0
	uri打印 System.out: content://downloads/my_downloads/70
	6.0以下的结果
	uri打印 System.out: file:///storage/emulated/0/Download/test.apk
##解决:
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
            downloadFileUri = Uri.parse("file://" + uriString);
        }
    }

package com.zonsim.autoupdate.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 获取应用的VersionCode 和 VersionName
 * Created by tang-jw on 2016/6/14.
 */
public class VersionUtil {
	/**
	 * 获取VersionCode
	 *
	 * @param context 上下文
	 * @return 返回int类型VersionCode
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}
	
	/**
	 * 获取VersionCode
	 *
	 * @param context 上下文
	 * @return 返回String类型VersionName
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}
	
}

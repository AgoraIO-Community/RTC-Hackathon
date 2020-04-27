package com.qmt.okhttplibrary.network.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 压缩图片的方法
 * @author  l
 * 
 */
public class CompressImageUtils {
	/**
	 * 压缩图片
	 * @param path path
	 */
	private static void compressImage(String path, File iconFile) {
		Bitmap image = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 90;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 1000) {
			baos.reset();// 重置baos即清空baos
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减少10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		saveFile(bitmap, iconFile);
	}

	/**
	 * 按比例压缩图片
	 *
	 * @param filePath
	 *            需要压缩图片的路径
	 * @param cachePath
	 *            存放压缩后的图片路径
	 * @param toWidth
	 *            压缩的宽度
	 * @param toHeight
	 *            压缩的高度
	 * @return boolean
	 */

	public static boolean getCacheImage(String filePath, File cachePath,
                                        int toWidth, int toHeight) {
		OutputStream out = null;
		BitmapFactory.Options option = new BitmapFactory.Options();
		// 设置为true，只读尺寸信息，不加载像素信息到内存
		option.inJustDecodeBounds = true;
		option.inJustDecodeBounds = false;
		int bWidth = option.outWidth;
		int bHeight = option.outHeight;
		// be = 1代表不缩放
		int be = 1;
		if (bWidth / toWidth > bHeight / toHeight && bWidth > toWidth) {
			be = bWidth / toHeight;
		} else if (bHeight / toHeight > bWidth / toWidth && bHeight > toHeight) {
			be = bHeight / toHeight;
		} else if (bHeight / toHeight == bWidth / toWidth
				&& (bHeight > toHeight || bWidth > toWidth)) {
			be = bHeight / toHeight;
		}
		// 设置缩放比例
		option.inSampleSize = be;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, option);
		try {
			out = new FileOutputStream(cachePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bitmap.compress(CompressFormat.PNG, 100, out)) {
			compressImage(cachePath.getPath(), cachePath);
			return true;
		}
		return false;
	}

	/**
	 * 保存压缩图片
	 *
	 * @param bm bm
	 * @param dirFile dir
	 */
	public static void saveFile(Bitmap bm, File dirFile) {
		if (dirFile == null) {
			return;
		}
		// 检测图片是否存在
		if (dirFile.exists()) {
			// 删除原图片
			dirFile.delete();
		}
		try {
			BufferedOutputStream bos;
			bos = new BufferedOutputStream(new FileOutputStream(dirFile));
			// 100表示不进行压缩，70表示压缩率为30%
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileDelete(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File mFile :files){
				if (mFile.isFile()){
					File deleteFile = new File(mFile.getAbsolutePath());
					// 路径为文件且不为空则进行删除
					if (deleteFile.isFile() && deleteFile.exists()) {
						deleteFile.delete();
					}
				}
			}

		}
	}
}

package com.asdf.funny.images.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.*;

/**
 * 
 * @Description : gif处理工具类
 * @author liuSir
 * @date : 2015-3-24上午10:49:15
 */
public class GifUtils {
	public static void main(String[] args) throws IOException {

		boolean ok = ImageIO.write(createImage(), "gif", new File("C://test.gif"));

		System.out.println("success=" + ok);

	}

	@SuppressWarnings("unused")
	static BufferedImage createImage() {
		IndexColorModel cm = createIndexColorModel();
		BufferedImage im = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_INDEXED, cm);
		Graphics2D g = im.createGraphics();
//		g.setColor(new Color(0, 0, 0, 0)); // transparent
//		g.fillRect(0, 0, 100, 100);
//		g.setColor(Color.RED);
//		g.fillRect(0, 0, 50, 50);
//		g.setColor(Color.GREEN);
//		g.fillRect(50, 50, 50, 50);
//		g.dispose();
		return im;

	}

	static IndexColorModel createIndexColorModel() {
		BufferedImage ex = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
		IndexColorModel icm = (IndexColorModel) ex.getColorModel();
		int SIZE = 256;
		byte[] r = new byte[SIZE];
		byte[] g = new byte[SIZE];
		byte[] b = new byte[SIZE];
		byte[] a = new byte[SIZE];
		icm.getReds(r);
		icm.getGreens(g);
		icm.getBlues(b);
		java.util.Arrays.fill(a, (byte) 255);
		r[0] = g[0] = b[0] = a[0] = 0; // transparent
		return new IndexColorModel(8, SIZE, r, g, b, a);
	}
	
	/**
	 * 
	 * @Description: 根据图片宽度进行压缩
	 * @throws
	 * @param srcImgPath 源文件路径
	 * @param outImgPath 文件输出路径
	 * @param width 想要压缩的宽度
	 */
	public static void resizeByWidth(File srcFile, String outImgPath,int width){
		try
		{
			GifDecoder gd = new GifDecoder();
			int status = gd.read(new FileInputStream(srcFile));
			if (status != GifDecoder.STATUS_OK) {
				return;
			}
			doGifByWidth(outImgPath, width, gd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: 根据图片宽度进行压缩
	 * @throws
	 * @param srcImgPath 源文件路径
	 * @param outImgPath 文件输出路径
	 * @param width 想要压缩的宽度
	 */
	public static void resizeByWidth(String srcImgPath, String outImgPath,
			int width){
		try
		{
			GifDecoder gd = new GifDecoder();
			int status = gd.read(new FileInputStream(new File(srcImgPath)));
			if (status != GifDecoder.STATUS_OK) {
				return;
			}
			doGifByWidth(outImgPath, width, gd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void resizeByWidthMy(File srcImgPath, String outImgPath){
		try
		{
			GifDecoder gd = new GifDecoder();
			int status = gd.read(new FileInputStream(srcImgPath));
			if (status != GifDecoder.STATUS_OK) {
				return;
			}
			doGifByWidth(outImgPath, 200, gd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void doGifByWidth(String outImgPath, int width, GifDecoder gd)
			throws FileNotFoundException
	{
		AnimatedGifEncoder ge = new AnimatedGifEncoder();
		OutputStream outputStream = new FileOutputStream(new File(outImgPath));
		ge.start(outputStream);
		ge.setRepeat(0);
		for (int i = 0; i < gd.getFrameCount(); i++) {
			BufferedImage frame = gd.getFrame(i);
			
			int w = (int)(frame.getWidth()*0.5);
			int height = (int)(frame.getHeight()*0.5);

			BufferedImage rescaled = Scalr.resize(frame, Mode.FIT_EXACT, w, height);
			int delay = gd.getDelay(i);
			
			ge.setDelay(delay);
			ge.addFrame(rescaled);
		}
		ge.finish();
		try
		{
			outputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}

package com.montnets.android.zmon;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class QRCodeBitmap extends Activity {

	private ImageView iv_2DCodeImage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.qrcode_layout);
		ExitApplication.getInstance().addActivity(this);
		String codesString = getIntent().getStringExtra("CodeString");
		System.out.print("QRCodeBitmap-二维码信息：" + codesString);
		iv_2DCodeImage = (ImageView)findViewById(R.id.iv_zxing);
		
		try {
			iv_2DCodeImage.setImageBitmap(createQRCode(codesString, 400));
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用字符串生成二维码
	 * @param str
	 * @author 
	 */
	private Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 800, 800);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;// 黑色
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}

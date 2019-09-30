package com.fly.viewlibrary.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fly.viewlibrary.utils.BitmapUtils;


/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.viewlibrary.text
 * 作    者 : FLY
 * 创建时间 : 2019/9/26
 * 描述: 文字图片，这个相信大家都知道，比如QQ底部导航上的未读消息数
 */

@SuppressLint("AppCompatCustomView")
public class ImageTextView extends TextView {
	private Bitmap bitmap;
	private String text;
	Drawable d;

	public ImageTextView(Context context) {
		super(context);
		
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	public void setIconText(Context context,String text){
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapUtils.getIndustry(context, text);
		d = BitmapUtils.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

}

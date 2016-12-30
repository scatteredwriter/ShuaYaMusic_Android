package com.shuaya.rodchongstudio.shuayamusic.widget;

/**
 * Created by RodChong on 2016/12/27.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuaya.rodchongstudio.shuayamusic.R;

public class RoundImageView extends ImageView {
    private Paint paint = new Paint();
    private int border_width;
    private int border_color;

    public RoundImageView(Context paramContext) {
        this(paramContext, null);
    }

    public RoundImageView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public RoundImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RoundImageView);
        this.border_width = typedArray.getInteger(R.styleable.RoundImageView_BorderWidth, 0);
        this.border_color = typedArray.getColor(R.styleable.RoundImageView_BorderColor, 0x55F5F5F5);
        typedArray.recycle();
    }

    private Bitmap getCircleBitmap(Bitmap paramBitmap, int paramInt) {
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());

        this.paint.setAntiAlias(true);
        localCanvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(0xFFa5a5a5);
        int i = paramBitmap.getWidth();
        localCanvas.drawCircle((i / 2), (i / 2), ((i - border_width) / 2), this.paint);

        this.paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        localCanvas.drawBitmap(paramBitmap, localRect, localRect, this.paint);

        return localBitmap;
    }

    protected void onDraw(Canvas paramCanvas) {
        Drawable localDrawable = getDrawable();
        if (localDrawable != null) {
            Bitmap localBitmap = getCircleBitmap(((BitmapDrawable) localDrawable).getBitmap(), 14);
            Rect localRect1 = new Rect(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
            Rect localRect2 = new Rect(0, 0, getWidth(), getHeight());
            this.paint.reset();
            paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, this.paint);

            Paint paint = new Paint();
            paint.setColor(border_color);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(border_width);
            int i = localBitmap.getWidth();
            paramCanvas.drawCircle((i), (i), (i - border_width / 2), paint);

        }
    }
}
package com.shuaya.rodchongstudio.shuayamusic.Widget;

/**
 * Created by RodChong on 2016/12/27.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundImageView extends ImageView {
    private Paint paint = new Paint();

    public RoundImageView(Context paramContext) {
        this(paramContext, null);
    }

    public RoundImageView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public RoundImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    private Bitmap getCircleBitmap(Bitmap paramBitmap, int paramInt) {
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
        this.paint.setAntiAlias(true);
        localCanvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(0xff424242);
        int i = paramBitmap.getWidth();
        localCanvas.drawCircle(i / 2, i / 2, i / 2, this.paint);
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
            return;
        }
        super.onDraw(paramCanvas);
    }
}
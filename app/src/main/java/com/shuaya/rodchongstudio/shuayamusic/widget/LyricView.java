package com.shuaya.rodchongstudio.shuayamusic.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.shuaya.rodchongstudio.shuayamusic.models.lyricmodels.Lyric;
import com.shuaya.rodchongstudio.shuayamusic.models.lyricmodels.LyricLine;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RodChong on 2017/1/3.
 */

public class LyricView extends View {

    private Lyric lyric;
    private int textsize = 50;
    private int lineheight = 55;
    private float shade_height = 0;
    private int current_line_color = 0xFFFFFFFF;
    private int default_color = 0xFFdddddd;
    private Paint lyric_paint = new Paint();

    private int current_line = 0;
    private float y_offest = 0f;

    public LyricView(Context context) {
        super(context);
        setLyricPaint();
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLyricPaint();
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLyricPaint();
    }

    private void setLyricPaint() {
        lyric_paint.setTextAlign(Paint.Align.CENTER);
        lyric_paint.setAntiAlias(true);
        lyric_paint.setTextSize(textsize);
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setLyric(String source) {
        if (source.contains("[00:00:00]"))
            return;
        Lyric lyric = new Lyric();
        lyric.setLyric(new ArrayList<LyricLine>());
        String regExp = "\\[.*\\]";
        Matcher m = Pattern.compile(regExp).matcher(source);
        while (m.find()) {
            String group = m.group();
            group = group.replaceAll("\\[|\\]", "");
            if (group.contains("ti:"))
                lyric.setTitle(group.substring(3));
            else if (group.contains("ar:"))
                lyric.setSinger(group.substring(3));
            else if (group.contains("al:"))
                lyric.setAlbum(group.substring(3));
            else if (group.contains("by:"))
                lyric.setBy(group.substring(3));
            else if (group.contains("offset:"))
                lyric.setOffset(Integer.decode(group.substring(7)));
        }
        regExp = "\\[\\d+:\\d+\\.\\d+\\].*$";
        m = Pattern.compile(regExp, Pattern.MULTILINE).matcher(source);
        for (int i = 0; m.find(); i++) {
            String group = m.group();
            try {
                String content = group.replaceAll("\\[\\d+:\\d+\\.\\d+\\]", "");
                if (content.isEmpty())
                    continue;
                String time = group.replaceAll("\\[|\\].*$", "");
                int min, sec, mill;
                try {
                    min = Integer.decode(time.substring(0, time.indexOf(':')));
                } catch (NumberFormatException e) {
                    min = Integer.decode(time.substring(1, time.indexOf(':')));
                }
                try {
                    sec = Integer.decode(time.substring(time.indexOf(':') + 1, time.indexOf('.')));
                } catch (NumberFormatException e) {
                    sec = Integer.decode(time.substring(time.indexOf(':') + 2, time.indexOf('.')));
                }
                try {
                    mill = Integer.decode(time.substring(time.indexOf('.') + 1));
                } catch (NumberFormatException e) {
                    mill = Integer.decode(time.substring(time.indexOf('.') + 2));
                }
                LyricLine lyricLine = new LyricLine();
                lyricLine.setStart_time(mill + sec * 1000 + min * 60 * 1000);
                lyricLine.setContent(content);
                lyric.getLyric().add(lyricLine);
            } catch (Exception e) {
                break;
            }
        }
        this.lyric = lyric;
    }

    public void setTextsize(int textsize) {
        this.textsize = textsize;
    }

    public void setLineheight(int lineheight) {
        this.lineheight = lineheight;
    }

    public void ProgressChanged(int position) {
        if (lyric != null) {
            for (int i = 0; i < lyric.getLyric().size(); i++) {
                if (i < lyric.getLyric().size() - 1) {
                    if (position >= lyric.getLyric().get(i).getStart_time() && position < lyric.getLyric().get(i + 1).getStart_time()) {
                        if (current_line == i)
                            return;
                        current_line = i;
                        AutoScroll(MeasureLineHeight(current_line));
                    }
                } else if (i == lyric.getLyric().size() - 1) {
                    if (position >= lyric.getLyric().get(lyric.getLyric().size() - 1).getStart_time()) {
                        if (current_line == i)
                            return;
                        current_line = i;
                        AutoScroll(MeasureLineHeight(current_line));
                    }
                }
            }
        }
    }

    private float MeasureLineHeight(int to_index) {
        float height = 0f;
        if (to_index >= 0) {
            Rect rect = new Rect();
            for (int i = 0; i <= to_index; i++) {
                lyric_paint.getTextBounds(lyric.getLyric().get(i).getContent(), 0, lyric.getLyric().get(i).getContent().length(), rect);
                height += ((lineheight + rect.height()));
            }
        }
        return height;
    }

    private void AutoScroll(final float deltaY) {
        ValueAnimator animator = ValueAnimator.ofFloat(y_offest, deltaY);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y_offest = (float) animation.getAnimatedValue();
                invalidateView();
            }
        });
        animator.setDuration(700L);
/*        animator.setInterpolator(new OvershootInterpolator(0.5f));*/
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        shade_height = getMeasuredHeight() * 0.1f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long start_height = 0L;
        Rect rect = new Rect();
        if (lyric == null || lyric.getLyric().isEmpty())
            return;
        for (int i = 0; i < lyric.getLyric().size(); i++) {
            String source = lyric.getLyric().get(i).getContent();
            float x = getMeasuredWidth() * 0.5f; //控件的水平中心
            lyric_paint.getTextBounds(source, 0, source.length(), rect);
            float y = getMeasuredHeight() * 0.5f + start_height - y_offest; //该行歌词的起始竖直方向位置
            start_height += (lineheight + rect.height());
            if (y > getMeasuredHeight()) //歌词所在的位置超出控件范围
                return;
            else if (y < 0)
                continue;

            if (i == current_line)
                lyric_paint.setColor(current_line_color);
            else
                lyric_paint.setColor(default_color);
            if (y < shade_height)
                lyric_paint.setAlpha(26 + (int) (23000.0f * y / shade_height * 0.01f));
            else if (y > getMeasuredHeight() - shade_height)
                lyric_paint.setAlpha(26 + (int) (23000.0f * (getMeasuredHeight() - y) / shade_height * 0.01f));

            if (rect.width() <= getMeasuredWidth()) {
                canvas.drawText(source, x, y, lyric_paint);
            } else {
                int time = rect.width() / getMeasuredWidth();
                int first_index = 0, last_index;
                for (int j = 0; j < time + 1; j++) {
                    last_index = (int) ((double) source.length() / (time + 1) * (j + 1));
                    String current = source.substring(first_index, last_index);
                    canvas.drawText(current, x, y, lyric_paint);
                    lyric_paint.getTextBounds(current, 0, current.length(), rect);
                    y = getMeasuredHeight() * 0.5f + start_height - y_offest; //该行歌词的起始竖直方向位置
                    if (y > getMeasuredHeight()) //歌词所在的位置超出控件范围
                        return;
                    if (y <= shade_height)
                        lyric_paint.setAlpha(26 + (int) (23000.0f * y / shade_height * 0.01f));
                    else if (y >= getMeasuredHeight() - shade_height)
                        lyric_paint.setAlpha(26 + (int) (23000.0f * (getMeasuredHeight() - y) / shade_height * 0.01f));
                    start_height += (lineheight + rect.height());
                    first_index = last_index;
                }
                start_height -= (lineheight + rect.height());
            }
        }
    }
}

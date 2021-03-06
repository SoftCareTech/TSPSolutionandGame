package softcare.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import softcare.game.R;
import softcare.game.model.CodeX;

public class PlotGame extends PlotTSP {


    private Paint highlightPaint;
    private int highlightColor;
    private int positionColor;

    public int getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public int getPositionColor() {
        return positionColor;
    }

    public void setPositionColor(int positionColor) {
        this.positionColor = positionColor;
    }

    public PlotGame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public PlotGame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public PlotGame(Context context) {
        super(context);
    }

    private OnPointListener onPointListener;

    public void setOnPointListener(OnPointListener onPointListener) {
        this.onPointListener = onPointListener;
    }

    public OnPointListener getOnPointListener() {
        return onPointListener;
    }

    @Override
    protected void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);

        highlightPaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PlotTSP, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            highlightColor = a.getInteger(R.styleable.PlotGame_highlightColor, Color.MAGENTA);
            positionColor = a.getInteger(R.styleable.PlotGame_positionColor, Color.BLUE);
        } finally {
            a.recycle();
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(CodeX.tag, "motion");
                if (onPointListener != null) {
                    int index = onPointXY(event.getX(), event.getY());
                    if (index > -1) onPointListener.onPoint(index);
                    Log.d(CodeX.tag, "index view e " + index);
                }
                return false;
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.setColorsAndSizes();
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setAntiAlias(true);
        highlightPaint.setColor(highlightColor);
        highlightPaint.setStrokeWidth(5f);
        drawWithZoom(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void drawWithZoom(Canvas canvas) {
        if (zoom <= 0) zoom = initZoom;
        float x;
        float y;
        int i = 0;
        if (pointXY != null)
            for (PointXY p : pointXY) {
                x = getXZoom(p.x);
                y = getYZoom(p.y);
                canvas.drawCircle(x, y, circleRadius, circlePaint);
                canvas.drawText(cities.get(i), x, y, labelPaint);
                i++;
            }
        drawPath(canvas);

    }

    protected void drawPath(Canvas canvas) {
        if (zoom < 1) zoom = initZoom;
        if (path == null) return;
        float x;
        float y;
        if (path.size() > 0) {
            x = getXZoom(pointXY.get(path.get(0)).x);
            y = getYZoom(pointXY.get(path.get(0)).y);
            canvas.drawCircle(x, y, circleRadius, highlightPaint);
        }else return;

        Path gc = new Path();
        gc.reset();
        x = getXZoom(pointXY.get(path.get(0)).x);
        y = getYZoom(pointXY.get(path.get(0)).y);
        gc.moveTo(x, y);
        float i_x = x, i_y = y;
        for (int k = 1; k < path.size(); k++) {
            int i = path.get(k);
            x = getXZoom(pointXY.get(i).x);
            y = getYZoom(pointXY.get(i).y);
            gc.lineTo(x, y);
            canvas.drawCircle(x, y, circleRadius, highlightPaint);
        }
        if (pointXY.size() == path.size())
            gc.lineTo(i_x, i_y);
        else {
            highlightPaint.setColor(positionColor);
            canvas.drawCircle(x, y, circleRadius, highlightPaint);

        }
        canvas.drawPath(gc, linePaint);

    }

/// my methods

    public void plotGame(List<PointXY> pointXY, List<String> cities) {
        super.pointXY = pointXY;
        super.cities = cities;
        super.path = new ArrayList<>();
        measureDim();
        refresh();

    }

    public void plotPath(List<Integer> path) {
        super.path = path;
        if (path != null)
            Log.d(CodeX.tag, " path " + path.toString());
        else Log.d(CodeX.tag, " Path null");
        refresh();

    }

    @Override
    public void zoomOut() {
        super.zoomOut();
    }

    @Override
    public void zoomIn() {
        super.zoomIn();
    }

    @Override
    public double getZoom() {
        return super.getZoom();
    }

    @Override
    public void setZoom(double zoom) {
        super.setZoom(zoom);
    }

    private int onPointXY(float x, float y) {
        if(pointXY!=null)
        for (int i = 0; i < pointXY.size(); i++) {
            float xp = getXZoom(pointXY.get(i).x);
            float yp = getYZoom(pointXY.get(i).y);

            if ((xp - circleRadius <= x && xp + circleRadius >= x) &&
                    (yp - circleRadius <= y && yp + circleRadius >= y))
                return i;
        }
        return -1;
    }


}

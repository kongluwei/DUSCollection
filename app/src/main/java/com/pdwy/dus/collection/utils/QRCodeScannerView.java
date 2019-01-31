package com.pdwy.dus.collection.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by MR on 2017/4/6.
 */

public class QRCodeScannerView extends ZXingScannerView {
    public QRCodeScannerView(Context context) {
        super(context);
    }

    public QRCodeScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new ViewFinderView(context);
    }

   private class ViewFinderView extends me.dm7.barcodescanner.core.ViewFinderView{

       public ViewFinderView(Context context) {
           super(context);
       }

       public ViewFinderView(Context context, AttributeSet attrs) {
           super(context, attrs);
       }

       @Override
       public void drawLaser(Canvas canvas) {
           Log.i("-------","========================绘制");
       }
   }
}

package com.example.whatsappresponse.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;

public class UtilItemDecorators {

    public static DividerItemDecoration getSpaceDecorator(Context context,
                                         int shapeDrawable){
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(context, shapeDrawable));
        return itemDecorator;
    }
}

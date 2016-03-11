package com.elclcd.multifunctionclock.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elc-06 on 2016/3/10.
 */
public class TimePickerSize {


    /**
     * 调整FrameLayout大小
     * @param tp
     */
    public  void resizePikcer(FrameLayout tp){
        List<NumberPicker> npList = findNumberPicker(tp);
        for(NumberPicker np:npList){

            resizeNumberPicker(np);

        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }


//    private EditText findEditText(NumberPicker np)
//    {
//        if (null != np)
//        {
//            for (int i = 0; i < np.getChildCount(); i++)
//            {
//                View child = np.getChildAt(i);
//
//                if (child instanceof EditText)
//                {
//                    return (EditText)child;
//                }
//            }
//        }
//
//        return null;
//    }

    /*
		 * 调整numberpicker大小
		 */
    private void resizeNumberPicker(NumberPicker np){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10,0);
        np.setLayoutParams(params);
    }
}

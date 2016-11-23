package godchoose.com.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import godchoose.com.R;
import godchoose.com.util.ToastUtil;
import godchoose.com.widget.randomlayout.ColorUtil;
import godchoose.com.widget.randomlayout.StellarMap;

/**
 * Created by Lannith on 16/11/20.
 */

public class StellarMapAdapter implements StellarMap.Adapter{
    private final int count = 16;
    private ArrayList<String> slist = new ArrayList<>();
    private Context context;

    public StellarMapAdapter(Context context,ArrayList<String> slist) {
        this.slist = slist;
        this.context=context;

    }

    /**
     * 返回多少组数据
     */
    @Override
    public int getGroupCount() {
        if (slist.size() == 0) {
            return 0;
        }
        return slist.size() / count;
    }

    /**
     * 每组多少个数据
     */
    @Override
    public int getCount(int group) {
        return count;
    }

    /**
     * 返回将要加入stellarMap的view对象
     * group:　表示当前是第几组
     * position: 表示是当前组中的位置
     */
    @Override
    public View getView(int group, int position, View convertView) {
        final TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.item_back_for_choice);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        //根据group和position计算出在list中对应的位置
        int listPosition = group * count + position;
        textView.setText(slist.get(listPosition));

        Random random = new Random();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, random.nextInt(6) + 12);//14-21
        textView.setTextColor(ColorUtil.randomColor());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = textView.getText().toString();
                ToastUtil.showToast(keyword);
            }
        });

        return textView;
    }

    /**
     * 并没用什么用
     */
    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }

    /**
     * 当前组执行完缩放动画后下一组加载哪一组的数据
     * group： 当前是第几组
     */
    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        return (group + 1) % getGroupCount();//0->1->2->0
    }

}



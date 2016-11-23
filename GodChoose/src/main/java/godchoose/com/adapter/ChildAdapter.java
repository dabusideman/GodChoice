package godchoose.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import godchoose.com.R;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder>{
    Context mcontext;
    List<String> mlist;
    public ChildAdapter(Context context, List<String> list) {
        mcontext=context;
        mlist=list;
    }




    @Override
    public int getItemCount() {

        return mlist.size();
    }


    //找到布局中空间位置
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View arg0) {
            super(arg0);

            tv=(TextView) arg0.findViewById(R.id.id_num);
        }

    }


    //绑定，渲染数据到view中
    @Override
    public void onBindViewHolder(MyViewHolder holder, int arg1) {

        holder.tv.setText(mlist.get(arg1));

    }



    //先执行onCreateViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mcontext).inflate(R.layout.item_recycal_child, parent,
                false));
        return holder;
    }


}

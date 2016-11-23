package godchoose.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import godchoose.com.R;
import godchoose.com.activity.ProduceDetailActivity;
import godchoose.com.bean.FragmentDataDetailVo;
import godchoose.com.globle.ImageLoaderOptions;


public class CounterGrideAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<FragmentDataDetailVo> list;
	public CounterGrideAdapter(Context context, ArrayList<FragmentDataDetailVo> list){
		this.context=context;
		this.list=list;
		this.inflater=LayoutInflater.from(context);

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		ViewHolder vh=null;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_feal_market_data, null);
			vh.imgCounter= (ImageView) convertView.findViewById(R.id.img_counter);
			vh.tvSpace= (TextView) convertView.findViewById(R.id.tv_space);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(list.get(position).isrc,vh.imgCounter, ImageLoaderOptions.list_options);

		if(position==0||position==1){
			vh.tvSpace.setVisibility(View.VISIBLE);
		}else {
			vh.tvSpace.setVisibility(View.GONE);
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, ProduceDetailActivity.class);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder{
		public ImageView imgCounter;
		private TextView tvSpace;
	}
}

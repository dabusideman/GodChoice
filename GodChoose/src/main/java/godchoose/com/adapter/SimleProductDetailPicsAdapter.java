package godchoose.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import godchoose.com.R;
import godchoose.com.activity.ImageScaleXueZhangActivity;
import godchoose.com.bean.FragmentDataDetailVo;
import godchoose.com.globle.ImageLoaderOptions;
import godchoose.com.globle.GodchooseApplication;


public class SimleProductDetailPicsAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<FragmentDataDetailVo> list;
	public SimleProductDetailPicsAdapter(Context context, ArrayList<FragmentDataDetailVo> list){
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
			convertView=inflater.inflate(R.layout.item_simple_product_detail_pics, null);
			vh.imgProductPic= (ImageView) convertView.findViewById(R.id.img_product_pic);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}

		ViewGroup.LayoutParams lpImage = vh.imgProductPic
				.getLayoutParams();
		lpImage.width = GodchooseApplication.windowWith-80;
		lpImage.height = lpImage.width;
		vh.imgProductPic.setLayoutParams(lpImage);

		ImageLoader.getInstance().displayImage(list.get(position).isrc,vh.imgProductPic, ImageLoaderOptions.list_options);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, ImageScaleXueZhangActivity.class);
				intent.putExtra("imageList", list);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder{
		public ImageView imgProductPic;
	}
}

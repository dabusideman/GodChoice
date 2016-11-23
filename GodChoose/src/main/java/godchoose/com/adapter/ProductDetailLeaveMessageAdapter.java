package godchoose.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import godchoose.com.R;


public class ProductDetailLeaveMessageAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<String> list;
	public ProductDetailLeaveMessageAdapter(Context context, ArrayList<String> list){
		this.context=context;
		this.list=list;
		this.inflater=LayoutInflater.from(context);

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
//		return list.size();
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
			convertView=inflater.inflate(R.layout.item_leave_message, null);
//			vh.imgProductPic= (ImageView) convertView.findViewById(R.id.img_product_pic);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}

//		ViewGroup.LayoutParams lpImage = vh.imgProductPic
//				.getLayoutParams();
//		lpImage.width = GodchooseApplication.windowWith-80;
//		lpImage.height = lpImage.width;
//		vh.imgProductPic.setLayoutParams(lpImage);
//
//		ImageLoader.getInstance().displayImage(list.get(position).isrc,vh.imgProductPic, ImageLoaderOptions.list_options);


		return convertView;
	}

	class ViewHolder{
//		public ImageView imgHead;
	}
}

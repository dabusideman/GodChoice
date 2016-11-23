package xuezhangyouhuo.com.activity.uploadpic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.adapter.uploadpic.AlbumHelper;
import xuezhangyouhuo.com.adapter.uploadpic.ImageGridAdapter;
import xuezhangyouhuo.com.bean.upload.ImageItem;
import xuezhangyouhuo.com.util.bitmaputil.Bimp;


public class ImageGridActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	// ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
	AlbumHelper helper;
	Button bt;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择3张图片", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_grid);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

//		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				if (Bimp.act_bool) {
//					Intent intent = new Intent(ImageGridActivity.this,
//							UpLoadPic.class);
//					startActivity(intent);
//					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < 9) {
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	/**
	 * 鍒濆鍖杤iew瑙嗗浘
	 */
//	private void initView() {
//		gridView = (GridView) findViewById(R.id.gridview);
//		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
//				mHandler);
//		gridView.setAdapter(adapter);
//		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
//			public void onListen(int count) {
//				bt.setText("完成" + "(" + count + ")");
//			}
//		});
//
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// if(dataList.get(position).isSelected()){
//				// dataList.get(position).setSelected(false);
//				// }else{
//				// dataList.get(position).setSelected(true);
//				// }
//				adapter.notifyDataSetChanged();
//			}
//
//		});
//
//	}
}

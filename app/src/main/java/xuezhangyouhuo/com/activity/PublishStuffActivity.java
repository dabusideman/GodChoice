package xuezhangyouhuo.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.activity.uploadpic.PhotoActivity;
import xuezhangyouhuo.com.dialog.UpPictureDialog;
import xuezhangyouhuo.com.util.ToastUtil;
import xuezhangyouhuo.com.util.bitmaputil.Bimp;
import xuezhangyouhuo.com.util.bitmaputil.FileUtils;


/**
 * //左侧点击图片
 * protected TextView iv_home_leftview;
 * //右侧点击图片
 * protected TextView iv_home_rightview;
 * //中间的文本
 * protected TextView tv_home_title;
 * //下面的内容
 * protected FrameLayout fl_home_content;
 * //替换的title
 * protected FrameLayout fl_home_title;
 * //title的内容
 * protected RelativeLayout rl_home_title;
 * //私有吐司点击时可以替换掉上次的吐司
 * private static Toast toast;
 */
public class PublishStuffActivity extends MyBaseActivity {

    public static int picError=0;
    private static final int TAKE_PICTURE = 1;
    public static Uri photoUri;


    private GridView noScrollgridview; // 图片展示
    private GridAdapter adapter; // 图片适配
    public static List<String> mSelectedImage = new LinkedList<String>();
    public static List<Bitmap> mSelectedImageBitmap = new LinkedList<Bitmap>();
    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        mSelectedImage.clear();
        mSelectedImageBitmap.clear();
        View view = View.inflate(this, R.layout.activity_publish_stuff, null);


        noScrollgridview = (GridView) view.findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // showShortToastMessage("点击了");
                if (position == mSelectedImageBitmap.size()) {
                    // 隐藏软键盘
                    // InputMethodManager imm = (InputMethodManager)
                    // getSystemService(Context.INPUT_METHOD_SERVICE);
                    // imm.hideSoftInputFromWindow(findContentEd.getWindowToken(),
                    // 0);
                    try {
                        initDialog();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ToastUtil.showToast("亲，抱歉，您的手机可能不支持该操作哦！");
                        // finish();
                    }
                } else {
                    Intent intent = new Intent(PublishStuffActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });


        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }


    @SuppressLint("HandlerLeak")
    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();


    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;
        Context context;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (PublishStuffActivity.mSelectedImageBitmap.size() == 100) {
                return PublishStuffActivity.mSelectedImageBitmap.size();
            }
            if(PublishStuffActivity.mSelectedImageBitmap.size()<3){

                return (PublishStuffActivity.mSelectedImageBitmap.size() + 1);
            }else {
                return 3;
            }
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

//				convertView = inflater.inflate(R.layout.jingmeitu_item,
//						parent, false);
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == mSelectedImageBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.launch_wall_pick_pic_un));
                if (position == 3) {
                    holder.image.setVisibility(View.GONE);
                } else {
                    holder.image.setVisibility(View.VISIBLE);
                }
            } else {
                try {
                    holder.image.setImageBitmap(PublishStuffActivity.mSelectedImageBitmap.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 弹出图片选择
     * 	tv_photograph = (TextView) view.findViewById(R.id.tv_photograph);
     tv_album = (TextView) view.findViewById(R.id.tv_album);
     tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
     */
    private void initDialog() {
        UpPictureDialog upPictureDialog = new UpPictureDialog(PublishStuffActivity.this);
        upPictureDialog.showDialog();

    }

}

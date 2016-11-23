package godchoose.com.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import godchoose.com.R;
import godchoose.com.activity.PublishStuffActivity;
import godchoose.com.activity.pichoose.imageloder.PicChooseUp;


/**
 * Created by Lannith on 16/3/25.
 */
public class UpPictureDialog {
    private Dialog dialog;
    private View dialogView;
    Activity context;
    String notice;
    public UpPictureDialog(Activity context) {
        this.context = context;
    }

    public void showDialog() {
        dialog = new AlertDialog.Builder(context).create();

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialogView = LayoutInflater.from(context).inflate(
                R.layout.dialog_chatbottom, null);
        TextView tv_photograph = (TextView) dialogView.findViewById(R.id.tv_photograph);
        TextView tv_album = (TextView) dialogView.findViewById(R.id.tv_album);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);;
        dialog.show();

        tv_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SDState = Environment.getExternalStorageState();
                if (SDState.equals(Environment.MEDIA_MOUNTED)) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
                    /***
                     * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
                     * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
                     */
                    ContentValues values = new ContentValues();
                    PublishStuffActivity.photoUri = context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, PublishStuffActivity.photoUri);
                    /** ----------------- */
                    context.startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(context, "内存卡不存在", Toast.LENGTH_LONG).show();
                }

            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PicChooseUp.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setContentView(dialogView);
    }

}

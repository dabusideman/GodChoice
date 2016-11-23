package godchoose.com.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import godchoose.com.R;


/**
 * Created by Lannith on 16/3/25.
 */
public class ChooseSchoolDialog {
    private Dialog dialog;
    private View dialogView;
    Activity context;
    String notice;
    public ChooseSchoolDialog(Activity context, String notice) {
        this.context = context;
        this.notice=notice;
    }

    public void showDialog() {
        dialog = new AlertDialog.Builder(context).create();

        dialog.getWindow().setGravity(Gravity.CENTER);
        dialogView = LayoutInflater.from(context).inflate(
                R.layout.dialog_exchange_success, null);
        TextView tvNotice = (TextView) dialogView.findViewById(R.id.tv_notice);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        dialog.show();

//        tvNotice.setText(notice);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setContentView(dialogView);
    }

}

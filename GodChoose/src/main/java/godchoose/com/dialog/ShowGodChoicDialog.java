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
public class ShowGodChoicDialog {
    public Dialog dialog;
    private View dialogView;
    public TextView tvGodChoice,tvReshake,tvCancel;
    Activity context;
    public ShowGodChoicDialog(Activity context) {
        this.context = context;
    }

    public void showDialog() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialogView = LayoutInflater.from(context).inflate(
                R.layout.dialog_show_god_choice, null);
        tvCancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
        tvGodChoice= (TextView) dialogView.findViewById(R.id.tv_show_choice);
        tvReshake= (TextView) dialogView.findViewById(R.id.tv_reshake);
        dialog.show();

        dialog.getWindow().setContentView(dialogView);
    }

}

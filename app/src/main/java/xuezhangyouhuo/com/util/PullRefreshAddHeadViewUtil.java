package xuezhangyouhuo.com.util;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by Lannith on 16/4/23.
 */
public class PullRefreshAddHeadViewUtil {
    public void addHeadView(Activity context,PullToRefreshListView lvp,int viewR){
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View viewTop=context.getLayoutInflater().inflate(viewR,null);
        viewTop.setLayoutParams(layoutParams);
        ListView lv = lvp.getRefreshableView();
        lv.addHeaderView(viewTop);
    }

}

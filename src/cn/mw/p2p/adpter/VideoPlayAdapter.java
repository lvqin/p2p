package cn.mw.p2p.adpter;

import java.util.ArrayList;

import com.montnets.android.zmon.R;

import cn.mw.p2p.bean.VedioPointBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoPlayAdapter extends BaseAdapter {

	private Context context;
	ArrayList<VedioPointBean> vedioPointsList;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VideoPlayAdapter(Context paramContext,ArrayList<VedioPointBean> paramArrayList) {
		this.context = paramContext;
		if (paramArrayList != null)
		{
			this.vedioPointsList = new ArrayList();
			this.vedioPointsList = paramArrayList;
		}
	}

	public int getCount() {
		return this.vedioPointsList.size();
	}

	public Object getItem(int paramInt) {
		return this.vedioPointsList.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		viewHoder localviewHoder;
		if (paramView == null) {
			paramView = LayoutInflater.from(this.context).inflate(R.layout.dialog_gallary_item, null, false);
			localviewHoder = new viewHoder();
			localviewHoder.imageView = ((ImageView) paramView.findViewById(R.id.ivplay));
			localviewHoder.textview = ((TextView) paramView.findViewById(R.id.txtDevName));
			paramView.setTag(localviewHoder);
			VedioPointBean localVedioPointBean = (VedioPointBean) getItem(paramInt);
			localviewHoder.textview.setText(localVedioPointBean.getName());
			if (!localVedioPointBean.isOnline())
			{
				localviewHoder.imageView.setBackgroundResource(R.drawable.p_off);
			}
			else {
				localviewHoder.imageView.setBackgroundResource(R.drawable.p);
			}
		} else {
			localviewHoder = (viewHoder) paramView.getTag();
		}
		return paramView;
	}

	public final class viewHoder {
		ImageView imageView;
		TextView textview;
	}

}

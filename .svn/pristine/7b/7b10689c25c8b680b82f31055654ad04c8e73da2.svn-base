package cn.mw.p2p.adpter;

import java.util.ArrayList;

import com.montnets.android.zmon.R;
import com.montnets.android.zmon.RecpolicyList;
import com.montnets.android.zmon.SafepolicyListSet;

import cn.mw.p2p.bean.VedioPointBean;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivityAdapter extends BaseAdapter {

	private Context context;
	ArrayList<VedioPointBean> vedioPointsList;
	private String strSkipType;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RecordActivityAdapter(Context paramContext,ArrayList<VedioPointBean> paramArrayList,String SkipType) {
		this.context = paramContext;
		if (paramArrayList != null)
		{
			this.vedioPointsList = new ArrayList();
			this.vedioPointsList = paramArrayList;
			this.strSkipType = SkipType;
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
			paramView = LayoutInflater.from(this.context).inflate(R.layout.recorditem, null, false);
			localviewHoder = new viewHoder();
			localviewHoder.ivonlie = (ImageView) paramView.findViewById(R.id.ivOnline);
			localviewHoder.textview = ((TextView) paramView.findViewById(R.id.txtName));
			paramView.setTag(localviewHoder);
			final VedioPointBean localVedioPointBean = (VedioPointBean) getItem(paramInt);
			if(localVedioPointBean.isOnline())
			{
				localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp);
			}
			else {
				localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp2);
			}
			localviewHoder.textview.setText(localVedioPointBean.getName());
			//点击事件
			localviewHoder.textview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!localVedioPointBean.isOnline())
					{
						Toast.makeText(context, "设备不在线，无法进行参数配置！", 0).show();
						return;
					}
					if(strSkipType.equals("0"))
					{
						Intent intent = new Intent(context, RecpolicyList.class);
						intent.putExtra("DevID", localVedioPointBean.getDevID());
						intent.putExtra("ChannelNO", localVedioPointBean.getChannelNo());
						context.startActivity(intent);
						System.out.println("录像策略");
					}
					else {
						Intent intent = new Intent(context, SafepolicyListSet.class);
						intent.putExtra("DevID", localVedioPointBean.getDevID());
						intent.putExtra("ChannelNO", localVedioPointBean.getChannelNo());
						context.startActivity(intent);
						System.out.println("安全策略");
					}
				}
			});

		} else {
			localviewHoder = (viewHoder) paramView.getTag();
		}
		return paramView;
	}

	public final class viewHoder {
		ImageView ivonlie;
		TextView textview;
	}

}

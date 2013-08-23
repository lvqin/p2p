package cn.mw.p2p.adpter;

import java.util.List;
import java.util.Map;

import com.montnets.android.zmon.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mListItem;
	
	public FileListAdapter(List<Map<String, Object>> mListItem,Context context)
	{
		this.mInflater = LayoutInflater.from(context);
		this.mListItem = mListItem;
	}

	@Override
	public int getCount() {
		return mListItem.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.filelistviewitem, null);
			holder.filename = (TextView) convertView.findViewById(R.id.txtFileName);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgicon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.filename.setText((String) mListItem.get(position).get("fileName"));

		return convertView;
	}
	
	public final class ViewHolder {
		public TextView filename;//文件名称
		public ImageView imgIcon;//图标
	}

}

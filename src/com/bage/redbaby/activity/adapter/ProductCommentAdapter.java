package com.bage.redbaby.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.domain.ProductCommentBean.Comment;
/**
 * 购买评论数据适配器
 * @author jun
 *
 */
public class ProductCommentAdapter extends BaseAdapter {
/**
 * 购买评论集合
 */
private List<Comment> comment;
private Context context;

	public ProductCommentAdapter(Context context, List<Comment> comment) {
	super();
	this.context = context;
	this.comment = comment;
}

	@Override
	public int getCount() {
		return comment.size();
	}

	@Override
	public Object getItem(int position) {
		return comment.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hodler;
		if (convertView!=null && convertView instanceof RelativeLayout) {
			hodler=(ViewHolder) convertView.getTag();
		}else{
			convertView=View.inflate(context, R.layout.prod_comments_items, null);
			hodler=new ViewHolder();
			hodler.textTitleIcon=(TextView) convertView.findViewById(R.id.textTitleIcon);
			hodler.textComTitle=(TextView) convertView.findViewById(R.id.textComTitle);
			hodler.textClothesDetail=(TextView) convertView.findViewById(R.id.textClothesDetail);
			hodler.textComFrom=(TextView) convertView.findViewById(R.id.textComFrom);
			hodler.textComDate=(TextView) convertView.findViewById(R.id.textComDate);
			convertView.setTag(hodler);
		}
		hodler.textComTitle.setText(comment.get(position).title);
		hodler.textClothesDetail.setText(comment.get(position).content);
		hodler.textComFrom.setText(comment.get(position).username);
		hodler.textComDate.setText(comment.get(position).time);
		return convertView;
	}
	static class ViewHolder {
		TextView textTitleIcon;//
		TextView textComTitle;//评论标题
		TextView textClothesDetail;//评论内容
		TextView textComFrom;//评论者
		TextView textComDate;//评论时间
	}

}

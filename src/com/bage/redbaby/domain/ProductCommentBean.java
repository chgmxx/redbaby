package com.bage.redbaby.domain;

import java.util.List;

/**
 * 商品评论业务bean
 * @author jun
 *
 */
public class ProductCommentBean {
	public List<Comment> comment;
	public String list_count;//评论数量
	public String response;
	
	public class Comment{
		public String content;//评论内容
		public String time;//评论时间
		public String title;//评论标题
		public String username;//评论者
	}
}

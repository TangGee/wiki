package com.ym.bean;

import com.ym.test.annotation.Column;
import com.ym.test.annotation.ID;
import com.ym.test.annotation.TableName;

@TableName("news")
public class News {

	@ID(autoincrement=true)
	@Column("_id")
	private int id;
	
	@Column("title")
	private String title;
	@Column("summary")
	private String summary;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public News( String title, String summary) {
		super();
		this.title = title;
		this.summary = summary;
	}
	public News() {
		super();
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", summary=" + summary
				+ "]";
	}
	
	
	
	
}

package com.gj.test.base.search;

import java.io.Serializable;

import com.gj.test.base.util.ProjectCodeBook;

public class BaseSearch implements Serializable {
	public static final String ISDEL_NO = "0";
	public static final String ISDEL_YES = "1";
	private Integer page = 1;
	private Integer rows = 10;
	private Integer[] selectIds;
	private String order = "asc";
	private String sort = "";
	private int total;
	private String language = "zh";

	public String getLanguage() {
		String lan = "zh";
		int flag = 0;
		for( int i = 0; i < ProjectCodeBook.languageGroup.length; i++ ) {
			if( language.equals( ProjectCodeBook.languageGroup[i] ) ) {
				flag = 1;
				break;
			}
		}
		if( flag == 0 ) {
			language = lan;
		}

		return language;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage( Integer page ) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows( Integer rows ) {
		this.rows = rows;
	}

	public Integer[] getSelectIds() {
		return selectIds;
	}

	public void setSelectIds( Integer[] selectIds ) {
		this.selectIds = selectIds;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder( String order ) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort( String sort ) {
		this.sort = sort;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal( int total ) {
		this.total = total;
	}

	public void setLanguage( String language ) {
		this.language = language;
	}

}

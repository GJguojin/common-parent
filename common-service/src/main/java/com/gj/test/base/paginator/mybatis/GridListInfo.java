package com.gj.test.base.paginator.mybatis;

import java.io.Serializable;
import java.util.ArrayList;

public class GridListInfo implements Serializable
{
	private int total;
	private ArrayList rows = new ArrayList();

	public int getTotal()
	{
		return total;
	}

	public void setTotal( int total )
	{
		this.total = total;
	}

	public ArrayList getRows()
	{
		return rows;
	}

	public void setRows( ArrayList rows )
	{
		this.rows = rows;
	}

}

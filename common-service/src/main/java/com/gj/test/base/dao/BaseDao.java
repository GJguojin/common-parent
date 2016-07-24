package com.gj.test.base.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> {
	public int insert( T entity ) throws Exception;

	public int update( T entity );

	public int delete( @Param("id") long entityId ) throws Exception;

	public T selectById( @Param("id") long entityId );

	public T selectByStrId( @Param("id") String entityId );

	public List<T> selectAll();

}

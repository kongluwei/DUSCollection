package com.pdwy.dus.collection.core;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseListViewAdapter 封装
 * Author： by MR on 2017/3/15.
 */
public abstract class BaseCommonAdapter<T> extends android.widget.BaseAdapter {
	public Context context;
	public List<T> list;
	public LayoutInflater inflater;

	public BaseCommonAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list != null && !list.isEmpty() ? list.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public void update(List<T> list) {
		// 添加数据
		setData(list);
		// 数据有变化
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 *
	 * @param list 任务门店名称集合
	 */
	private void setData(List<T> list) {
		if (list == null) {
			list = new ArrayList<>();
		}
		this.list = list;
	}

	/**
	 * 获取list数据
	 * @return
     */
	public List<T> getData() {
		return list;
	}

	/**
	 * 增加一个元素
	 * @param item
     */
	public void add(T item){
		list.add(item);
	}

	/**
	 * 增加一个list
	 * @param lists
     */
	public void addALL(List<T> lists){
		if(lists==null||lists.size()==0){
			return ;
		}
		list.addAll(lists);
	}

	/**
	 * 移除一个元素
	 * @param item
     */
	public void removeEntity(T item){
		list.remove(item);
	}

	/**
	 * 清除所有元素
	 */
	public void clearAll() {
		list.clear();
	}
}

package com.system.util.core;

/**
 * datatable分页参数
 * @author PangPeijie
 *
 */
public class DataTableParameter {

	private String sEcho;//DataTable请求服务器端次数
	private int iDisplayLength;//每页显示的数量
	private int iDisplayStart;//分页时每页跨度数量
	private int iColumns;//列数
	private int iSortingCols;//排序列的数量
	private String sColumns;//逗号分割所有的列
	
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiColumns() {
		return iColumns;
	}
	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}
	public int getiSortingCols() {
		return iSortingCols;
	}
	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	
	
}

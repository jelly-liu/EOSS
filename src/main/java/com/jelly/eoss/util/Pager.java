package com.jelly.eoss.util;

import java.util.List;

public class Pager {
	private int pageSize;
	private int page;
	private int totalPage;
	private int totalRow;
	private List<?> data;
	
	private int rowStart;
	private int rowEnd;
	
	public Pager() {
		
	}
	
	public Pager(int page, int pageSize, int totalRow) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalRow = totalRow;
		this.init();
	}
	
	private void init(){
		int t = this.totalRow / this.pageSize;
		this.totalPage = this.totalRow % this.pageSize == 0 ? t : t + 1;
		this.rowStart = (this.page - 1) * this.pageSize + 1;
		int t1 = this.rowStart + this.pageSize - 1;
		this.rowEnd = t1 > this.totalRow ? this.totalRow : t1;
	}
	
	public int getFirstPage(){
		return 1;
	}
	
	public int getLastPage(){
		return this.totalPage;
	}
	
	public int getPrePage(){
		return page == 1 ? 1 : page - 1;
	}
	
	public int getNextPage(){
		return page + 1 == this.totalPage ? this.totalPage : this.page + 1;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getRowStart() {
		return rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}
	
}

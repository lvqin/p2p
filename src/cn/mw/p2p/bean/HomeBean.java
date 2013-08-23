﻿package cn.mw.p2p.bean;

import java.util.ArrayList;

public class HomeBean {
	public String name;
	private ArrayList<VedioPointBean> pointsList;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<VedioPointBean> getPointsList() {
		if (this.pointsList == null)
			this.pointsList = new ArrayList();
		return this.pointsList;
	}

	public void setPointsList(ArrayList<VedioPointBean> paramArrayList) {
		this.pointsList = paramArrayList;
	}
}

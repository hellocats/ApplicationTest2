package com.example.administrator.applicationtest2.view.test.tree;

import com.example.administrator.applicationtest2.common.tree.TreeNodeId;
import com.example.administrator.applicationtest2.common.tree.TreeNodeLabel;
import com.example.administrator.applicationtest2.common.tree.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String desc;

	public FileBean(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

}

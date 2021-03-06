package com.example.administrator.applicationtest2.view.test.tree;

import com.example.administrator.applicationtest2.common.tree.TreeNodeId;
import com.example.administrator.applicationtest2.common.tree.TreeNodeLabel;
import com.example.administrator.applicationtest2.common.tree.TreeNodePid;

public class Bean
{
	@TreeNodeId
	private int id;
	@TreeNodePid
	private int pId;
	@TreeNodeLabel
	private String label;

	public Bean()
	{
	}

	public Bean(int id, int pId, String label)
	{
		this.id = id;
		this.pId = pId;
		this.label = label;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getpId()
	{
		return pId;
	}

	public void setpId(int pId)
	{
		this.pId = pId;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

}

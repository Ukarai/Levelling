package com.tribescommunity.levelling.skills;

import java.util.List;

import com.tribescommunity.levelling.abilities.RightClickAbility;

/* 
 * Date: 22 Nov 2012
 * Time: 20:53:11
 * Maker: theguynextdoor
 */
public interface LevellingSkill {

	public String getName();

	public RightClickAbility getAbility();

	public String getXpMethodInfo();

	public List<String> getXpTable(int level);
}

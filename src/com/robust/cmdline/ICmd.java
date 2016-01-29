package com.robust.cmdline;

public interface ICmd {
	void doAction(String... strings);
	String getUseTipRes();
	String getHelpTipRes();
}

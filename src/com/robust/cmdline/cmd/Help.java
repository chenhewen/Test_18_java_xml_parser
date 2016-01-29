package com.robust.cmdline.cmd;

import com.robust.cmdline.BaseCmd;

public class Help extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		
	}

	@Override
	public String getHelpTipRes() {
		return "cmd.help.tip";
				
	}

	@Override
	public String getUseTipRes() {
		return "cmd.help.use";
	}

}

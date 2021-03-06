package com.robust.cmdline.cmd;

import java.io.File;

import com.robust.ParserManager;
import com.robust.cmdline.BaseCmd;

public class Fill extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		ParserManager parserManager = new ParserManager();
		parserManager.appendFile(new File(strings[0]), new File(strings[1]));
	}

	@Override
	public String getUseTipRes() {
		return "cmd.fill.use";
	}

	@Override
	public String getHelpTipRes() {
		return "cmd.fill.tip";
	}

}

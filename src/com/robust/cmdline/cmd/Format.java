package com.robust.cmdline.cmd;

import java.io.File;

import com.robust.ParserManager;
import com.robust.cmdline.BaseCmd;

public class Format extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		ParserManager parserManager = new ParserManager();
		parserManager.formatDir(new File(strings[0]), new File(strings[1]));
	}

	@Override
	public String getUseTipRes() {
		return "cmd.format.use";
	}

	@Override
	public String getHelpTipRes() {
		return "cmd.format.tip";
	}

}

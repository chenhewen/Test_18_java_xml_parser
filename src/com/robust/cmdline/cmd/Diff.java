package com.robust.cmdline.cmd;

import java.io.File;

import com.robust.Context;
import com.robust.ParserManager;
import com.robust.cmdline.BaseCmd;
import com.robust.cmdline.ICmd;

public class Diff extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		ParserManager parserManager = new ParserManager();
		parserManager.createDistinctFilesInAndroidFolderStructure(new File(strings[0]), new File(strings[1]), new File(strings[2]));
	}
	
	@Override
	public String getUseTipRes() {
		return "cmd.diff.use";
	}
	
	@Override
	public String getHelpTipRes() {
		return "cmd.diff.tip";
	}
	
}

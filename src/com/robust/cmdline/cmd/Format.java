package com.robust.cmdline.cmd;

import java.io.File;

import com.robust.ParserManager;
import com.robust.cmdline.BaseCmd;

public class Format extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		ParserManager parserManager = new ParserManager();
		
		if(strings.length == 2) {
			parserManager.formatFile(new File(strings[0]), new File(strings[1]));
		} else if (strings.length == 3){
			parserManager.formatRegexFile(new File(strings[0]), new File(strings[1]), strings[2]);
		}
		
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

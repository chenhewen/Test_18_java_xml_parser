package com.robust.cmdline.cmd;

import java.io.File;

import com.robust.TranslationInspector;
import com.robust.cmdline.BaseCmd;
import com.robust.cmdline.CmdOutput;

public class Check extends BaseCmd {

	@Override
	public void doAction(String... strings) {
		TranslationInspector translationInspector	= new TranslationInspector();
		translationInspector.setOutput(new CmdOutput());
		translationInspector.checkProblems(new File(strings[0]), new File(strings[1]), new File(strings[2]));
	}

	@Override
	public String getUseTipRes() {
		return "cmd.check.use";
	}

	@Override
	public String getHelpTipRes() {
		return "cmd.check.tip";
	}
}

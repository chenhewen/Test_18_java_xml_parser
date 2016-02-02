package com.robust.cmdline;

import com.robust.output.BaseOutput;

public class CmdOutput extends BaseOutput {

	@Override
	public void output(CharSequence charSequence) {
		System.out.println(charSequence);
	}
}

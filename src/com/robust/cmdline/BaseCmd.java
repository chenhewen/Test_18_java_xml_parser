package com.robust.cmdline;

import com.robust.Context;

/**
 * 
 * @author Administrator
 *
 */
public abstract class BaseCmd implements ICmd {

	private Context mContext;
	
	public BaseCmd() {
		mContext = new Context();
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public String getUseAndHelpTip() {
		return mContext.getResources().getString(getUseTipRes()) + "\n\t\t\t" + 
				mContext.getResources().getString(getHelpTipRes());
	}
}

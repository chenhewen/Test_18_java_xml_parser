package com.robust;

import java.util.ResourceBundle;

import com.robust.values.ResourceBundleManager;
/**
 * 全局上下文
 * @author Administrator
 *
 */
public class Context {
	
	public ResourceBundle getResources() {
		return ResourceBundleManager.getInstance().getResourceBundle();
	}
}

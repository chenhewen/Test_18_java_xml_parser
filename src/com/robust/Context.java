package com.robust;

import java.util.ResourceBundle;

import com.robust.values.ResourceBundleManager;
/**
 * 
 * @author Administrator
 *
 */
public class Context {
	
	public ResourceBundle getResources() {
		return ResourceBundleManager.getInstance().getResourceBundle();
	}
}

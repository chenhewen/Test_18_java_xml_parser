package com.robust.values;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Administrator
 *
 */
public class ResourceBundleManager {

	private static ResourceBundleManager sInstance;
	
	private ResourceBundle mResourceBundle;
	
	private ResourceBundleManager() {
		String bundleQualifierName = "com.robust.values.value";
		ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleQualifierName, new Locale("en"));
		setResourceBundle(resourceBundle);
	}
	
	public static ResourceBundleManager getInstance() {
		if (sInstance == null) {
			return new ResourceBundleManager();
		}
		
		return sInstance;
	}
	
	public ResourceBundle getResourceBundle(){
		return mResourceBundle;
	}
	
	public void setResourceBundle(ResourceBundle bundle) {
		mResourceBundle = bundle;
	}
}

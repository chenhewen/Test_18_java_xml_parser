package com.robust.cmdline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;

import com.robust.cmdline.cmd.Diff;


/**
 * ������
 * @author Administrator
 *
 */
public class CmdLine {

	public String help() {

		Reflections reflections = new Reflections("com.robust");
		 Set<Class<? extends BaseCmd>> subTypes = 
		           reflections.getSubTypesOf(BaseCmd.class);
		
		 StringBuffer sb = new StringBuffer();
		for (Class<? extends BaseCmd> clazz : subTypes) {
			try {
				Object newInstance = clazz.newInstance();
				Method declaredMethod = newInstance.getClass().getMethod("getUseAndHelpTip");
				String helpTip = (String) declaredMethod.invoke(newInstance);
				sb.append(helpTip).append("\n");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
		}
		
		return sb.toString();
	}
	
	public void diff(String resDir, String outputDir, String modleFile) {
		Diff diff = new Diff();
		diff.doAction(resDir, outputDir, modleFile);
	}

	public String getSupportedCmd() {
		return help();
	}
}

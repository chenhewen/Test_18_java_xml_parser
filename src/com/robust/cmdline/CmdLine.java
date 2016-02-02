package com.robust.cmdline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;

import com.robust.cmdline.cmd.Check;
import com.robust.cmdline.cmd.Diff;
import com.robust.cmdline.cmd.Fill;
import com.robust.cmdline.cmd.Format;


/**
 * √¸¡Ó––
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
	
	public void diff(String srcDir, String outputDir, String modleFile) {
		Diff diff = new Diff();
		diff.doAction(srcDir, outputDir, modleFile);
	}
	
	public void check(String srcDir, String outputDir, String modleFile) {
		Check check = new Check();
		check.doAction(srcDir, outputDir, modleFile);
	}
	
	public void fill(String srcDir, String destDir) {
		Fill fill = new Fill();
		fill.doAction(srcDir, destDir);
	}

	public void format(String destDir, String modelFile) {
		Format format = new Format();
		format.doAction(destDir, modelFile);
	}
	
	public String getSupportedCmd() {
		return help();
	}
}

package Evomath;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Function {

	protected String function;
	final ScriptEngineManager mgr = new ScriptEngineManager();
	final ScriptEngine engine = mgr.getEngineByName("JavaScript");

	public Function(String function) {
		this.function = function.toLowerCase();
	}

	public Function(Function f) {
		this.function = f.function;
	}

	public double get(double x) throws ScriptException {
		Double val = (Double) engine.eval(function);
		return val;	
	}
}

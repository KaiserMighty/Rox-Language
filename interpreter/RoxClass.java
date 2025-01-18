package interpreter;

import java.util.List;
import java.util.Map;

class RoxClass implements RoxCallable
{
    final String name;
    private final Map<String, RoxFunction> methods;

    RoxClass(String name, Map<String, RoxFunction> methods)
    {
        this.name = name;
        this.methods = methods;
    }

    RoxFunction findMethod(String name)
    {
        if (methods.containsKey(name))
        {
            return methods.get(name);
        }
        return null;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments)
    {
        RoxInstance instance = new RoxInstance(this);
        RoxFunction initializer = findMethod("init");
        if (initializer != null)
        {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity()
    {
        RoxFunction initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }
}
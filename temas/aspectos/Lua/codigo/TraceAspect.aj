import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TraceAspect {
    
    @Before("execution(* MyClass.doSomething())")
    public void traceMethod() {
        System.out.println("Method called: doSomething()");
    }
}
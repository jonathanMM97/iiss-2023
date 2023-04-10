import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;


public class TemaAspectos extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Account.class);
        bind(SecurityAspect.class);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(SecurityAspect.class), new SecurityAspect());
    }
}
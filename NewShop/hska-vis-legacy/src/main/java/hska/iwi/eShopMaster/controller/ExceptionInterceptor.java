package hska.iwi.eShopMaster.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableAnalyzer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

public class ExceptionInterceptor extends ExceptionMappingInterceptor {
	
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	
	@Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;

        try {
            result = invocation.invoke();
        } catch (Exception e) {
        	e.printStackTrace();
    		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
    		UserRedirectRequiredException redirect = (UserRedirectRequiredException) throwableAnalyzer
    				.getFirstThrowableOfType(
    						UserRedirectRequiredException.class, causeChain);
    		if (redirect != null) {
    			throw e;
    		}
            if (isLogEnabled()) {
                handleLogging(e);
            }
            List<ExceptionMappingConfig> exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
            ExceptionMappingConfig mappingConfig = this.findMappingFromExceptions(exceptionMappings, e);
            if (mappingConfig != null && mappingConfig.getResult()!=null) {
                Map parameterMap = mappingConfig.getParams();
                // create a mutable HashMap since some interceptors will remove parameters, and parameterMap is immutable
                invocation.getInvocationContext().setParameters(new HashMap<String, Object>(parameterMap));
                result = mappingConfig.getResult();
                publishException(invocation, new ExceptionHolder(e));
            } else {
                throw e;
            }
        }

        return result;
    }

}

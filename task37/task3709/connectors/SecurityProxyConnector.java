package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.connectors.SimpleConnector;
import com.javarush.task.task37.task3709.security.SecurityChecker;
import com.javarush.task.task37.task3709.security.SecurityCheckerImpl;

/**
 * Created by 123 on 16.12.2017.
 */
public class SecurityProxyConnector implements Connector {

    private SecurityChecker securityChecker;

    private SimpleConnector simpleConnector;

    public SecurityProxyConnector(String url) {
        simpleConnector=new SimpleConnector(url);
        securityChecker=new SecurityCheckerImpl();
    }

    @Override
    public void connect() {
       if(securityChecker.performSecurityCheck()){
           simpleConnector.connect();
       }
    }
}

package com.github.mk23.jmxproxy.jmx.tests;

import com.github.mk23.jmxproxy.jmx.TimeoutRMISocketFactory;

import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

import static org.junit.Assert.assertNotNull;

public class TimeoutRMISocketFactoryTest {
    private final String validHost   = "localhost";
    private final String invalidHost = "192.0.2.1";

    private final int serverPort     = Integer.getInteger("com.sun.management.jmxremote.port");

    private final int connectTimeout = 500;

    @Rule public ExpectedException thrown = ExpectedException.none();
    @Rule public TestName name = new TestName();

    @Before
    public void printTestName() {
        System.out.println(" -> " + name.getMethodName());
    }

    @Test
    public void checkClientValidHostValidPort() throws Exception {
        final TimeoutRMISocketFactory sf = new TimeoutRMISocketFactory(connectTimeout);

        assertNotNull(sf.createSocket(validHost, serverPort));
    }

    @Test(timeout=2000)
    public void checkClientInvalidHostValidPort() throws Exception {
        final TimeoutRMISocketFactory sf = new TimeoutRMISocketFactory(connectTimeout);

        thrown.expect(SocketTimeoutException.class);
        sf.createSocket(invalidHost, serverPort);
    }

    @Test
    public void checkClientValidHostInvalidPort() throws Exception {
        final TimeoutRMISocketFactory sf = new TimeoutRMISocketFactory(connectTimeout);

        thrown.expect(ConnectException.class);
        sf.createSocket(validHost, 0);
    }

    @Test
    public void checkServerValidPort() throws Exception {
        final TimeoutRMISocketFactory sf = new TimeoutRMISocketFactory(connectTimeout);

        assertNotNull(sf.createServerSocket(0));
    }

    @Test
    public void checkServerInvalidPort() throws Exception {
        final TimeoutRMISocketFactory sf = new TimeoutRMISocketFactory(connectTimeout);

        thrown.expect(BindException.class);
        sf.createServerSocket(serverPort);
    }
}

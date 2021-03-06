/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.hk2.tests.locator.proxiable2;

import junit.framework.Assert;

import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.tests.locator.utilities.LocatorHelper;
import org.junit.Test;

/**
 * @author jwells
 *
 */
public class ProxiableTest {
    private final static String TEST_NAME = "Proxiable2Test";
    private final static ServiceLocator locator = LocatorHelper.create(TEST_NAME, new ProxiableModule());
    
    /**
     * Tests that I can have something in the singleton
     * scope that gets proxied
     */
    @Test
    public void testProxiedSingleton() {
        ProxiableService.resetConstructorCalled();
        
        ServiceHandle<ProxiableService> psHandle = locator.getServiceHandle(ProxiableService.class);
        Assert.assertNotNull(psHandle);
        
        try {
            ProxiableService ps = psHandle.getService();
        
            Assert.assertEquals(0, ProxiableService.getConstructorCalled());
        
            ps.doService();  // Forces true creation
        
            Assert.assertEquals(1, ProxiableService.getConstructorCalled());
        }
        finally {
            // Removes it from Singleton scope
            psHandle.destroy();
        }
    }
    
    /**
     * Tests that the proxied singleton service implements ProxyCtl
     */
    @Test
    public void testProxiedSingletonUsingProxyCtl() {
        ProxiableService2.resetConstructorCalled();
        
        ServiceHandle<ProxiableService2> psHandle = locator.getServiceHandle(ProxiableService2.class);
        Assert.assertNotNull(psHandle);
        
        try {
            ProxiableService2 ps = psHandle.getService();
        
            Assert.assertEquals(0, ProxiableService2.getConstructorCalled());
            
            Assert.assertTrue(ps instanceof ProxyCtl);
        
            ((ProxyCtl) ps).__make();  // Forces true creation
        
            Assert.assertEquals(1, ProxiableService2.getConstructorCalled());
        }
        finally {
            // Removes it from Singleton scope
            psHandle.destroy();
        }
    }
    
    /**
     * Test that the singleton context works
     */
    @Test
    public void testProxiedSingletonFromContext() {
        ProxiableServiceInContext.resetConstructorCalled();
        
        ServiceHandle<ProxiableServiceInContext> psHandle = locator.getServiceHandle(ProxiableServiceInContext.class);
        Assert.assertNotNull(psHandle);
        
        try {
            ProxiableServiceInContext ps = psHandle.getService();
        
            Assert.assertEquals(0, ProxiableServiceInContext.getConstructorCalled());
        
            ps.doService();  // Forces true creation
        
            Assert.assertEquals(1, ProxiableServiceInContext.getConstructorCalled());
        }
        finally {
            // Removes it from Singleton scope
            psHandle.destroy();
        }
    }
    
    /**
     * Tests that the ProxiableSingleton works, using ProxyCtl
     */
    @Test
    public void testProxiedSingletonInContextUsingProxyCtl() {
        ProxiableServiceInContext2.resetConstructorCalled();
        
        ServiceHandle<ProxiableServiceInContext2> ps2Handle = locator.getServiceHandle(ProxiableServiceInContext2.class);
        Assert.assertNotNull(ps2Handle);
        
        try {
            ProxiableServiceInContext2 ps2 = ps2Handle.getService();
        
            Assert.assertEquals(0, ProxiableServiceInContext2.getConstructorCalled());
            
            Assert.assertTrue(ps2 instanceof ProxyCtl);

            ((ProxyCtl) ps2).__make();  // Forces true creation
        
            Assert.assertEquals(1, ProxiableServiceInContext2.getConstructorCalled());
        }
        finally {
            // Removes it from Singleton scope
            ps2Handle.destroy();
        }
    }
    
    /**
     * Test that you can explicitly NOT be proxied from a proxiable context
     */
    @Test
    public void testNotProxiedSingletonFromContext() {
        NotProxiableService.resetConstructorCalled();
        
        ServiceHandle<NotProxiableService> psHandle = locator.getServiceHandle(NotProxiableService.class);
        Assert.assertNotNull(psHandle);
        
        Assert.assertEquals(0, NotProxiableService.getConstructorCalled());
        
        try {
            NotProxiableService ps = psHandle.getService();
        
            Assert.assertEquals(1, NotProxiableService.getConstructorCalled());
        
            Assert.assertFalse(ps instanceof ProxyCtl);
        }
        finally {
            // Removes it from Singleton scope
            psHandle.destroy();
        }
    }
    
    /**
     * Test that the singleton context works
     */
    @Test
    public void testProxiedServiceFromFactory() {
        ProxiableServiceFromFactory.resetConstructorCalled();
        
        ServiceHandle<ProxiableServiceFromFactory> psHandle = locator.getServiceHandle(ProxiableServiceFromFactory.class);
        Assert.assertNotNull(psHandle);
        
        try {
            ProxiableServiceFromFactory ps = psHandle.getService();
        
            Assert.assertEquals(0, ProxiableServiceFromFactory.getConstructorCalled());
        
            ps.doService();  // Forces true creation
        
            Assert.assertEquals(1, ProxiableServiceFromFactory.getConstructorCalled());
        }
        finally {
            // Removes it from Singleton scope
            psHandle.destroy();
        }
    }

}

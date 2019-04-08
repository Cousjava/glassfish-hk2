/*
 * Copyright (c) 2019 Payara Services Ltd. All rights reserved.
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
package org.glassfish.hk2.examples.simple;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author jonathan
 */
@Service
public class Bar {
    
    
    @PostConstruct
    public void postConstuct() {
        System.out.println("postConstruct of class Bar");
    }
    
    public String doStuff() {
        return "A string returned from class Bar";
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy of class Bar");
    }
}

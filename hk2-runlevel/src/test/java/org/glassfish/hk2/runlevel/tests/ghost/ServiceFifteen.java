/*
 * Copyright (c) 2014, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.hk2.runlevel.tests.ghost;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import javax.inject.Inject;

import org.glassfish.hk2.runlevel.RunLevel;

/**
 * @author jwells
 *
 */
@RunLevel(value=15, mode=RunLevel.RUNLEVEL_MODE_VALIDATING)
public class ServiceFifteen {
    @Inject
    private Registrar registrar;
    
    @PostConstruct
    private void postConstruct() {
        registrar.register(this);
    }
    
    @PreDestroy
    private void preDestroy() {
        registrar.goingDown(this);
    }

}

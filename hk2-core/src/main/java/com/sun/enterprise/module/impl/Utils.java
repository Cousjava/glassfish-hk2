/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.enterprise.module.impl;

import com.sun.enterprise.module.ModuleDependency;
import com.sun.enterprise.module.Module;
import com.sun.enterprise.module.ModulesRegistry;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dochez
 */
public class Utils {
    
    /** Creates a new instance of Utils */
    private Utils() {
    }

    public static void identifyCyclicDependency(ModuleImpl m, Logger logger) {

        StringBuffer tree = new StringBuffer();
        tree.append(m.getName());
        Vector<Module> traversed = new Vector<Module>();
        boolean success = traverseAndFind(m, m, traversed);
        if (success) {
            traversed.remove(0);
            for (Module mod : traversed) {
                tree.append("-->" + mod.getName());
            }
            tree.append("-->" + m.getName());
        logger.log(Level.SEVERE, "Cyclic dependency : " + tree.toString());
        }
    }

    static private boolean traverseAndFind(Module toTraverse, ModuleImpl toFind, Vector<Module> traversed) {

        traversed.add(toTraverse);
        for (ModuleDependency md : toTraverse.getModuleDefinition().getDependencies())  {
            ModulesRegistry registry = toTraverse.getRegistry();
            for (Module mod : registry.getModules()) {
                if (mod.getName().equals(md.getName())) {
                    if (mod!=null) {
                        if (mod.getName().equals(toFind.getName())) {
                            return true;
                        }
                        if (traverseAndFind(mod, toFind, traversed)) {
                            return true;
                        }
                    }

                }
            }
        }
        traversed.remove(toTraverse);
        return false;
    }
}

/* 
 * Copyright 2010 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.terminal.gwt.client;

import java.util.Set;

/**
 * Client side console implementation for non-debug mode that discards all
 * messages.
 * 
 */
public class NullConsole implements Console {

    public void dirUIDL(UIDL u) {
    }

    public void error(String msg) {
    }

    public void log(String msg) {
    }

    public void printObject(Object msg) {
    }

    public void printLayoutProblems(ValueMap meta,
            ApplicationConnection applicationConnection,
            Set<Paintable> zeroHeightComponents,
            Set<Paintable> zeroWidthComponents) {
    }

}

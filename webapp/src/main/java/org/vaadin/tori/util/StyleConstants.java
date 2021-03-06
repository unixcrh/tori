/*
 * Copyright 2012 Vaadin Ltd.
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

package org.vaadin.tori.util;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Style name constants to be used throughout the whole application.
 */
public final class StyleConstants {

    private StyleConstants() {
        // not instantiable
    }

    /**
     * Use for {@link HorizontalLayout} or {@link VerticalLayout} when you want
     * a smaller margin.
     */
    public static final String HALF_MARGIN = "halfMargin";

    /**
     * Use for {@link org.vaadin.hene.popupbutton.PopupButton} when you want to
     * hide the indicator arrow.
     */
    public static final String POPUP_INDICATOR_HIDDEN = "popupIndicatorHidden";

    /**
     * Use when you want to hide a component only in CSS, not from the whole DOM
     */
    public static final String HIDDEN = "hidden";
}

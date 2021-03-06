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

package org.vaadin.tori.component.post;

import org.vaadin.tori.data.entity.Post;
import org.vaadin.tori.exception.DataSourceException;
import org.vaadin.tori.service.post.PostReport;
import org.vaadin.tori.service.post.PostReport.Reason;
import org.vaadin.tori.thread.ThreadPresenter;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
class ReportWindow2 extends Window {

    private final CssLayout explanationLayout;
    private final NativeButton reportButton;
    private final VerticalLayout layout = new VerticalLayout();

    public ReportWindow2(final Post post, final ThreadPresenter presenter,
            final int x, final int y, final String postPermalink) {
        super("Report Post");
        layout.setSpacing(true);
        layout.setMargin(true);

        setContent(layout);
        setStyleName("reportwindow");
        setClosable(false);
        setCloseShortcut(KeyCode.ESCAPE);
        setResizable(false);
        getContent().setWidth("100%");
        setWidth("350px");
        focus();

        if (x != -1 && y != -1) {
            setPositionX(x - 350);
            setPositionY(y);
        }

        layout.addComponent(new Label("What's wrong with this post?"));

        final CssLayout reasonLayout = new CssLayout();
        reasonLayout.setWidth("100%");
        layout.addComponent(reasonLayout);

        final OptionGroup reason = new OptionGroup();

        reason.addItem(Reason.SPAM);
        reason.setItemCaption(Reason.SPAM, "Spam");
        reason.addItem(Reason.OFFENSIVE);
        reason.setItemCaption(Reason.OFFENSIVE,
                "Offensive, abusive or hateful.");
        reason.addItem(Reason.WRONG_CATEGORY);
        reason.setItemCaption(Reason.WRONG_CATEGORY,
                "Doesn't belong in the category.");
        reason.addItem(Reason.MODERATOR_ALERT);
        reason.setItemCaption(Reason.MODERATOR_ALERT,
                "A moderator should take a look at it.");

        reason.setImmediate(true);
        reason.addValueChangeListener(new OptionGroup.ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                explanationLayout.setVisible(reason.getValue() == Reason.MODERATOR_ALERT);
                reportButton.setEnabled(reason.getValue() != null);
            }
        });
        reasonLayout.addComponent(reason);

        explanationLayout = new CssLayout();
        explanationLayout.setStyleName("explanationlayout");
        explanationLayout.addComponent(new Label("Here's why:"));
        final TextArea reasonText = createReasonTextArea();
        explanationLayout.addComponent(reasonText);
        explanationLayout.setVisible(false);
        explanationLayout.setWidth("100%");
        reasonLayout.addComponent(explanationLayout);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        layout.addComponent(footer);

        reportButton = new NativeButton("Report Post");
        reportButton.addClickListener(new NativeButton.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                final PostReport report = new PostReport(post, (Reason) reason
                        .getValue(), reasonText.getValue(), postPermalink);

                try {
                    presenter.handlePostReport(report);
                    ReportWindow2.this.close();
                } catch (final DataSourceException e) {
                    layout.removeAllComponents();
                    layout.addComponent(new Label(
                            DataSourceException.BORING_GENERIC_ERROR_MESSAGE));
                    ReportWindow2.this.setClosable(true);
                }
            }
        });
        reportButton.setEnabled(false);
        footer.addComponent(reportButton);

        final NativeButton cancel = new NativeButton("Cancel");
        cancel.addClickListener(new NativeButton.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                ReportWindow2.this.close();
            }
        });
        footer.addComponent(cancel);
    }

    private static TextArea createReasonTextArea() {
        final TextArea area = new TextArea();
        area.setWidth("100%");
        area.setRows(4);
        return area;
    }
}

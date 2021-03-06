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
import org.vaadin.tori.data.entity.PostVote;
import org.vaadin.tori.exception.DataSourceException;
import org.vaadin.tori.thread.ThreadPresenter;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class PostScoreComponent extends CustomComponent {
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "SE_BAD_FIELD", justification = "Ignoring serialization")
    private final ThreadPresenter presenter;
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "SE_BAD_FIELD", justification = "Ignoring serialization")
    private final Post post;

    private final CssLayout layout = new CssLayout();
    private final Label score;

    public PostScoreComponent(final Post post, final ThreadPresenter presenter) {
        this.post = post;
        this.presenter = presenter;
        setCompositionRoot(layout);
        layout.setWidth("100%");
        setWidth("75px");
        setStyleName("scorecomponent");

        score = new Label();
        score.setWidth(null);
        layout.addComponent(score);
    }

    public void enableUpDownVoting(final PostVote postVote) {
        layout.removeAllComponents();
        final NativeButton upvote = new NativeButton();
        upvote.setStyleName("upvote");
        upvote.addStyleName("vote");
        if (postVote.isUpvote()) {
            upvote.addStyleName("done");
        }
        upvote.addClickListener(new NativeButton.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    presenter.upvote(post);
                } catch (final DataSourceException e) {
                    Notification
                            .show(DataSourceException.BORING_GENERIC_ERROR_MESSAGE);
                }
            }
        });
        layout.addComponent(upvote);

        final NativeButton downvote = new NativeButton();
        downvote.setStyleName("downvote");
        downvote.addStyleName("vote");
        if (postVote.isDownvote()) {
            downvote.addStyleName("done");
        }
        downvote.addClickListener(new NativeButton.ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    presenter.downvote(post);
                } catch (final DataSourceException e) {
                    Notification
                            .show(DataSourceException.BORING_GENERIC_ERROR_MESSAGE);
                }
            }
        });
        layout.addComponent(downvote);
        layout.addComponent(score);
    }

    public void setScore(final long newScore) {
        score.setValue((newScore > 0 ? "+" : "") + String.valueOf(newScore));

        // reset styles
        score.removeStyleName("positive");
        score.removeStyleName("negative");
        score.removeStyleName("zero");

        // assign style name
        if (newScore > 0) {
            score.addStyleName("positive");
        } else if (newScore < 0) {
            score.addStyleName("negative");
        } else if (newScore == 0) {
            score.addStyleName("zero");
        }
    }
}

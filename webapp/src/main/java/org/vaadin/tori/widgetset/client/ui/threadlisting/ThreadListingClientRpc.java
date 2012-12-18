package org.vaadin.tori.widgetset.client.ui.threadlisting;

import java.util.Map;

import org.vaadin.tori.widgetset.client.ui.threadlisting.ThreadListingState.RowInfo;

import com.vaadin.shared.communication.ClientRpc;

public interface ThreadListingClientRpc extends ClientRpc {
    void sendComponents(Map<Integer, RowInfo> rows);
}

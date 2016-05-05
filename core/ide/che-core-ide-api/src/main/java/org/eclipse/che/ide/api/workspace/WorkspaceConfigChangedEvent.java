/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.api.workspace;

import com.google.common.annotations.Beta;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import org.eclipse.che.api.core.model.workspace.WorkspaceConfig;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Workspace update events describe changes to workspace configuration.
 * <p/>
 * This event is intended to be fired when workspace has started or other operation is performed on its configuration.
 * <p/>
 * By design this event is intended to initialize {@link Workspace} context with initial configuration.
 *
 * @author Vlad Zhukovskiy
 * @since 4.0.0-RC14
 */
@Beta
public class WorkspaceConfigChangedEvent extends GwtEvent<WorkspaceConfigChangedEvent.WorkspaceConfigChangedHandler> {

    /**
     * A workspace configuration change listener is notified of changes to workspace configuration.
     * <p/>
     * Third party components may implement this interface to handle workspace configuration changes event.
     */
    public interface WorkspaceConfigChangedHandler extends EventHandler {
        /**
         * Notifies the listener that some workspace configuration changes are happening. The supplied event dives details.
         *
         * @param event
         *         instance of {@link WorkspaceConfigChangedEvent}
         * @see WorkspaceConfigChangedEvent
         * @since 4.0.0-RC14
         */
        void onConfigChanged(WorkspaceConfigChangedEvent event);
    }

    private static Type<WorkspaceConfigChangedHandler> TYPE;

    public static Type<WorkspaceConfigChangedHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    private final String          wsId;
    private final org.eclipse.che.api.core.model.workspace.Workspace configuration;
    private final boolean         temporary;

    public WorkspaceConfigChangedEvent(String wsId, org.eclipse.che.api.core.model.workspace.Workspace configuration, boolean temporary) {
        this.wsId = checkNotNull(wsId, "Workspace identifier should not be null");
        this.configuration = checkNotNull(configuration, "Workspace configuration should not ba null");
        this.temporary = temporary;
    }

    /**
     * Returns the workspace identifier which was affected in configuration update.
     *
     * @return the workspace identifier
     * @since 4.0.0-RC14
     */
    public String getID() {
        return wsId;
    }

    /**
     * Returns the new workspace configuration.
     *
     * @return the new workspace configuration
     * @see WorkspaceConfig
     * @since 4.0.0-RC14
     */
    public org.eclipse.che.api.core.model.workspace.Workspace getConfiguration() {
        return configuration;
    }

    /**
     * Returns the persistence status of current workspace.
     *
     * @return the true if workspace is temporary, otherwise returns the false
     * @see Workspace#isTemporary()
     * @since 4.0.0-RC14
     */
    public boolean isTemporary() {
        return temporary;
    }

    /** {@inheritDoc} */
    @Override
    public Type<WorkspaceConfigChangedHandler> getAssociatedType() {
        return TYPE;
    }

    /** {@inheritDoc} */
    @Override
    protected void dispatch(WorkspaceConfigChangedHandler handler) {
        handler.onConfigChanged(this);
    }
}

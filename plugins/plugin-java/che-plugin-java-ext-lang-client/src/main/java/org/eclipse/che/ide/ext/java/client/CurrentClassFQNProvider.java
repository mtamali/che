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
package org.eclipse.che.ide.ext.java.client;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.editor.EditorAgent;
import org.eclipse.che.ide.api.event.FileEvent;
import org.eclipse.che.ide.api.event.FileEventHandler;
import org.eclipse.che.ide.api.event.SelectionChangedEvent;
import org.eclipse.che.ide.api.event.SelectionChangedHandler;
import org.eclipse.che.ide.api.resources.Container;
import org.eclipse.che.ide.api.resources.Resource;
import org.eclipse.che.ide.api.resources.VirtualFile;
import org.eclipse.che.ide.api.selection.Selection;
import org.eclipse.che.ide.ext.java.client.projecttree.JavaSourceFolderUtil;
import org.eclipse.che.ide.ext.java.client.resource.JavaSourceFolderMarker;
import org.eclipse.che.ide.ext.java.client.util.JavaUtil;
import org.eclipse.che.ide.extension.machine.client.command.valueproviders.CommandPropertyValueProvider;

import static org.eclipse.che.ide.api.event.FileEvent.FileOperation.CLOSE;
import static org.eclipse.che.ide.api.resources.Resource.FILE;
import static org.eclipse.che.ide.ext.java.client.util.JavaUtil.isJavaFile;

/**
 * Provides FQN of the Java-class which is opened in active editor.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class CurrentClassFQNProvider implements CommandPropertyValueProvider {

    private static final String KEY = "${current.class.fqn}";
    private final AppContext appContext;

    @Inject
    public CurrentClassFQNProvider(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getValue() {
        final Resource[] resources = appContext.getResources();

        if (resources == null || resources.length > 1) {
            return "";
        }

        final Resource resource = resources[0];
        final Optional<Resource> srcFolder = resource.getParentWithMarker(JavaSourceFolderMarker.ID);

        if (resource.getResourceType() == FILE && isJavaFile(resource) && srcFolder.isPresent()) {
            return JavaUtil.resolveFQN((Container)srcFolder.get(), resource);
        } else {
            return "";
        }
    }
}

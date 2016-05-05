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
package org.eclipse.che.ide.upload.folder;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.inject.Inject;

import org.eclipse.che.api.machine.gwt.client.DevMachine;
import org.eclipse.che.api.promises.client.Operation;
import org.eclipse.che.api.promises.client.OperationException;
import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.api.resources.Container;
import org.eclipse.che.ide.api.resources.Resource;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.che.ide.api.notification.StatusNotification.DisplayMode.FLOAT_MODE;
import static org.eclipse.che.ide.api.notification.StatusNotification.Status.FAIL;

/**
 * The purpose of this class is upload folder from zip
 *
 * @author Roman Nikitenko
 * @author Vlad Zhukovskyi
 */
public class UploadFolderFromZipPresenter implements UploadFolderFromZipView.ActionDelegate {

    private final UploadFolderFromZipView  view;
    private final CoreLocalizationConstant locale;
    private final NotificationManager      notificationManager;
    private final DevMachine devMachine;
    private       Container                container;

    @Inject
    public UploadFolderFromZipPresenter(UploadFolderFromZipView view,
                                        AppContext appContext,
                                        NotificationManager notificationManager,
                                        CoreLocalizationConstant locale) {
        devMachine = appContext.getDevMachine();
        this.view = view;
        this.locale = locale;
        this.view.setDelegate(this);
        this.view.setEnabledUploadButton(false);
        this.notificationManager = notificationManager;

        view.setEncoding(FormPanel.ENCODING_MULTIPART);
    }

    /** Show dialog. */
    public void showDialog(Container container) {
        this.container = container;
        view.showDialog();
        view.setAction(devMachine.getWsAgentBaseUrl() + "/project/" + devMachine.getId() + "/upload/zipfolder" + container.getLocation());
    }

    /** {@inheritDoc} */
    @Override
    public void onCancelClicked() {
        view.closeDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void onSubmitComplete(String result) {
        view.setLoaderVisibility(false);

        if (!isNullOrEmpty(result)) {
            view.closeDialog();
            notificationManager.notify(locale.failedToUploadFilesFromZip(), parseMessage(result), FAIL, FLOAT_MODE);
            return;
        }

        container.synchronize().then(new Operation<Resource[]>() {
            @Override
            public void apply(Resource[] arg) throws OperationException {
                view.closeDialog();
            }
        });

        //TODO this should process editor agent
//        if (view.isOverwriteFileSelected()) {
//            updateOpenedEditors();
//        }
    }

    /** {@inheritDoc} */
    @Override
    public void onUploadClicked() {
        view.setLoaderVisibility(true);
        view.submit();
    }

    /** {@inheritDoc} */
    @Override
    public void onFileNameChanged() {
        String fileName = view.getFileName();
        boolean enabled = !fileName.isEmpty() && fileName.contains(".zip");
        view.setEnabledUploadButton(enabled);
    }

    private String parseMessage(String message) {
        int startIndex = 0;
        int endIndex = -1;

        if (message.contains("<pre>message:")) {
            startIndex = message.indexOf("<pre>message:") + "<pre>message:".length();
        } else if (message.contains("<pre>")) {
            startIndex = message.indexOf("<pre>") + "<pre>".length();
        }

        if (message.contains("</pre>")) {
            endIndex = message.indexOf("</pre>");
        }
        return (endIndex != -1) ? message.substring(startIndex, endIndex) : message.substring(startIndex);
    }
}

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
package org.eclipse.che.ide.extension.maven.shared;

/**
 * @author Evgen Vidolob
 */
public interface MavenAttributes {
    String MAVEN_ID   = "maven";
    String MAVEN_NAME = "Maven Project";

    String GENERATION_STRATEGY_OPTION = "type";

    String SIMPLE_GENERATION_STRATEGY    = "simple";
    String ARCHETYPE_GENERATION_STRATEGY = "archetype";

    String ARCHETYPE_GROUP_ID_OPTION    = "archetypeGroupId";
    String ARCHETYPE_ARTIFACT_ID_OPTION = "archetypeArtifactId";
    String ARCHETYPE_VERSION_OPTION     = "archetypeVersion";
    String ARCHETYPE_REPOSITORY_OPTION  = "archetypeRepository";

    String GROUP_ID           = "maven.groupId";
    String ARTIFACT_ID        = "maven.artifactId";
    String VERSION            = "maven.version";
    String PACKAGING          = "maven.packaging";
    String PARENT_GROUP_ID    = "maven.parent.groupId";
    String PARENT_ARTIFACT_ID = "maven.parent.artifactId";
    String PARENT_VERSION     = "maven.parent.version";

    String SOURCE_FOLDER      = "maven.source.folder";
    String TEST_SOURCE_FOLDER = "maven.test.source.folder";

    String RESOURCE_FOLDER = "maven.resource.folder";

    String DEFAULT_SOURCE_FOLDER         = "src/main/java";
    String DEFAULT_RESOURCES_FOLDER      = "src/main/resources";
    String DEFAULT_TEST_SOURCE_FOLDER    = "src/test/java";
    String DEFAULT_TEST_RESOURCES_FOLDER = "src/test/resources";
    String DEFAULT_VERSION               = "1.0-SNAPSHOT";
    String DEFAULT_PACKAGING             = "jar";

    String POM_XML = "pom.xml";
}

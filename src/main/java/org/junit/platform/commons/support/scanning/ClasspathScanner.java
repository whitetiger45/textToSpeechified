/*
 * Copyright 2015-2025 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.commons.support.scanning;

import static org.apiguardian.api.API.Status;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

import org.apiguardian.api.API;
import org.junit.platform.commons.support.Resource;

/**
 * {@code ClasspathScanner} allows to scan the classpath for classes and
 * resources.
 *
 * <p>An implementation of this interface can be registered via the
 * {@link java.util.ServiceLoader ServiceLoader} mechanism.
 *
 * @since 1.12
 */
@API(status = Status.EXPERIMENTAL, since = "1.12")
public interface ClasspathScanner {

	/**
	 * Find all {@linkplain Class classes} in the supplied classpath {@code root}
	 * that match the specified {@code classFilter} filter.
	 *
	 * <p>The classpath scanning algorithm searches recursively in subpackages
	 * beginning with the root of the classpath.
	 *
	 * @param basePackageName the name of the base package in which to start
	 * scanning; must not be {@code null} and must be valid in terms of Java
	 * syntax
	 * @param classFilter the class type filter; never {@code null}
	 * @return a list of all such classes found; never {@code null}
	 * but potentially empty
	 */
	List<Class<?>> scanForClassesInPackage(String basePackageName, ClassFilter classFilter);

	/**
	 * Find all {@linkplain Class classes} in the supplied classpath {@code root}
	 * that match the specified {@code classFilter} filter.
	 *
	 * <p>The classpath scanning algorithm searches recursively in subpackages
	 * beginning with the root of the classpath.
	 *
	 * @param root the URI for the classpath root in which to scan; never
	 * {@code null}
	 * @param classFilter the class type filter; never {@code null}
	 * @return a list of all such classes found; never {@code null}
	 * but potentially empty
	 */
	List<Class<?>> scanForClassesInClasspathRoot(URI root, ClassFilter classFilter);

	/**
	 * Find all {@linkplain Resource resources} in the supplied classpath {@code root}
	 * that match the specified {@code resourceFilter} predicate.
	 *
	 * <p>The classpath scanning algorithm searches recursively in subpackages
	 * beginning with the root of the classpath.
	 *
	 * @param basePackageName the name of the base package in which to start
	 * scanning; must not be {@code null} and must be valid in terms of Java
	 * syntax
	 * @param resourceFilter the resource type filter; never {@code null}
	 * @return a list of all such resources found; never {@code null}
	 * but potentially empty
	 */
	List<Resource> scanForResourcesInPackage(String basePackageName, Predicate<Resource> resourceFilter);

	/**
	 * Find all {@linkplain Resource resources} in the supplied classpath {@code root}
	 * that match the specified {@code resourceFilter} predicate.
	 *
	 * <p>The classpath scanning algorithm searches recursively in subpackages
	 * beginning with the root of the classpath.
	 *
	 * @param root the URI for the classpath root in which to scan; never
	 * {@code null}
	 * @param resourceFilter the resource type filter; never {@code null}
	 * @return a list of all such resources found; never {@code null}
	 * but potentially empty
	 */
	List<Resource> scanForResourcesInClasspathRoot(URI root, Predicate<Resource> resourceFilter);

}

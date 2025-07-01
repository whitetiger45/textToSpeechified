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

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.util.function.Predicate;

import org.apiguardian.api.API;
import org.junit.platform.commons.util.Preconditions;

/**
 * Class-related predicate used by reflection utilities.
 *
 * @since 1.1
 */
@API(status = EXPERIMENTAL, since = "1.12")
public class ClassFilter {

	/**
	 * Create a {@link ClassFilter} instance that accepts all names but filters classes.
	 *
	 * @param classPredicate the class type predicate; never {@code null}
	 * @return an instance of {@code ClassFilter}; never {@code null}
	 */
	public static ClassFilter of(Predicate<Class<?>> classPredicate) {
		return of(name -> true, classPredicate);
	}

	/**
	 * Create a {@link ClassFilter} instance that filters by names and classes.
	 *
	 * @param namePredicate the class name predicate; never {@code null}
	 * @param classPredicate the class type predicate; never {@code null}
	 * @return an instance of {@code ClassFilter}; never {@code null}
	 */
	public static ClassFilter of(Predicate<String> namePredicate, Predicate<Class<?>> classPredicate) {
		return new ClassFilter(namePredicate, classPredicate);
	}

	private final Predicate<String> namePredicate;
	private final Predicate<Class<?>> classPredicate;

	private ClassFilter(Predicate<String> namePredicate, Predicate<Class<?>> classPredicate) {
		this.namePredicate = Preconditions.notNull(namePredicate, "name predicate must not be null");
		this.classPredicate = Preconditions.notNull(classPredicate, "class predicate must not be null");
	}

	/**
	 * Test the given name using the stored name predicate.
	 *
	 * @param name the name to test; never {@code null}
	 * @return {@code true} if the input name matches the predicate, otherwise
	 * {@code false}
	 */
	public boolean match(String name) {
		return namePredicate.test(name);
	}

	/**
	 * Test the given class using the stored class predicate.
	 *
	 * @param type the type to test; never {@code null}
	 * @return {@code true} if the input type matches the predicate, otherwise
	 * {@code false}
	 */
	public boolean match(Class<?> type) {
		return classPredicate.test(type);
	}

}

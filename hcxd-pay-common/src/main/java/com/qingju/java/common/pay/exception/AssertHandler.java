/*
 *
 *       '||      '||  '.
 *      ||  ||     ||         ....     ....
 *     ||    ||    ||   ||  .|...||  ||    ||
 *    ||......||   ||   ||  ||       ||    ||
 *   ||        || .||. .||.  '|...' .||.  .||.
 *
 * --------------- By Liuyihua. ------------- '''' -----------
 *
 * @url http://www.alien.pub
 */

package com.qingju.java.common.pay.exception;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 对断言封装,自定义返回异常 Created by qiuye on 2015/9/28.
 */
public class AssertHandler {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new RestException(message);
		}
	}

	public static void isEmpty(String expression, String message) {
		if (null == expression || "".equals(expression)) {

			throw new RestException(message);
		}
	}

	public static void isTrue(boolean expression, String message, Integer errorCode) {
		if (!expression) {
			throw new RestException(message, errorCode);
		}
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new RestException(message);
		}
	}

	public static void isNull(Object object, String message, Integer errorCode) {
		if (object != null) {
			throw new RestException(message, errorCode);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new RestException(message);
		}
	}

	public static void notNull(Object object, String message, Integer errorCode) {
		if (object == null) {
			throw new RestException(message, errorCode);
		}
	}

	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new RestException(message);
		}
	}

	public static void hasLength(String text, String message, Integer errorCode) {
		if (!StringUtils.hasLength(text)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw new RestException(message);
		}
	}

	public static void hasText(String text, String message, Integer errorCode) {
		if (!StringUtils.hasText(text)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring)
				&& textToSearch.contains(substring)) {
			throw new RestException(message);
		}
	}

	public static void doesNotContain(String textToSearch, String substring, String message, Integer errorCode) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring)
				&& textToSearch.contains(substring)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw new RestException(message);
		}
	}

	public static void notEmpty(Object[] array, String message, Integer errorCode) {
		if (ObjectUtils.isEmpty(array)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new RestException(message);
				}
			}
		}
	}

	public static void noNullElements(Object[] array, String message, Integer errorCode) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new RestException(message, errorCode);
				}
			}
		}
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new RestException(message);
		}
	}

	public static void notEmpty(Collection<?> collection, String message, Integer errorCode) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new RestException(message);
		}
	}

	public static void notEmpty(Map<?, ?> map, String message, Integer errorCode) {
		if (CollectionUtils.isEmpty(map)) {
			throw new RestException(message, errorCode);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new RestException(message);
		}
	}

	public static void state(boolean expression, String message, Integer errorCode) {
		if (!expression) {
			throw new RestException(message, errorCode);
		}
	}

	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new RestException((StringUtils.hasLength(message) ? message + " " : "") + "Object of class ["
					+ (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
		}
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new RestException(message + subType + " is not assignable to " + superType);
		}
	}
}

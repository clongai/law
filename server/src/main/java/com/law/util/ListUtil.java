package com.law.util;

import java.util.List;

public class ListUtil {

	public static boolean isBlank(List<?> list) {

		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(List<?> list) {
		if (list != null && !list.isEmpty()) {
			return true;
		}

		return false;
	}

}

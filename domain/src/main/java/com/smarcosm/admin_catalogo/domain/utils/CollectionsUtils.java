package com.smarcosm.admin_catalogo.domain.utils;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionsUtils {
    private CollectionsUtils() {
    }
    public static <IN, OUT> Set<OUT> mapTo(final Set<IN> list, final Function<IN, OUT> mapper){
        return list.stream().map(mapper).collect(Collectors.toSet());
    }
}

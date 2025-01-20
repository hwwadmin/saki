package com.saki.common.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List工具类
 *
 * @author hww
 */
public class Lists {

    private Lists() {

    }

    private static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> ofIterable(Iterable<T> collection) {
        Assert.isTrue(collection instanceof Collection);
        return new ArrayList<>((Collection<T>) collection);
    }

    @SafeVarargs
    public static <T> List<T> of(final T... elements) {
        final List<T> list = newArrayList();
        if (ObjectUtils.isNull(elements)) return list;
        list.addAll(Arrays.asList(elements));
        return list;
    }

    @SafeVarargs
    public static <T> List<T> immutable(final T... elements) {
        return List.of(elements);
    }

    @SafeVarargs
    public static <T> List<T> ofAndFilterNull(final T... elements) {
        final List<T> list = newArrayList();
        if (ObjectUtils.isNull(elements)) return list;
        list.addAll(Arrays.stream(elements).filter(Objects::nonNull).toList());
        return list;
    }

    public static <T, R> List<R> map(final List<T> list, final Function<? super T, ? extends R> mapper) {
        Assert.nonNull(mapper);
        if (ObjectUtils.isNull(list)) return of();
        return list.stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T, K> Map<K, T> toMap(final List<T> list, final Function<T, K> kMapper) {
        return toMap(list, kMapper, ObjectUtils::self);
    }

    public static <T, K, V> Map<K, V> toMap(final List<T> list, final Function<T, K> kMapper, final Function<T, V> vMapper) {
        Assert.nonNull(kMapper);
        Assert.nonNull(vMapper);
        if (ObjectUtils.isNull(list)) return Maps.of();
        return list.stream().filter(Objects::nonNull).collect(Collectors.toMap(kMapper, vMapper));
    }

    public static <K, T> Map<K, List<T>> toGroup(final List<T> list, final Function<T, K> mapper) {
        Assert.nonNull(mapper);
        if (ObjectUtils.isNull(list)) return Maps.of();
        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(mapper));
    }

    public static <T, V> Set<V> toSet(final List<T> list, final Function<T, V> mapper) {
        Assert.nonNull(mapper);
        if (ObjectUtils.isNull(list)) return new HashSet<>();
        return list.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toSet());
    }

    public static <T> List<T> filterNull(final List<T> list) {
        if (ObjectUtils.isNull(list)) return Lists.of();
        return list.stream().filter(ObjectUtils::nonNull).collect(Collectors.toList());
    }

}

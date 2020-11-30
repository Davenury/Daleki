package model.other;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListConcatener {
    public static<T> List<T> concatenate(List<T>... lists){
        return Stream.of(lists)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}

package com.bobocode.training.reflection;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private Class<T> targetType;
    private boolean compareOnlyAccessibleFields;
    private Field fieldToCompare;

    public RandomFieldComparator(Class<T> targetType) {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        this.targetType = targetType;
        this.compareOnlyAccessibleFields = compareOnlyAccessibleFields;
        choseRandomField();
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @SneakyThrows
    @Override
    public int compare(T o1, T o2) {
        Objects.requireNonNull(o1);
        Objects.requireNonNull(o2);

        fieldToCompare.setAccessible(true);
        Comparable first = (Comparable) fieldToCompare.get(o1);
        Comparable second = (Comparable) fieldToCompare.get(o2);

        if (Objects.isNull(first) && Objects.isNull(second)) {
            return 0;
        } else if (Objects.isNull(first)) {
            return 1;
        } else if (Objects.isNull(second)) {
            return -1;
        }

        return first.compareTo(fieldToCompare.get(o2));
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'", targetType.getTypeName(), fieldToCompare.getName());
    }

    @SneakyThrows
    private void choseRandomField() {
        if (this.targetType.getDeclaredFields().length == 0) {
            throw new IllegalArgumentException();
        }

        if (compareOnlyAccessibleFields) {
            List<Field> fields = Arrays.stream(targetType.getFields())
                    .filter(field -> Comparable.class.isAssignableFrom(field.getType()) || field.getType().isPrimitive())
                    .collect(toList());

            fields.addAll(getPrivateFieldsWithPublicGetters(targetType.getDeclaredFields()));
            int generatedInt = ThreadLocalRandom.current().nextInt(fields.size());
            this.fieldToCompare = fields.get(generatedInt);
        } else {
            List<Field> declaredFields = Arrays.stream(this.targetType.getDeclaredFields()).toList();
            int generatedInt = ThreadLocalRandom.current().nextInt(declaredFields.size());
            this.fieldToCompare = declaredFields.get(generatedInt);
        }
    }

    private List<Field> getPrivateFieldsWithPublicGetters(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(field -> Comparable.class.isAssignableFrom(field.getType()) || field.getType().isPrimitive())
                .filter(field -> Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers()))
                .filter(field -> findGetterForProperty(field.getName(), field.getType()).isPresent())
                .toList();
    }

    private Optional<Method> findGetterForProperty(String propertyName, Type propertyType) {
        String capitalizedPropertyName = StringUtils.capitalize(propertyName);
        boolean isBooleanType = Boolean.class.getTypeName().equals(propertyType.getClass().getTypeName());
        String getter = isBooleanType ? "is".concat(capitalizedPropertyName) : "get".concat(capitalizedPropertyName);
        try {
            return Optional.of(this.targetType.getMethod(getter));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }
}

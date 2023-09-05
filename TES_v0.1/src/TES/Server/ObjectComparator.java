package TES.Server;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ObjectComparator<T> {
    public List<String> findDifferenceFields(T obj1, T obj2) {
        List<String> differences = new ArrayList<>();
        Class<?> clazz = obj1.getClass();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);

                if ((value1 == null && value2 != null) || (value1 != null && !value1.equals(value2))) {
                    differences.add(field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return differences;
    }

    public <T> HashMap<String, Object> findDifferenceFieldsWithValues(T oldObj, T newObj) {
        HashMap<String, Object> differences = new HashMap<>();
        Class<?> clazz = oldObj.getClass();

        for (Field field : getAllFields(clazz)) {//clazz.getDeclaredFields() inherited fieldleri almıyor
            field.setAccessible(true);

            try {
                Object value1 = field.get(oldObj);
                Object value2 = field.get(newObj);

                if (!value1.equals(value2)) {
                    differences.put(field.getName(), value2);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return differences;
    }

    public List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }


    public static String serializeDifferences(HashMap<String, Object> differences) {
        StringBuilder serialized = new StringBuilder("{");
        boolean isFirst = true;
        for (HashMap.Entry<String, Object> entry : differences.entrySet()) {
            if (!isFirst) {
                serialized.append(", ");
            }
            String key = entry.getKey();
            Object value = entry.getValue();
            serialized.append("\"").append(key).append("\": \"").append(value).append("\"");
            isFirst = false;
        }
        serialized.append("}");
        return serialized.toString();
    }


}

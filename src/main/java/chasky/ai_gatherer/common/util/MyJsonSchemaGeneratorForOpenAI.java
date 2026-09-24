package chasky.ai_gatherer.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class MyJsonSchemaGeneratorForOpenAI {
    private final Set<String> visitedClasses = new HashSet<>();
    private final String schemaName;

    public MyJsonSchemaGeneratorForOpenAI(String schemaName) {
        this.schemaName = schemaName;
    }

    public String generateSchema(Class<?> clazz) {
        visitedClasses.clear();
        Map<String, Object> root = new LinkedHashMap<>();

        Map<String, Object> format = new LinkedHashMap<>();

        format.put("type", "json_schema");
        format.put("name", schemaName);
        format.put("schema", buildSchema(clazz));
        format.put("strict", true);

        root.put("format", format);

        return mapToJsonString(root);
    }

    private Map<String, Object> buildSchema(Class<?> clazz) {
        Map<String, Object> schema = new LinkedHashMap<>();
        schema.put("type", "object");

        Map<String, Object> properties = new LinkedHashMap<>();
        List<String> required = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            properties.put(fieldName, getFieldSchema(field));
            required.add(fieldName);
        }

        schema.put("properties", properties);
        schema.put("required", required);
        schema.put("additionalProperties", false);
        return schema;
    }

    private Map<String, Object> getFieldSchema(Field field) {
        Class<?> fieldType = field.getType();
        Type genericType = field.getGenericType();

        if (fieldType == String.class) {
            return createTypeSchema("string");
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return createTypeSchema("boolean");
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return createTypeSchema("integer");
        } else if (fieldType == Double.class || fieldType == double.class) {
            return createTypeSchema("number");
        } else if (List.class.isAssignableFrom(fieldType)) {
            return buildArraySchema(genericType);
        } else {
            return buildNestedObjectSchema(fieldType);
        }
    }

    private Map<String, Object> buildArraySchema(Type genericType) {
        Map<String, Object> arraySchema = new LinkedHashMap<>();
        arraySchema.put("type", "array");

        if (genericType instanceof ParameterizedType paramType) {
            Type elementType = paramType.getActualTypeArguments()[0];
            Class<?> elementClass = (Class<?>) elementType;
            arraySchema.put("items", buildNestedObjectSchema(elementClass));
        } else {
            arraySchema.put("items", createTypeSchema("string"));
        }

        return arraySchema;
    }

    private Map<String, Object> buildNestedObjectSchema(Class<?> clazz) {
        // String className = clazz.getName();

        // if (visitedClasses.contains(className)) {
        //     Map<String, Object> objectSchema = new LinkedHashMap<>();
        //     objectSchema.put("type", "object");
        //     objectSchema.put("additionalProperties", false);
        //     return objectSchema;
        // }

        // visitedClasses.add(className);

        Map<String, Object> objectSchema = new LinkedHashMap<>();
        objectSchema.put("type", "object");

        Map<String, Object> properties = new LinkedHashMap<>();
        List<String> required = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            properties.put(fieldName, getFieldSchema(field));
            required.add(fieldName);
        }

        objectSchema.put("properties", properties);
        objectSchema.put("required", required);
        objectSchema.put("additionalProperties", false);
        return objectSchema;
    }

    private Map<String, Object> createTypeSchema(String type) {
        Map<String, Object> typeSchema = new LinkedHashMap<>();
        typeSchema.put("type", type);
        return typeSchema;
    }

    private String mapToJsonString(Map<String, Object> map) {
        StringBuilder json = new StringBuilder();
        appendValue(json, map, 0);
        return json.toString();
    }

    private void appendValue(StringBuilder sb, Object value, int indent) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof Map) {
            appendMap(sb, (Map<?, ?>) value, indent);
        } else if (value instanceof List) {
            appendList(sb, (List<?>) value, indent);
        } else if (value instanceof String) {
            sb.append("\"").append(escapeJson((String) value)).append("\"");
        } else if (value instanceof Boolean) {
            sb.append(value);
        } else {
            sb.append(value);
        }
    }

    private void appendMap(StringBuilder sb, Map<?, ?> map, int indent) {
        sb.append("{");
        int nextIndent = indent + 2;
        boolean first = true;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first)
                sb.append(",");
            first = false;
            sb.append("\n").append(" ".repeat(nextIndent));
            sb.append("\"").append(entry.getKey()).append("\": ");
            appendValue(sb, entry.getValue(), nextIndent);
        }

        sb.append("\n").append(" ".repeat(indent)).append("}");
    }

    private void appendList(StringBuilder sb, List<?> list, int indent) {
        sb.append("[");
        int nextIndent = indent + 2;
        boolean first = true;

        for (Object item : list) {
            if (!first)
                sb.append(",");
            first = false;
            sb.append("\n").append(" ".repeat(nextIndent));
            appendValue(sb, item, nextIndent);
        }

        sb.append("\n").append(" ".repeat(indent)).append("]");
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

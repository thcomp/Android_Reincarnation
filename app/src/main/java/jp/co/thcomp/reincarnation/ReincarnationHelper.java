package jp.co.thcomp.reincarnation;

import android.os.Bundle;
import android.os.Parcelable;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by H_Tatsuguchi on 2016/11/20.
 */

public class ReincarnationHelper {
    private static final int NONE_ARRAY_FIELD_COUNT = -1;
    private static final String ABSOLUTE_NAME_SEPARATER = ".";
    private static final String DATA_COUNT_NAME = ABSOLUTE_NAME_SEPARATER + "+DataCount+";
    private static final String KEY_NAME = ABSOLUTE_NAME_SEPARATER + "+Key+";
    private static final String VALUE_NAME = ABSOLUTE_NAME_SEPARATER + "+Value+";
    private static final String CLASS_NAME = ABSOLUTE_NAME_SEPARATER + "+Class+";

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface TargetField {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface UntargetField {

    }

    public static void save(Object instance, Bundle outBundle) {
        Parameter param = new Parameter();
        param.targetInstance = instance;
        param.state = outBundle;
        param.haveTargetClassAnnotation = false;
        param.onlyPublic = true;
        saveLocal(param);
    }

    public static void restore(Object instance, Bundle savedInstance) {
        Parameter param = new Parameter();
        param.targetInstance = instance;
        param.state = savedInstance;
        param.haveTargetClassAnnotation = false;
        param.onlyPublic = true;
        restoreLocal(param);
    }

    public static void saveAll(Object instance, Bundle outBundle) {
        Parameter param = new Parameter();
        param.targetInstance = instance;
        param.state = outBundle;
        param.haveTargetClassAnnotation = false;
        param.onlyPublic = false;
        saveLocal(param);
    }

    public static void restoreAll(Object instance, Bundle savedInstance) {
        Parameter param = new Parameter();
        param.targetInstance = instance;
        param.state = savedInstance;
        param.haveTargetClassAnnotation = false;
        param.onlyPublic = false;
        restoreLocal(param);
    }

    private static void saveLocal(Parameter param) {
        // インスタンスのクラス名称を保持
        param.state.putString(param.parentConcatName + CLASS_NAME, param.targetInstance.getClass().getName());

        Field[] fields = null;
        if (param.onlyPublic) {
            fields = param.targetInstance.getClass().getFields();
        } else {
            ArrayList<Field> fieldList = new ArrayList<Field>();
            getAllFields(param.targetInstance.getClass(), fieldList);
            fields = fieldList.toArray(new Field[fieldList.size()]);
        }

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                boolean targetField = param.haveTargetClassAnnotation;

                if (targetField) {
                    // UntargetField指定されているか確認
                    targetField = !containAnnotation(field.getAnnotations(), UntargetField.class);
                } else {
                    // TargetFieldが指定されているか確認
                    targetField = containAnnotation(field.getAnnotations(), TargetField.class);
                }

                if (targetField) {
                    boolean primitiveType = savePrimitiveData(param, field);

                    if (!primitiveType) {
                        try {
                            field.setAccessible(true);
                            Object fieldObject = field.get(param.targetInstance);
                            Parameter nextParam = new Parameter(param);
                            nextParam.haveTargetClassAnnotation = true;

                            String baseName = createAbsoluteName(param.parentConcatName, field);
                            int dataCount = getDataCount(fieldObject);

                            if (fieldObject instanceof String) {
                                // 非配列のオブジェクトなので、dataCountとしてNONE_ARRAY_FIELD_COUNTを入れて置き、展開時に配列か否かの判断に使用
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                param.state.putString(createAbsoluteName(param.parentConcatName, field), (String) fieldObject);
                            } else if (fieldObject instanceof String[]) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                param.state.putStringArray(createAbsoluteName(param.parentConcatName, field), (String[]) fieldObject);
                            } else if (fieldObject instanceof Parcelable) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                param.state.putParcelable(createAbsoluteName(param.parentConcatName, field), (Parcelable) fieldObject);
                            } else if (fieldObject instanceof Parcelable[]) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                param.state.putParcelableArray(createAbsoluteName(param.parentConcatName, field), (Parcelable[]) fieldObject);
                            } else if (fieldObject instanceof Object[]) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                if (dataCount > 0) {
                                    int index = 0;
                                    Bundle nextBundle = new Bundle();
                                    param.state.putBundle(baseName, nextBundle);
                                    nextParam.state = nextBundle;

                                    for (Object listItem : (Object[]) fieldObject) {
                                        nextParam.parentConcatName = baseName + VALUE_NAME + index;
                                        nextParam.targetInstance = listItem;
                                        nextParam.state.putString(baseName + VALUE_NAME + CLASS_NAME + index, listItem.getClass().getName());
                                        if (!savePrimitiveClassData(nextParam, listItem)) {
                                            saveLocal(nextParam);
                                        }
                                        index++;
                                    }
                                }
                            } else if (fieldObject instanceof List) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                if (dataCount > 0) {
                                    int index = 0;
                                    Bundle nextBundle = new Bundle();
                                    param.state.putBundle(baseName, nextBundle);
                                    nextParam.state = nextBundle;

                                    for (Object listItem : (List) fieldObject) {
                                        nextParam.parentConcatName = baseName + VALUE_NAME + index;
                                        nextParam.targetInstance = listItem;
                                        nextParam.state.putString(baseName + VALUE_NAME + CLASS_NAME + index, listItem.getClass().getName());
                                        if (!savePrimitiveClassData(nextParam, listItem)) {
                                            saveLocal(nextParam);
                                        }
                                        index++;
                                    }
                                }
                            } else if (fieldObject instanceof Map) {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);
                                if (dataCount > 0) {
                                    int index = 0;
                                    Bundle nextBundle = new Bundle();
                                    param.state.putBundle(baseName, nextBundle);
                                    nextParam.state = nextBundle;

                                    for (Object mapEntry : ((Map) fieldObject).entrySet()) {
                                        nextParam.parentConcatName = baseName + KEY_NAME + index;
                                        nextParam.targetInstance = ((Map.Entry) mapEntry).getKey();
                                        nextParam.state.putString(baseName + KEY_NAME + CLASS_NAME + index, nextParam.targetInstance.getClass().getName());
                                        if (!savePrimitiveClassData(nextParam, nextParam.targetInstance)) {
                                            saveLocal(nextParam);
                                        }

                                        nextParam.parentConcatName = baseName + VALUE_NAME + index;
                                        nextParam.targetInstance = ((Map.Entry) mapEntry).getValue();
                                        nextParam.state.putString(baseName + VALUE_NAME + CLASS_NAME + index, nextParam.targetInstance.getClass().getName());
                                        if (!savePrimitiveClassData(nextParam, nextParam.targetInstance)) {
                                            saveLocal(nextParam);
                                        }
                                        index++;
                                    }
                                }
                            } else {
                                param.state.putInt(baseName + DATA_COUNT_NAME, dataCount);

                                Bundle nextBundle = new Bundle();
                                param.state.putBundle(baseName, nextBundle);
                                nextParam.state = nextBundle;

                                nextParam.parentConcatName = createAbsoluteName(param.parentConcatName, field);
                                nextParam.targetInstance = field.get(param.targetInstance);
                                if (nextParam.targetInstance != null) {
                                    saveLocal(nextParam);
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                }
            }
        }
    }

    private static void restoreLocal(Parameter param) {
        Field[] fields = null;
        if (param.onlyPublic) {
            fields = param.targetInstance.getClass().getFields();
        } else {
            ArrayList<Field> fieldList = new ArrayList<Field>();
            getAllFields(param.targetInstance.getClass(), fieldList);
            fields = fieldList.toArray(new Field[fieldList.size()]);
        }

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                boolean fieldInObject = field.getDeclaringClass().getName().equals(Object.class.getName());
                boolean targetField = param.haveTargetClassAnnotation;

                if (targetField) {
                    // UntargetField指定されているか確認
                    targetField = !containAnnotation(field.getAnnotations(), UntargetField.class);
                } else {
                    // TargetFieldが指定されているか確認
                    targetField = containAnnotation(field.getAnnotations(), TargetField.class);
                }

                if (targetField && !fieldInObject) {
                    boolean primitiveType = restorePrimitiveData(param, field);

                    if (!primitiveType) {
                        try {
                            field.setAccessible(true);
                            String baseName = createAbsoluteName(param.parentConcatName, field);
                            int dataCount = param.state.getInt(baseName + DATA_COUNT_NAME, NONE_ARRAY_FIELD_COUNT);
                            Object fieldObject = field.get(param.targetInstance);

                            if (fieldObject == null) {
                                if (dataCount == NONE_ARRAY_FIELD_COUNT) {
                                    // 配列ではないデータを保存
                                    fieldObject = createNewInstance(param.state.get(baseName), field.getType().getName());
                                } else {
                                    fieldObject = createNewCollectionInstance(field, dataCount);
                                }
                                field.set(param.targetInstance, fieldObject);
                            }

                            if (fieldObject != null) {
                                Parameter nextParam = new Parameter(param);
                                nextParam.haveTargetClassAnnotation = true;

                                if (fieldObject instanceof String) {
                                    field.set(param.targetInstance, param.state.getString(baseName));
                                } else if (fieldObject instanceof String[]) {
                                    field.set(param.targetInstance, param.state.getStringArray(baseName));
                                } else if (fieldObject instanceof Parcelable) {
                                    field.set(param.targetInstance, param.state.getParcelable(baseName));
                                } else if (fieldObject instanceof Parcelable[]) {
                                    field.set(param.targetInstance, param.state.getParcelableArray(baseName));
                                } else if (fieldObject instanceof Object[]) {
                                    if (dataCount > 0) {
                                        nextParam.state = param.state.getBundle(baseName);

                                        if (nextParam.state != null) {
                                            for (int i = 0; i < dataCount; i++) {
                                                nextParam.parentConcatName = baseName + VALUE_NAME + i;

                                                String arrayItemClassName = nextParam.state.getString(baseName + VALUE_NAME + CLASS_NAME + i, null);
                                                if (arrayItemClassName != null) {
                                                    Object arrayItem = createNewInstance(nextParam.state.get(nextParam.parentConcatName), arrayItemClassName);
                                                    ((Object[]) fieldObject)[i] = arrayItem;

                                                    if (!isPrimitiveClassData(arrayItem)) {
                                                        nextParam.targetInstance = arrayItem;
                                                        restoreLocal(nextParam);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (fieldObject instanceof List) {
                                    if (dataCount > 0) {
                                        nextParam.state = param.state.getBundle(baseName);

                                        if (nextParam.state != null) {
                                            for (int i = 0; i < dataCount; i++) {
                                                nextParam.parentConcatName = baseName + VALUE_NAME + i;

                                                String arrayItemClassName = nextParam.state.getString(baseName + VALUE_NAME + CLASS_NAME + i, null);
                                                if (arrayItemClassName != null) {
                                                    Object arrayItem = createNewInstance(nextParam.state.get(nextParam.parentConcatName), arrayItemClassName);
                                                    ((List) fieldObject).add(arrayItem);

                                                    if (!isPrimitiveClassData(arrayItem)) {
                                                        nextParam.targetInstance = arrayItem;
                                                        restoreLocal(nextParam);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (fieldObject instanceof Map) {
                                    if (dataCount > 0) {
                                        nextParam.state = param.state.getBundle(baseName);

                                        if (nextParam.state != null) {
                                            for (int i = 0; i < dataCount; i++) {
                                                Object keyObject = null;
                                                Object valueObject = null;

                                                String keyName = baseName + KEY_NAME + i;
                                                String valueName = baseName + VALUE_NAME + i;

                                                String keyArrayItemClassName = nextParam.state.getString(baseName + KEY_NAME + CLASS_NAME + i, null);
                                                if (keyArrayItemClassName != null) {
                                                    Object arrayItem = createNewInstance(nextParam.state.get(keyName), keyArrayItemClassName);
                                                    keyObject = arrayItem;
                                                }

                                                String valueArrayItemClassName = nextParam.state.getString(baseName + VALUE_NAME + CLASS_NAME + i, null);
                                                if (valueArrayItemClassName != null) {
                                                    Object arrayItem = createNewInstance(nextParam.state.get(valueName), valueArrayItemClassName);
                                                    valueObject = arrayItem;
                                                }

                                                // マップに一旦インスタンスを設定
                                                if (keyObject != null && valueObject != null) {
                                                    ((Map) fieldObject).put(keyObject, valueObject);
                                                }

                                                // キー向けのインスタンスの展開
                                                if (!isPrimitiveClassData(keyObject)) {
                                                    nextParam.targetInstance = keyObject;
                                                    nextParam.parentConcatName = keyName;
                                                    try {
                                                        restoreLocal(nextParam);
                                                    } catch (Exception e) {
                                                        // 展開に失敗したので、削除
                                                        ((Map) fieldObject).remove(keyObject);
                                                        valueObject = null;
                                                    }
                                                }

                                                if (valueObject != null && !isPrimitiveClassData(valueObject)) {
                                                    nextParam.targetInstance = valueObject;
                                                    nextParam.parentConcatName = valueName;
                                                    try {
                                                        restoreLocal(nextParam);
                                                    } catch (Exception e) {
                                                        // 展開に失敗したので、削除
                                                        ((Map) fieldObject).remove(keyObject);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    nextParam.state = param.state.getBundle(baseName);

                                    if (nextParam.state != null) {
                                        nextParam.parentConcatName = baseName;
                                        nextParam.targetInstance = field.get(param.targetInstance);
                                        restoreLocal(nextParam);
                                    }
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                }
            }
        }
    }

    private static boolean containAnnotation(Annotation[] annotationArray, Class targetAnnotationClass) {
        boolean ret = false;

        if (annotationArray != null && annotationArray.length > 0) {
            for (Annotation annotation : annotationArray) {
                if (annotation.annotationType().equals(targetAnnotationClass)) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    private static boolean savePrimitiveData(Parameter param, Field field) {
        boolean primitiveType = false;

        try {
            String fieldTypeName = field.getType().getName();

            field.setAccessible(true);
            if (fieldTypeName.equals(byte.class.getName())) {
                primitiveType = true;
                param.state.putByte(createAbsoluteName(param.parentConcatName, field), field.getByte(param.targetInstance));
            } else if (fieldTypeName.equals(byte[].class.getName())) {
                primitiveType = true;
                param.state.putByteArray(createAbsoluteName(param.parentConcatName, field), (byte[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(char.class.getName())) {
                primitiveType = true;
                param.state.putChar(createAbsoluteName(param.parentConcatName, field), field.getChar(param.targetInstance));
            } else if (fieldTypeName.equals(char[].class.getName())) {
                primitiveType = true;
                param.state.putCharArray(createAbsoluteName(param.parentConcatName, field), (char[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(short.class.getName())) {
                primitiveType = true;
                param.state.putShort(createAbsoluteName(param.parentConcatName, field), field.getShort(param.targetInstance));
            } else if (fieldTypeName.equals(short[].class.getName())) {
                primitiveType = true;
                param.state.putShortArray(createAbsoluteName(param.parentConcatName, field), (short[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(int.class.getName())) {
                primitiveType = true;
                param.state.putInt(createAbsoluteName(param.parentConcatName, field), field.getInt(param.targetInstance));
            } else if (fieldTypeName.equals(int[].class.getName())) {
                primitiveType = true;
                param.state.putIntArray(createAbsoluteName(param.parentConcatName, field), (int[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(long.class.getName())) {
                primitiveType = true;
                param.state.putLong(createAbsoluteName(param.parentConcatName, field), field.getLong(param.targetInstance));
            } else if (fieldTypeName.equals(long[].class.getName())) {
                primitiveType = true;
                param.state.putLongArray(createAbsoluteName(param.parentConcatName, field), (long[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(float.class.getName())) {
                primitiveType = true;
                param.state.putFloat(createAbsoluteName(param.parentConcatName, field), field.getFloat(param.targetInstance));
            } else if (fieldTypeName.equals(float[].class.getName())) {
                primitiveType = true;
                param.state.putFloatArray(createAbsoluteName(param.parentConcatName, field), (float[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(double.class.getName())) {
                primitiveType = true;
                param.state.putDouble(createAbsoluteName(param.parentConcatName, field), field.getDouble(param.targetInstance));
            } else if (fieldTypeName.equals(double[].class.getName())) {
                primitiveType = true;
                param.state.putDoubleArray(createAbsoluteName(param.parentConcatName, field), (double[]) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Byte.class.getName())) {
                primitiveType = true;
                param.state.putByte(createAbsoluteName(param.parentConcatName, field), (Byte) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Byte[].class.getName())) {
                primitiveType = true;
                param.state.putByteArray(createAbsoluteName(param.parentConcatName, field), (byte[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Character.class.getName())) {
                primitiveType = true;
                param.state.putChar(createAbsoluteName(param.parentConcatName, field), (Character) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Character[].class.getName())) {
                primitiveType = true;
                param.state.putCharArray(createAbsoluteName(param.parentConcatName, field), (char[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Short.class.getName())) {
                primitiveType = true;
                param.state.putShort(createAbsoluteName(param.parentConcatName, field), (Short) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Short[].class.getName())) {
                primitiveType = true;
                param.state.putShortArray(createAbsoluteName(param.parentConcatName, field), (short[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Integer.class.getName())) {
                primitiveType = true;
                param.state.putInt(createAbsoluteName(param.parentConcatName, field), (Integer) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Integer[].class.getName())) {
                primitiveType = true;
                param.state.putIntArray(createAbsoluteName(param.parentConcatName, field), (int[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Long.class.getName())) {
                primitiveType = true;
                param.state.putLong(createAbsoluteName(param.parentConcatName, field), (Long) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Long[].class.getName())) {
                primitiveType = true;
                param.state.putLongArray(createAbsoluteName(param.parentConcatName, field), (long[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Float.class.getName())) {
                primitiveType = true;
                param.state.putFloat(createAbsoluteName(param.parentConcatName, field), (Float) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Float[].class.getName())) {
                primitiveType = true;
                param.state.putFloatArray(createAbsoluteName(param.parentConcatName, field), (float[]) changeToPrimitive(field.get(param.targetInstance)));
            } else if (fieldTypeName.equals(Double.class.getName())) {
                primitiveType = true;
                param.state.putDouble(createAbsoluteName(param.parentConcatName, field), (Double) field.get(param.targetInstance));
            } else if (fieldTypeName.equals(Double[].class.getName())) {
                primitiveType = true;
                param.state.putDoubleArray(createAbsoluteName(param.parentConcatName, field), (double[]) changeToPrimitive(field.get(param.targetInstance)));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }

        return primitiveType;
    }

    private static boolean savePrimitiveClassData(Parameter param, Object value) {
        boolean primitiveClassType = false;
        String fieldTypeName = value.getClass().getName();

        if (fieldTypeName.equals(Byte.class.getName())) {
            primitiveClassType = true;
            param.state.putByte(param.parentConcatName, (Byte) value);
        } else if (fieldTypeName.equals(Character.class.getName())) {
            primitiveClassType = true;
            param.state.putChar(param.parentConcatName, (Character) value);
        } else if (fieldTypeName.equals(Short.class.getName())) {
            primitiveClassType = true;
            param.state.putShort(param.parentConcatName, (Short) value);
        } else if (fieldTypeName.equals(Integer.class.getName())) {
            primitiveClassType = true;
            param.state.putInt(param.parentConcatName, (Integer) value);
        } else if (fieldTypeName.equals(Long.class.getName())) {
            primitiveClassType = true;
            param.state.putLong(param.parentConcatName, (Long) value);
        } else if (fieldTypeName.equals(Float.class.getName())) {
            primitiveClassType = true;
            param.state.putFloat(param.parentConcatName, (Float) value);
        } else if (fieldTypeName.equals(Double.class.getName())) {
            primitiveClassType = true;
            param.state.putDouble(param.parentConcatName, (Double) value);
        } else if (fieldTypeName.equals(String.class.getName())) {
            primitiveClassType = true;
            param.state.putString(param.parentConcatName, (String) value);
        }

        return primitiveClassType;
    }

    private static boolean isPrimitiveClassData(Object value) {
        boolean primitiveClassType = false;
        String fieldTypeName = value.getClass().getName();

        if (fieldTypeName.equals(Byte.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Character.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Short.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Integer.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Long.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Float.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(Double.class.getName())) {
            primitiveClassType = true;
        } else if (fieldTypeName.equals(String.class.getName())) {
            primitiveClassType = true;
        }

        return primitiveClassType;
    }

    private static boolean restorePrimitiveData(Parameter param, Field field) {
        boolean primitiveType = false;

        try {
            String fieldTypeName = field.getType().getName();

            field.setAccessible(true);
            if (fieldTypeName.equals(byte.class.getName())) {
                primitiveType = true;
                field.setByte(param.targetInstance, param.state.getByte(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(byte[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(char.class.getName())) {
                primitiveType = true;
                field.setChar(param.targetInstance, param.state.getChar(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(char[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(short.class.getName())) {
                primitiveType = true;
                field.setShort(param.targetInstance, param.state.getShort(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(short[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(int.class.getName())) {
                primitiveType = true;
                field.setInt(param.targetInstance, param.state.getInt(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(int[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(long.class.getName())) {
                primitiveType = true;
                field.setLong(param.targetInstance, param.state.getLong(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(long[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(float.class.getName())) {
                primitiveType = true;
                field.setFloat(param.targetInstance, param.state.getFloat(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(float[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(double.class.getName())) {
                primitiveType = true;
                field.setDouble(param.targetInstance, param.state.getDouble(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(double[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Byte.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Byte) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Byte[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Character.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Character) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Character[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Short.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Short) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Short[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Integer.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Integer) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Integer[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Long.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Long) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Long[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Float.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Float) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Float[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.get(createAbsoluteName(param.parentConcatName, field))));
            } else if (fieldTypeName.equals(Double.class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, (Double) param.state.get(createAbsoluteName(param.parentConcatName, field)));
            } else if (fieldTypeName.equals(Double[].class.getName())) {
                primitiveType = true;
                field.set(param.targetInstance, changeToClass(param.state.getDoubleArray(createAbsoluteName(param.parentConcatName, field))));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }

        return primitiveType;
    }

    private static String createAbsoluteName(String parentConcatName, Field instance) {
        return new StringBuilder(parentConcatName).append(ABSOLUTE_NAME_SEPARATER).append(instance.getName()).toString();
    }

    private static int getDataCount(Object fieldObject) {
        int dataCount = NONE_ARRAY_FIELD_COUNT;

        if (fieldObject != null) {
            if (fieldObject instanceof List) {
                List listObject = (List) fieldObject;
                dataCount = listObject.size();
            } else if (fieldObject instanceof Map) {
                Map mapObject = (Map) fieldObject;
                dataCount = mapObject.size();
            } else if (fieldObject instanceof Object[]) {
                Object[] objectArray = (Object[]) fieldObject;
                dataCount = objectArray.length;
            } else if (fieldObject instanceof byte[]) {
                byte[] byteArray = (byte[]) fieldObject;
                dataCount = byteArray.length;
            } else if (fieldObject instanceof char[]) {
                char[] charArray = (char[]) fieldObject;
                dataCount = charArray.length;
            } else if (fieldObject instanceof short[]) {
                short[] shortArray = (short[]) fieldObject;
                dataCount = shortArray.length;
            } else if (fieldObject instanceof int[]) {
                int[] intArray = (int[]) fieldObject;
                dataCount = intArray.length;
            } else if (fieldObject instanceof long[]) {
                long[] longArray = (long[]) fieldObject;
                dataCount = longArray.length;
            } else if (fieldObject instanceof float[]) {
                float[] floatArray = (float[]) fieldObject;
                dataCount = floatArray.length;
            } else if (fieldObject instanceof double[]) {
                double[] doubleArray = (double[]) fieldObject;
                dataCount = doubleArray.length;
            }
        }

        return dataCount;
    }

    private static Object changeToClass(Object object) {
        Object ret = null;

        if (object instanceof byte[]) {
            byte[] srcArray = (byte[]) object;
            Byte[] destArray = new Byte[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof char[]) {
            char[] srcArray = (char[]) object;
            Character[] destArray = new Character[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof short[]) {
            short[] srcArray = (short[]) object;
            Short[] destArray = new Short[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof int[]) {
            int[] srcArray = (int[]) object;
            Integer[] destArray = new Integer[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof long[]) {
            long[] srcArray = (long[]) object;
            Long[] destArray = new Long[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof float[]) {
            float[] srcArray = (float[]) object;
            Float[] destArray = new Float[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof double[]) {
            double[] srcArray = (double[]) object;
            Double[] destArray = new Double[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        }


        return ret;
    }

    private static Object changeToPrimitive(Object object) {
        Object ret = null;

        if (object instanceof Byte[]) {
            Byte[] srcArray = (Byte[]) object;
            byte[] destArray = new byte[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Character[]) {
            Character[] srcArray = (Character[]) object;
            char[] destArray = new char[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Short[]) {
            Short[] srcArray = (Short[]) object;
            short[] destArray = new short[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Integer[]) {
            Integer[] srcArray = (Integer[]) object;
            int[] destArray = new int[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Long[]) {
            Long[] srcArray = (Long[]) object;
            long[] destArray = new long[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Float[]) {
            Float[] srcArray = (Float[]) object;
            float[] destArray = new float[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        } else if (object instanceof Double[]) {
            Double[] srcArray = (Double[]) object;
            double[] destArray = new double[srcArray.length];

            for (int i = 0, size = srcArray.length; i < size; i++) {
                destArray[i] = srcArray[i];
            }

            ret = destArray;
        }


        return ret;
    }

    public static Object createNewInstance(Object valueObject, String className) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Object ret = null;

        Class itemClass = Class.forName(className);
        if (itemClass.equals(Byte.class)) {
            ret = Byte.valueOf((byte) valueObject);
        } else if (itemClass.equals(Character.class)) {
            ret = Character.valueOf((char) valueObject);
        } else if (itemClass.equals(Short.class)) {
            ret = Short.valueOf((short) valueObject);
        } else if (itemClass.equals(Integer.class)) {
            ret = Integer.valueOf((int) valueObject);
        } else if (itemClass.equals(Long.class)) {
            ret = Long.valueOf((long) valueObject);
        } else if (itemClass.equals(Float.class)) {
            ret = Float.valueOf((float) valueObject);
        } else if (itemClass.equals(Double.class)) {
            ret = Double.valueOf((double) valueObject);
        } else if (itemClass.equals(String.class)) {
            ret = valueObject;
        } else {
            Constructor arrayItemConstructor = itemClass.getConstructor();
            ret = arrayItemConstructor.newInstance();
        }

        return ret;
    }

    private static Object createNewCollectionInstance(Field field, int dataCount) {
        Object ret = null;
        Class fieldTypeClass = field.getType();
        try {
            Constructor fieldClassConstructor = fieldTypeClass.getConstructor();
            ret = fieldClassConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            // 配列且つ非primitiveなデータの配列と判断し、Object[dataCount]を返却
            ret = new Object[dataCount];
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static void getAllFields(Class targetClass, List<Field> fieldList){
        Field[] fields = targetClass.getDeclaredFields();

        if(fields != null && fields.length > 0){
            for(Field field : fields){
                fieldList.add(field);
            }
        }

        Class superClass = targetClass.getSuperclass();
        if(!superClass.equals(Object.class)){
            getAllFields(superClass, fieldList);
        }
    }

    private static class Parameter {
        public String parentConcatName = "";
        public Object targetInstance;
        public Bundle state;
        public boolean haveTargetClassAnnotation = false;
        public boolean onlyPublic = true;

        public Parameter() {
            // 処理なし
        }

        public Parameter(Parameter orgParam) {
            this.parentConcatName = orgParam.parentConcatName;
            this.targetInstance = orgParam.targetInstance;
            this.state = orgParam.state;
            this.haveTargetClassAnnotation = orgParam.haveTargetClassAnnotation;
            this.onlyPublic = orgParam.onlyPublic;
        }
    }
}

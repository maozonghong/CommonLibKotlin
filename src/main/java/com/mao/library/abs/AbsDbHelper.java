package com.mao.library.abs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.mao.library.interfaces.NotDBValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 所有数据库管理器需要继承的抽象类，实现了大部分建表，更新，增删改查，排序等功能 使用完之后必须第一时间调用{@link #close()}
 *
 * @param <T>
 *            对应的数据库实体对象
 */
public abstract class AbsDbHelper<T extends AbsDBModel> extends SQLiteOpenHelper {
    private static final String NOT_NULL = "not_null";
    private static boolean isCreatingOrUpdating;
    // private final String getTabName();
    protected String byUser;

    private static final Map<Class<? extends AbsDBModel>, Class<? extends AbsDbHelper<?>>> allDBHelpers = new HashMap<Class<? extends AbsDBModel>, Class<? extends AbsDbHelper<?>>>();

    /**
     * 注册数据库管理器和数据库对象，需在application初始化时注册所有的。
     *
     * @param modelClass
     * @param dbHelperClass
     */
    public static void registerDB(Class<? extends AbsDBModel> modelClass, Class<? extends AbsDbHelper<?>> dbHelperClass) {
        allDBHelpers.put(modelClass, dbHelperClass);
    }

    public AbsDbHelper() {
        super(AbsApplication.getInstance(), AbsApplication.getInstance().getDB_NAME(), null, AbsApplication.VERSION_CODE);
    }

    /**
     * @param byUser
     *            操作数据库的用户id
     */
    public AbsDbHelper(String byUser) {
        super(AbsApplication.getInstance(), AbsApplication.getInstance().getDB_NAME(), null, AbsApplication.VERSION_CODE);
        this.byUser = byUser;
    }

    private AbsDbHelper(String byUser, SQLiteDatabase.CursorFactory factory, int version) {
        super(AbsApplication.getInstance(), AbsApplication.getInstance().getDB_NAME(), factory, AbsApplication.VERSION_CODE);
        this.byUser = byUser;
    }

    /**
     * 获取本数据库表的表名
     *
     * @return
     */
    protected abstract String getTabName();

    /**
     * 主键的名称，必需与对应{@link AbsDBModel}中作为id的成员变量名称统一
     *
     * @return
     */
    protected String getIdName() {
        return "uid";
    }

    /**
     * 本表是否需要主键自增长
     *
     * @return
     */
    protected boolean needAutoincrement() {
        return false;
    }

    /**
     * 本表是否在清空数据库时会被清空，默认为true
     *
     * @return
     */
    protected boolean canClear() {
        return true;
    }

    /**
     * 默认的排序方式，必须使用对应{@link AbsDBModel}中某字段名，倒序则字段名后加上" desc"。
     *
     * @return
     */
    protected String getDefaultOrderBy() {
        return null;
    }

    @Override
    public final synchronized void onCreate(SQLiteDatabase db) {
        if (AbsApplication.isOnMainProcess()) {
            Log.i("mao", "AbsDbHelper:onCreate");
            if (!isCreatingOrUpdating) {
                isCreatingOrUpdating = true;
                create(db);
                isCreatingOrUpdating = false;
            }
        }
    }

    private void create(SQLiteDatabase db) {
        synchronized (NOT_NULL) {
            for (Map.Entry<Class<? extends AbsDBModel>, Class<? extends AbsDbHelper<?>>> entry : allDBHelpers.entrySet()) {
                AbsDbHelper<?> dbHelper;
                try {
                    dbHelper = entry.getValue().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                createTable(db, dbHelper, entry.getKey(), dbHelper.getTabName());
            }
        }
    }

    @Override
    public synchronized final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (AbsApplication.isOnMainProcess()) {
            Log.i("mao", "AbsDbHelper:onUpgrade");
            if (!isCreatingOrUpdating) {
                upgrade(db);
            }
        }
    }

    private synchronized void upgrade() {
        upgrade(getWritableDatabase());
    }

    private synchronized void upgrade(SQLiteDatabase db) {
        if (!isCreatingOrUpdating) {
            Log.i("mao", "AbsDbHelper:upgrade");
            synchronized (NOT_NULL) {
                Set<String> created_tables = new HashSet<String>();
                Cursor cursor = db.rawQuery("select name from sqlite_master where type='table';", null);
                while (cursor.moveToNext()) {
                    created_tables.add(cursor.getString(0));
                }
                cursor.close();

                for (Map.Entry<Class<? extends AbsDBModel>, Class<? extends AbsDbHelper<?>>> entry : allDBHelpers.entrySet()) {
                    AbsDbHelper<?> dbHelper;
                    try {
                        dbHelper = entry.getValue().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    String tabName = dbHelper.getTabName();

                    if (!created_tables.contains(tabName)) {
                        createTable(db, dbHelper, entry.getKey(), tabName);
                        continue;
                    }

                    created_tables.remove(tabName);

                    cursor = db.query(tabName, null, null, null, null, null, null, "0");

                    HashSet<String> oldNames = new HashSet<String>();
                    for (String string : cursor.getColumnNames()) {
                        oldNames.add(string);
                    }
                    cursor.close();

                    db.execSQL("ALTER TABLE " + tabName + " RENAME TO __temp__" + tabName);
                    Set<String> newNames = createTable(db, dbHelper, entry.getKey(), tabName);

                    StringBuilder builder = new StringBuilder(128);
                    builder.append("INSERT INTO ");
                    builder.append(tabName);
                    builder.append("(");

                    StringBuilder selectBuilder = new StringBuilder(128);
                    for (String string : newNames) {
                        if (oldNames.contains(string)) {
                            builder.append(string);
                            builder.append(",");
                            selectBuilder.append(string);
                            selectBuilder.append(",");
                        }
                    }

                    if (builder.length() > 0) {
                        builder.deleteCharAt(builder.length() - 1);
                        selectBuilder.deleteCharAt(selectBuilder.length() - 1);
                    }

                    builder.append(")");
                    builder.append(" SELECT ");
                    builder.append(selectBuilder);
                    builder.append(" FROM __temp__");
                    builder.append(tabName);

                    db.execSQL(builder.toString());
                    db.execSQL("DROP TABLE __temp__" + tabName);
                }

                for (String tabName : created_tables) {
                    if (!tabName.equals("sqlite_sequence")) {
                        db.execSQL("DROP TABLE " + tabName);
                        Log.i("mao", "DROP:" + tabName);
                    }
                }
            }
            isCreatingOrUpdating = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        Type genType = getClass().getGenericSuperclass();
        while (genType != null && !(genType instanceof ParameterizedType)) {
            genType = genType.getClass().getGenericSuperclass();
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<T> entityClass = (Class<T>) params[0];

        return entityClass;
    }

    private Set<String> createTable(SQLiteDatabase db, AbsDbHelper<?> dbHelper, Class<? extends AbsDBModel> modelClz, String tabName) {
        StringBuilder builder = new StringBuilder(128);

        builder.append("create table if not exists " + tabName);
        builder.append("(");

        boolean needAutoincrement = dbHelper.needAutoincrement();

        Set<String> result = new HashSet<String>();

        String idName = dbHelper.getIdName();

        for (Field field : modelClz.getFields()) {

            if (field.isAnnotationPresent(NotDBValue.class) || Modifier.isStatic(field.getModifiers())){
                continue;
            }

            String name = field.getName();

            if (name.equals(idName)) {
                builder.append(name);

                result.add(name);

                if (needAutoincrement) {
                    builder.append(" integer primary key autoincrement,");
                } else {
                    builder.append(" varchar,");
                }
                continue;
            }

            Class<?> fieldClz = field.getType();

            String type = "";

            if (String.class.isAssignableFrom(fieldClz)) {
                type = "varchar";
            } else if (fieldClz.isEnum()) {
                type = "varchar";
            } else if (fieldClz == boolean.class || fieldClz == Boolean.class) {
                type = "integer";
            } else if (fieldClz == int.class || fieldClz == Integer.class) {
                type = "integer";
            } else if (fieldClz == long.class || fieldClz == Long.class) {
                type = "integer";
            } else if (fieldClz == float.class || fieldClz == Float.class) {
                type = "float";
            } else if (fieldClz == double.class || fieldClz == Double.class) {
                type = "double";
            } else if (Date.class.isAssignableFrom(fieldClz)) {
                type = "integer";
            } else if (AbsDBModel.class.isAssignableFrom(fieldClz)) {
                type = "varchar";
            } else if (fieldClz.isArray()) {
                type = "varchar";
            } else if (List.class.isAssignableFrom(fieldClz)) {
                type = "varchar";
            } else {
                continue;
            }

            result.add(name);

            builder.append(name);
            builder.append(" ");
            builder.append(type);
            builder.append(",");
        }

        if (!needAutoincrement) {
            builder.append("primary key (byUser," + idName + "))");
        } else {
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append(")");
        }

        db.execSQL(builder.toString());

        Log.i("mao", "create:" + tabName);

        return result;
    }

    /**
     * 新增或更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 是否成功
     */
    public final boolean saveOrUpdate(T t, boolean saveChildren) {
        if (count(t.getId()) > 0) {
            return update(t, saveChildren);
        } else {
            return save(t, saveChildren) > 0;
        }
    }

    /**
     * 新增或更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 是否成功
     */
    public final static boolean saveOrUpdateDBModel(AbsDBModel t, boolean saveChildren) {
        return saveOrUpdateDBModel(t, saveChildren, null);
    }

    /**
     * 新增或更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @param byUser
     *            操作数据库的用户id
     * @return 是否成功
     */
    public final static <T extends AbsDBModel> boolean saveOrUpdateDBModel(T t, boolean saveChildren, String byUser) {
        boolean result = false;
        AbsDbHelper<T> dbHelper = getDbHelper(t, byUser);
        if (dbHelper != null) {
            result = dbHelper.saveOrUpdate(t, saveChildren);
            dbHelper.close();
        }
        return result;
    }

    /**
     * 更新多条数据
     *
     * @param collection
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 更新成功的条数
     */
    public final int update(Collection<T> collection, boolean saveChildren) {
        int count = 0;
        for (T t : collection) {
            if (update(t, saveChildren)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 更新多条数据
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 是否成功
     */
    public final static int updateDBModel(Collection<AbsDBModel> collection, boolean saveChildren) {
        return updateDBModel(collection, saveChildren, null);
    }

    /**
     * 更新多条数据
     * @param saveChildren
     *            是否新增或更新子对象
     * @param byUser
     *            操作数据库的用户id
     * @return 是否成功
     */
    public final static <T extends AbsDBModel> int updateDBModel(Collection<T> collection,
                                                                 boolean saveChildren, String byUser) {
        int count = 0;
        if (!collection.isEmpty()) {
            AbsDbHelper<T> dbHelper = getDbHelper(collection.iterator().next(), byUser);
            if (dbHelper != null) {
                count = dbHelper.update(collection, saveChildren);
            }
        }
        return count;
    }

    /**
     * 更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 是否成功
     */
    public final boolean update(T t, boolean saveChildren) {
        int count = 0;
        String idName = getIdName();
        ContentValues values = createContentValues(t, saveChildren);
        try {
            synchronized (NOT_NULL) {
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().update(getTabName(), values, idName + "=? and byUser=?", new String[] { t.getId(), byUser });
                } else {
                    count = getWritableDatabase().update(getTabName(), values, idName + "=?", new String[] { t.getId() });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return update(t, saveChildren);
            }
        }
        return count > 0;
    }

    private boolean isNeedUpgrade(Exception e) {
        return !isCreatingOrUpdating && e.getMessage() != null && (e.getMessage().contains("no such") || e.getMessage().contains("has no"));
    }

    /**
     * 更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 是否成功
     */
    public final static boolean updateDBModel(AbsDBModel t, boolean saveChildren) {
        return updateDBModel(t, saveChildren, null);
    }

    /**
     * 更新一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @param byUser
     *            操作数据库的用户id
     * @return 是否成功
     */
    public final static <T extends AbsDBModel> boolean updateDBModel(T t, boolean saveChildren, String byUser) {
        boolean result = false;
        AbsDbHelper<T> dbHelper = getDbHelper(t, byUser);
        if (dbHelper != null) {
            result = dbHelper.update(t, saveChildren);
        }
        return result;
    }

    /**
     * 新增一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 如果是自增表({@link #needAutoincrement()}为true),返回生成的主键,否则范围rowId
     */
    public final long save(T t, boolean saveChildren) {
        ContentValues values = createContentValues(t, saveChildren);

        try {
            synchronized (NOT_NULL) {
                return getWritableDatabase().insertWithOnConflict(getTabName(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return save(t, saveChildren);
            }
            return -1;
        }
    }

    /**
     * 新增一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 如果是自增表({@link #needAutoincrement()}为true),返回生成的主键,否则范围rowId
     */
    public final static long saveDBModel(AbsDBModel t, boolean saveChildren) {
        return saveDBModel(t, saveChildren, null);
    }

    /**
     * 新增一条数据
     *
     * @param t
     * @param saveChildren
     *            是否新增或更新子对象
     * @param byUser
     *            操作数据库的用户id
     * @return 如果是自增表({@link #needAutoincrement()}为true),返回生成的主键,否则范围rowId
     */
    public final static <T extends AbsDBModel> long saveDBModel(T t, boolean saveChildren, String byUser) {
        long result = -1;
        AbsDbHelper<T> dbHelper = getDbHelper(t, byUser);
        if (dbHelper != null) {
            result = dbHelper.save(t, saveChildren);
        }
        return result;
    }

    /**
     * 根据数据对象获取数据库操作对象
     *
     * @param t
     */
    public static final AbsDbHelper<AbsDBModel> getDbHelper(AbsDBModel t) {
        return getDbHelper(t, null);
    }

    /**
     * 根据数据对象获取数据库操作对象
     *
     * @param t
     * @param byUser
     *            操作数据库的用户id
     */
    @SuppressWarnings("unchecked")
    public static final <T extends AbsDBModel> AbsDbHelper<T> getDbHelper(T t, String byUser) {
        AbsDbHelper<T> dbHelper = null;
        Class<AbsDbHelper<T>> clz = (Class<AbsDbHelper<T>>) allDBHelpers.get(t.getClass());
        if (clz != null) {
            if (byUser == null) {
                try {
                    return clz.getConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                return dbHelper = clz.getConstructor(String.class).newInstance(byUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dbHelper;
    }

    /**
     * 新增多条数据
     *
     * @param collection
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 新增成功的数据总数
     */
    public final int save(Collection<T> collection, boolean saveChildren) {
        int count = 0;
        for (T t : collection) {
            if (save(t, saveChildren) > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 新增或更新多条数据
     *
     * @param collection
     * @param saveChildren
     *            是否新增或更新子对象
     * @return 修改成功的数据总数
     */
    public final int saveOrUpdate(Collection<T> collection, boolean saveChildren) {
        int count = 0;
        for (T t : collection) {
            if (saveOrUpdate(t, saveChildren)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 修改表中所有条目的某个字段
     *
     * @param columnName
     *            字段名，必需与对应{@link AbsDBModel}中的成员变量名一致
     * @param newValue
     *            新值
     * @return 是否成功
     */
    @SuppressWarnings("unused")
    public final boolean update(String columnName, Object newValue) {
        ContentValues values = new ContentValues();

        if (newValue instanceof Boolean) {
            if (newValue != null) {
                values.put(columnName, (Boolean) newValue ? 1 : 2);
            } else {
                values.put(columnName, 0);
            }
        } else {
            values.put(columnName, String.valueOf(newValue));
        }

        int count = 0;
        try {
            synchronized (NOT_NULL) {
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().update(getTabName(), values, "byUser=?", new String[] { byUser });
                } else {
                    count = getWritableDatabase().update(getTabName(), values, null, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return update(columnName, newValue);
            }
        }
        return count > 0;
    }

    /**
     * 修改表中所有条目的某个值为指定值的字段
     *
     * @param columnName
     *            字段名，必需与对应{@link AbsDBModel}中的成员变量名一致
     * @param newValue
     *            新值
     * @return 是否成功
     */
    @SuppressWarnings("unused")
    public final boolean update(String columnName, Object oldValue, Object newValue) {
        ContentValues values = new ContentValues();

        if (newValue instanceof Boolean) {
            if (newValue != null) {
                values.put(columnName, (Boolean) newValue ? 1 : 2);
            } else {
                values.put(columnName, 0);
            }
        } else {
            values.put(columnName, String.valueOf(newValue));
        }

        if (oldValue instanceof Boolean) {
            if (oldValue != null) {
                oldValue = (Boolean) oldValue ? 1 : 2;
            } else {
                oldValue = 0;
            }
        }

        int count = 0;
        try {
            synchronized (NOT_NULL) {
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().update(getTabName(), values, "byUser=?" + " and " + columnName + "=?", new String[] { byUser, String.valueOf(oldValue) });
                } else {
                    count = getWritableDatabase().update(getTabName(), values, columnName + "=?", new String[] { String.valueOf(oldValue) });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return update(columnName, oldValue, newValue);
            }
        }
        return count > 0;
    }

    /**
     * 修改某条数据
     *
     * @param uid
     *            数据的主键
     *
     * @param map
     *            键值对，key必需与对应{@link AbsDBModel}中的字段名一致，value为修改后的数值
     * @return 是否成功
     */
    @SuppressWarnings("unused")
    public final boolean update(String uid, Map<String, Object> map) {
        ContentValues values = new ContentValues();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Boolean) {
                if (value != null) {
                    values.put(key, (Boolean) value ? 1 : 2);
                } else {
                    values.put(key, 0);
                }
            } else {
                values.put(key, String.valueOf(value));
            }
        }

        int count = 0;
        try {
            synchronized (NOT_NULL) {
                String idName = getIdName();
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().update(getTabName(), values, idName + "=? and byUser=?", new String[] { uid, byUser });
                } else {
                    count = getWritableDatabase().update(getTabName(), values, idName + "=?", new String[] { uid });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return update(uid, map);
            }
        }
        return count > 0;
    }

    /**
     * 表中指定字段值为特定数个值其中之一时，修改这些数据
     *
     * @param columnName
     *            字段名，必需与对应{@link AbsDBModel}中的成员变量名一致 数据的主键
     * @param inSet
     *            指定值的集合
     * @param map
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为修改后的数值
     * @return 是否成功
     */
    @SuppressWarnings("unused")
    public final boolean updateIn(String columnName, Collection<Object> inSet, Map<String, Object> map) {
        ContentValues values = new ContentValues();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Boolean) {
                if (value != null) {
                    values.put(key, (Boolean) value ? 1 : 2);
                } else {
                    values.put(key, 0);
                }
            } else {
                values.put(key, String.valueOf(value));
            }
        }

        StringBuilder builder = new StringBuilder();
        if (inSet != null && !inSet.isEmpty()) {
            for (Object object : inSet) {
                builder.append("'");
                if (object instanceof Boolean) {
                    if (object != null) {
                        builder.append((Boolean) object ? 1 : 2);
                    } else {
                        builder.append(0);
                    }
                } else if (object instanceof AbsDBModel) {
                    builder.append(((AbsDBModel) object).getId());
                } else {
                    builder.append(object.toString());
                }
                builder.append("'");
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }

        int count = 0;
        try {
            synchronized (NOT_NULL) {
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().update(getTabName(), values, "byUser=? and " + columnName + " in (" + builder.toString() + ")", new String[] { byUser });
                } else {
                    count = getWritableDatabase().update(getTabName(), values, new String(columnName + " in (" + builder.toString() + ")"), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return updateIn(columnName, inSet, map);
            }
        }
        return count > 0;
    }

    /**
     * 删除一条数据
     *
     * @param t
     * @return 是否成功
     */
    public final boolean delete(T t) {
        int count = 0;
        try {
            synchronized (NOT_NULL) {
                String idName = getIdName();
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().delete(getTabName(), idName + "=? and byUser=?", new String[] { t.getId(), byUser });
                } else {
                    count = getWritableDatabase().delete(getTabName(), idName + "=?", new String[] { t.getId() });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return delete(t);
            }
        }
        return count > 0;
    }

    /**
     * 删除一条数据
     *
     * @param t
     * @return 是否成功
     */
    public final static boolean deleteDBModel(AbsDBModel t) {
        return deleteDBModel(t, null);
    }

    /**
     * 删除一条数据
     *
     * @param t
     * @param byUser
     *            操作数据库的用户id
     * @return 是否成功
     */
    public final static <T extends AbsDBModel> boolean deleteDBModel(T t, String byUser) {
        boolean result = false;
        AbsDbHelper<T> dbHelper = getDbHelper(t, byUser);
        if (dbHelper != null) {
            result = dbHelper.delete(t);
        }
        return result;
    }

    /**
     * 删除多条特定键值相符的数据
     *
     * @param equal
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为指定的数值
     * @return
     */
    @SuppressWarnings("unused")
    public final int delete(Map<String, Object> equal) {
        if (equal == null || equal.isEmpty()) {
            return 0;
        }

        if (byUser != null && !byUser.isEmpty()) {
            equal.put("byUser", byUser);
        }

        StringBuilder selection = new StringBuilder();
        ArrayList<String> selectionList = new ArrayList<String>();

        Iterator<Map.Entry<String, Object>> iter = equal.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();

            selection.append(entry.getKey());

            Object value = entry.getValue();

            if (value.equals(NOT_NULL)) {
                selection.append("!=?");
            } else {
                selection.append("=?");
            }

            if (iter.hasNext()) {
                selection.append(" and ");
            }

            if (value.equals(NOT_NULL)) {
                selectionList.add("null");
            } else if (value instanceof Boolean) {
                if (value != null) {
                    selectionList.add((Boolean) value ? "1" : "2");
                } else {
                    selectionList.add("0");
                }
            } else {
                selectionList.add(String.valueOf(value));
            }
        }

        String[] selectionArgs = null;
        if (selectionList.size() > 0) {
            selectionArgs = new String[selectionList.size()];
            selectionArgs = (String[]) selectionList.toArray(selectionArgs);
        }

        int count = 0;
        try {
            synchronized (NOT_NULL) {
                count = getWritableDatabase().delete(getTabName(), selection.toString(), selectionArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return delete(equal);
            }
        }
        return count;
    }

    /**
     * 删除本表中所有数据
     *
     * @return 删除的数据总条数
     */
    public final int deleteAll() {
        int count = 0;
        try {
            synchronized (NOT_NULL) {
                if (byUser != null && !byUser.isEmpty()) {
                    count = getWritableDatabase().delete(getTabName(), "byUser=?", new String[] { byUser });
                } else {
                    count = getWritableDatabase().delete(getTabName(), null, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return deleteAll();
            }
        }
        return count;
    }

    /**
     * 根据条件查询
     *
     * @param equal
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为必须相符的数值
     * @param or
     *            键值对，key必需与对应{@link AbsDBModel}
     *            中的成员变量名一致，value为必须相符的数值;本集合中所有条件有一个相符即可
     * @param like
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为使用模糊查询的数值
     * @param orderBy
     *            排序方式，必需与对应{@link AbsDBModel}中的成员变量名一致
     * @param start
     *            跳过数据总量
     * @param count
     *            返回数据总量，0为最大数量
     * @return 搜索结果的对象list
     */
    public final List<T> query(Map<String, Object> equal, Map<String, Object> or, Map<String, String> like, String orderBy, int start, int count) {
        return handleQuery(getQueryCursor(equal, or, like, orderBy, start, count));
    }

    @SuppressWarnings("unused")
    private Cursor getQueryCursor(Map<String, Object> equal, Map<String, Object> or, Map<String, String> like, String orderBy, int start, int count) {
        if (!TextUtils.isEmpty(byUser)) {
            if (equal == null) {
                equal = new HashMap<>();
            }
            equal.put("byUser", byUser);
        }

        if (orderBy == null) {
            orderBy = getDefaultOrderBy();
        }

        StringBuilder selection = new StringBuilder();
        ArrayList<String> selectionList = new ArrayList<String>();

        if (equal != null && !equal.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iter = equal.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();

                String key = entry.getKey().trim();
                Object value = entry.getValue();

                selection.append(key);

                if (value.equals(NOT_NULL)) {
                    selection.append("!=?");
                } else if (key.endsWith("<") || key.endsWith(">") || key.endsWith("=")) {
                    selection.append("?");
                } else {
                    selection.append("=?");
                }

                if (iter.hasNext()) {
                    selection.append(" and ");
                }

                if (value.equals(NOT_NULL)) {
                    selectionList.add("null");
                } else if (value instanceof Boolean) {
                    if (value != null) {
                        selectionList.add((Boolean) value ? "1" : "2");
                    } else {
                        selectionList.add("0");
                    }
                } else {
                    selectionList.add(String.valueOf(value));
                }
            }
        }

        if (or != null && !or.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iter = or.entrySet().iterator();
            selection.append(" and (");

            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();

                String key = entry.getKey().trim();
                Object value = entry.getValue();

                selection.append(key);

                if (value.equals(NOT_NULL)) {
                    selection.append("!=?");
                } else if (key.endsWith("<") || key.endsWith(">") || key.endsWith("=")) {
                    selection.append("?");
                } else {
                    selection.append("=?");
                }

                if (iter.hasNext()) {
                    selection.append(" or ");
                }

                if (value.equals(NOT_NULL)) {
                    selectionList.add("null");
                } else if (value instanceof Boolean) {
                    if (value != null) {
                        selectionList.add((Boolean) value ? "1" : "2");
                    } else {
                        selectionList.add("0");
                    }
                } else {
                    selectionList.add(String.valueOf(value));
                }
            }
            selection.append(")");
        }

        if (like != null && !like.isEmpty()) {
            Iterator<Map.Entry<String, String>> iter = like.entrySet().iterator();
            selection.append(" and ");

            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();

                selection.append(entry.getKey());
                selection.append(" like ? ");

                if (iter.hasNext()) {
                    selection.append(" and ");
                }

                selectionList.add("%" + entry.getValue() + "%");
            }
        }

        String[] selectionArgs = null;
        if (selectionList.size() > 0) {
            selectionArgs = new String[selectionList.size()];
            selectionArgs = (String[]) selectionList.toArray(selectionArgs);
        }

        Cursor cursor = null;
        try {
            synchronized (NOT_NULL) {
                if (count > 0) {
                    cursor = getWritableDatabase().query(getTabName(), null, selection.toString(), selectionArgs, getDefaultGroupBy(), null, orderBy, start + "," + count);
                } else {
                    cursor = getWritableDatabase().query(getTabName(), null, selection.toString(), selectionArgs, getDefaultGroupBy(), null, orderBy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            if (isNeedUpgrade(e)) {
                upgrade();
                return getQueryCursor(equal, or, like, orderBy, start, count);
            }
            return null;
        }
        return cursor;
    }

    /**
     * 默认分组查询的字段名,必需与对应{@link AbsDBModel}中的成员变量名一致
     *
     * @return
     */
    protected String getDefaultGroupBy() {
        return null;
    }

    public final T queryOne(String uid) {
        Cursor cursor = null;
        try {
            synchronized (NOT_NULL) {
                if (!TextUtils.isEmpty(byUser)) {
                    cursor = getWritableDatabase().query(getTabName(), null, getIdName() + "=? and byUser=?", new String[] { uid, byUser }, null, null, null, 0 + "," + 1);
                } else {
                    cursor = getWritableDatabase().query(getTabName(), null, getIdName() + "=?", new String[] { uid }, null, null, null, 0 + "," + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            if (isNeedUpgrade(e)) {
                upgrade();
                return queryOne(uid);
            }
            return null;
        }

        List<T> list = handleQuery(cursor);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public final T queryOne(Map<String, Object> equal, String orderBy) {
        List<T> list = query(equal, null, null, orderBy, 0, 1);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public final T queryOne(Map<String, Object> equal) {
        List<T> list = query(equal, 0, 1);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 模糊查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> search(Map<String, Object> equal, Map<String, Object> or, Map<String, String> filter) {
        return query(equal, or, filter, null, 0, 0);
    }

    /**
     * 模糊查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> search(Map<String, Object> equal, Map<String, String> filter) {
        return query(equal, null, filter, null, 0, 0);
    }

    /**
     * 模糊查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> search(Map<String, String> filter) {
        return query(null, null, filter, null, 0, 0);
    }

    /**
     * 模糊查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> search(Map<String, String> filter, int start, int count) {
        return query(null, null, filter, null, start, count);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> query(Map<String, Object> equal, Map<String, Object> or) {
        return query(equal, or, null, null, 0, 0);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> query(Map<String, Object> equal) {
        return query(equal, null, null, null, 0, 0);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> query(int start, int count) {
        return query(null, null, null, null, start, count);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> query(Map<String, Object> equal, int start, int count) {
        return query(equal, null, null, null, start, count);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final List<T> query() {
        return query(null, null, null, null, 0, 0);
    }

    /**
     * 查询总数，相应字段含义参考{@link #count(Map, Map, Map, String, int, int)}
     */
    public final int count(Map<String, Object> equal, Map<String, Object> or, Map<String, String> filter) {
        return count(equal, or, filter, null, 0, 0);
    }

    /**
     * 查询总数，相应字段含义参考{@link #count(Map, Map, Map, String, int, int)}
     */
    public final int count(Map<String, Object> equal, Map<String, Object> or) {
        return count(equal, or, null, null, 0, 0);
    }

    /**
     * 查询总数，相应字段含义参考{@link #count(Map, Map, Map, String, int, int)}
     */
    public final int count(Map<String, Object> equal) {
        return count(equal, null, null, null, 0, 0);
    }

    /**
     * 精确查询，相应字段含义参考{@link #query(Map, Map, Map, String, int, int)}
     */
    public final int count(String uid) {
        Cursor cursor = null;
        try {
            synchronized (NOT_NULL) {
                if (!TextUtils.isEmpty(byUser)) {
                    cursor = getWritableDatabase().query(getTabName(), null, getIdName() + "=? and byUser=?", new String[] { uid, byUser }, null, null, null, 0 + "," + 1);
                } else {
                    cursor = getWritableDatabase().query(getTabName(), null, getIdName() + "=?", new String[] { uid }, null, null, null, 0 + "," + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isNeedUpgrade(e)) {
                upgrade();
                return count(uid);
            }
        }
        int result = 0;
        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }

    /**
     * 获取总数，相应字段含义参考{@link #count(Map, Map, Map, String, int, int)}
     */
    public final int count() {
        return count(null, null, null, null, 0, 0);
    }

    /**
     * 根据条件查询总数
     *
     * @param equal
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为必须相符的数值
     * @param or
     *            键值对，key必需与对应{@link AbsDBModel}
     *            中的成员变量名一致，value为必须相符的数值;本集合中所有条件有一个相符即可
     * @param like
     *            键值对，key必需与对应{@link AbsDBModel}中的成员变量名一致，value为使用模糊查询的数值
     * @param orderBy
     *            排序方式，必需与对应{@link AbsDBModel}中的成员变量名一致
     * @param start
     *            跳过数据总量
     * @param count
     *            返回数据总量，0为最大数量
     * @return 搜索结果的对象list
     */
    public final int count(Map<String, Object> equal, Map<String, Object> or, Map<String, String> like, String orderBy, int start, int count) {
        Cursor cursor = getQueryCursor(equal, or, like, orderBy, start, count);
        int result = 0;
        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private ContentValues createContentValues(T t, boolean saveChildren) {
        ContentValues values = new ContentValues();

        for (Field field : getEntityClass().getFields()) {
            if(!field.isAnnotationPresent(NotDBValue.class)&&!Modifier.isStatic(field.getModifiers())){
                try {
                    Object object = field.get(t);

                    if (object != null) {
                        if (object instanceof String) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof Integer) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof Long) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof Float) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof Double) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof Date) {
                            values.put(field.getName(), ((Date) object).getTime());
                        } else if (object instanceof Boolean) {
                            values.put(field.getName(), (Boolean) object ? 1 : 2);
                        } else if (object instanceof Enum<?>) {
                            values.put(field.getName(), object.toString());
                        } else if (object instanceof AbsDBModel) {
                            AbsDBModel obj = (AbsDBModel) object;
                            Class<? extends AbsDbHelper<?>> dbClz = allDBHelpers.get(object.getClass());

                            AbsDbHelper<AbsDBModel> dbhelper = (AbsDbHelper<AbsDBModel>) dbClz.getConstructor(String.class).newInstance(byUser);
                            if (saveChildren) {
                                if (obj.getId() == null || obj.getId().equals("null")) {
                                    boolean needAutoincrement = dbhelper.needAutoincrement();
                                    if (!needAutoincrement) {
                                        obj.setId(UUID.randomUUID().toString());
                                        dbhelper.save(obj, saveChildren);
                                    } else {
                                        obj.setId(String.valueOf(dbhelper.save(obj, saveChildren)));
                                    }
                                } else {
                                    dbhelper.saveOrUpdate(obj, saveChildren);
                                }
                            } else {
                                if (obj.getId() != null && dbhelper.queryOne(obj.getId()) == null) {
                                    dbhelper.saveOrUpdate(obj, saveChildren);
                                }
                            }
                            values.put(field.getName(), obj.getId());
                        } else if (field.getType().isArray()) {
                            Object[] array = (Object[]) object;

                            StringBuilder builder = new StringBuilder();
                            for (Object temp : array) {
                                builder.append(temp.toString());
                                builder.append(",");
                            }

                            if (builder.length() > 0) {
                                builder.deleteCharAt(builder.lastIndexOf(","));
                            }

                            values.put(field.getName(), builder.toString());
                        } else if (object instanceof List) {
                            StringBuilder builder = new StringBuilder();

                            ParameterizedType pt = (ParameterizedType) field.getGenericType();
                            Class<?> componentType = (Class<?>) pt.getActualTypeArguments()[0];

                            if (AbsDBModel.class.isAssignableFrom(componentType)) {
                                Class<? extends AbsDbHelper<?>> dbClz = allDBHelpers.get(componentType);

                                List<AbsDBModel> array = (List<AbsDBModel>) object;

                                AbsDbHelper<AbsDBModel> dbhelper = (AbsDbHelper<AbsDBModel>) dbClz.getConstructor(String.class).newInstance(byUser);
                                for (AbsDBModel obj : array) {
                                    if (saveChildren) {
                                        if (TextUtils.isEmpty(obj.getId())) {
                                            boolean needAutoincrement = dbhelper.needAutoincrement();
                                            if (!needAutoincrement) {
                                                obj.setId(UUID.randomUUID().toString());
                                                dbhelper.save(obj, saveChildren);
                                            } else {
                                                obj.setId(String.valueOf(dbhelper.save(obj, saveChildren)));
                                            }
                                        } else {
                                            dbhelper.saveOrUpdate(obj, saveChildren);
                                        }
                                    } else if (obj.getId() != null && dbhelper.queryOne(obj.getId()) == null) {
                                        dbhelper.save(obj, saveChildren);
                                    }

                                    builder.append(obj.getId());
                                    builder.append(",");
                                }
                            } else {
                                for (Object model : (List<?>) object) {
                                    builder.append(model.toString());
                                    builder.append(",");
                                }
                            }

                            if (builder.length() > 0) {
                                builder.deleteCharAt(builder.lastIndexOf(","));
                            }

                            values.put(field.getName(), builder.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (byUser != null) {
            values.put("byUser", byUser);
        }
        return values;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<T> handleQuery(Cursor cursor) {
        List<T> list = new ArrayList<T>();
        if (cursor != null) {
            try {
                cursor.moveToFirst();
            } catch (Exception e) {
                e.printStackTrace();
                cursor.close();
                SQLiteDatabase.releaseMemory();
                return list;
            }
            while (!cursor.isAfterLast()) {
                Class<T> entityClass = getEntityClass();

                T t;
                try {
                    t = entityClass.getConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    cursor.close();
                    return list;
                }

                for (Field field : entityClass.getFields()) {
                    if(!field.isAnnotationPresent(NotDBValue.class)&&!Modifier.isStatic(field.getModifiers())){
                        try {
                            Class<?> clz = field.getType();

                            if (String.class.isAssignableFrom(clz)) {
                                field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
                            } else if (clz.isEnum()) {
                                String temp = cursor.getString(cursor.getColumnIndex(field.getName()));
                                if (temp != null) {
                                    field.set(t, Enum.valueOf((Class<Enum>) clz, temp));
                                }
                            } else if (clz == boolean.class || clz == Boolean.class) {
                                switch (cursor.getInt(cursor.getColumnIndex(field.getName()))) {
                                    case 1:
                                        field.set(t, true);
                                        break;
                                    case 2:
                                        field.set(t, false);
                                        break;
                                }
                            } else if (clz == int.class || clz == Integer.class) {
                                field.set(t, cursor.getInt(cursor.getColumnIndex(field.getName())));
                            } else if (clz == long.class || clz == Long.class) {
                                field.set(t, cursor.getLong(cursor.getColumnIndex(field.getName())));
                            } else if (clz == float.class || clz == Float.class) {
                                field.set(t, cursor.getFloat(cursor.getColumnIndex(field.getName())));
                            } else if (clz == double.class || clz == Double.class) {
                                field.set(t, cursor.getDouble(cursor.getColumnIndex(field.getName())));
                            } else if (Date.class.isAssignableFrom(clz)) {
                                long temp = cursor.getLong(cursor.getColumnIndex(field.getName()));
                                if (temp > 0) {
                                    field.set(t, new Date(temp));
                                }
                            } else if (AbsDBModel.class.isAssignableFrom(clz)) {
                                String uid = cursor.getString(cursor.getColumnIndex(field.getName()));
                                if (uid != null) {
                                    AbsDbHelper<?> dbhelper = allDBHelpers.get(clz).getConstructor(String.class).newInstance(byUser);
                                    field.set(t, dbhelper.queryOne(uid));
                                }
                            } else if (clz.isArray()) {
                                String temp = cursor.getString(cursor.getColumnIndex(field.getName()));
                                if (temp != null) {
                                    String[] array = temp.split(",");
                                    Class<?> componentType = clz.getComponentType();

                                    if (String.class.isAssignableFrom(componentType)) {
                                        field.set(t, array);
                                    } else {
                                        // for (String temp : array) {
                                        //
                                        // }
                                    }
                                }
                            } else if (List.class.isAssignableFrom(clz)) {
                                String temp = cursor.getString(cursor.getColumnIndex(field.getName()));
                                if (!TextUtils.isEmpty(temp)) {
                                    String[] array = temp.split(",");

                                    List result = (List) clz.newInstance();

                                    ParameterizedType pt = (ParameterizedType) field.getGenericType();
                                    Class<?> componentType = (Class<?>) pt.getActualTypeArguments()[0];

                                    if (AbsDBModel.class.isAssignableFrom(componentType)) {
                                        AbsDbHelper<?> dbhelper = allDBHelpers.get(componentType).getConstructor(String.class).newInstance(byUser);

                                        for (String s : array) {
                                            AbsDBModel model = dbhelper.queryOne(s);
                                            if (model != null) {
                                                result.add(model);
                                            }
                                        }
                                    } else if (componentType.isPrimitive()) {
                                        Constructor<?> constructor = componentType.getConstructor(String.class);
                                        for (String s : array) {
                                            result.add(constructor.newInstance(s));
                                        }
                                    }

                                    if (!result.isEmpty()) {
                                        field.set(t, result);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.add(t);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 清空整个数据库的所有表，慎用
     */
    public final static void clearAllDB() {
        clearAllDB(null, false);
    }

    /**
     * 清空整个数据库的所有表，慎用
     *
     * @param byUser
     *            用户id，如为空则清空所有数据，不为空则清空相应用户id下的数据
     */
    public final static void clearAllDB(String byUser) {
        clearAllDB(byUser, false);
    }

    /**
     * 清空整个数据库的所有表，慎用
     *
     * @param deleteAll
     *            如果为true,无视{@link AbsDbHelper#canClear()},全部清空
     */
    public final static void clearAllDB(boolean deleteAll) {
        clearAllDB(null, deleteAll);
    }

    /**
     * 清空整个数据库的所有表，慎用
     *
     * @param byUser
     *            用户id，如为空则清空所有数据，不为空则清空相应用户id下的数据
     * @param deleteAll
     *            如果为true,无视{@link AbsDbHelper#canClear()},全部清空
     */
    public final static void clearAllDB(String byUser, boolean deleteAll) {
        for (Map.Entry<Class<? extends AbsDBModel>, Class<? extends AbsDbHelper<?>>> entry : allDBHelpers.entrySet()) {
            try {
                AbsDbHelper<?> dbhelper;
                if (byUser != null) {
                    try {
                        dbhelper = entry.getValue().getConstructor(String.class).newInstance(byUser);
                    } catch (Exception e) {
                        dbhelper = entry.getValue().getConstructor().newInstance();
                    }
                } else {
                    dbhelper = entry.getValue().getConstructor().newInstance();
                }
                if (deleteAll || dbhelper.canClear()) {
                    dbhelper.deleteAll();
                }
                dbhelper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
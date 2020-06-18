package com.mao.library.http;

import com.mao.library.interfaces.NotRequestValue;
import com.mao.library.interfaces.OnFileUploadProgressListener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by maozonghong
 * on 2020/6/18
 */
public abstract class AbsRequest {
    @NotRequestValue
    public OnFileUploadProgressListener listener;

    public AbsRequest(){

    }

    public Map<String,String> getBody(){
        init();
        Map<String,String> params=new HashMap<>();
        addParams(params, getClass().getFields());
        return params;
    }

    protected void init() {

    }

    private void addParams(Map<String,String> map, Field[] fields) {
        for(Field field:fields){
            if(!Modifier.isStatic(field.getModifiers())&&!field.isAnnotationPresent(NotRequestValue.class)){
                Object object=null;
                try {
                    field.setAccessible(true);
                    object=field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(object!=null){
                    String key=field.getName();
                    String value;
                    if(object instanceof Boolean){
                        value= (Boolean)object?"1":"0";
                    }else if(object instanceof File){
                        onGetFileFiled(map,key,(File)object);
                        continue;
                    }else{
                        value=object.toString();
                    }
                    map.put(key,value);
                }
            }
        }
    }

    protected void onGetFileFiled(Map<String,String> params, String key, File object) {
    }

    public MultipartBody getMultipartEntity() {
        return getMultipartEntity(null);
    }

    public MultipartBody getMultipartEntity(Map<String, Object> extras) {

        if (extras == null) {
            extras = new HashMap<>();
        }
        for (Field field : getClass().getFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(NotRequestValue.class)) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(this);

                    if (obj instanceof OnFileUploadProgressListener) {
                        continue;
                    }
                    putBody(field.getName(), obj, extras);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        MultipartBody.Builder builder=new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : extras.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String key = entry.getKey();

            if (value instanceof File) {
                if(listener!=null){
                    FileProgressRequestBody progressRequestBody=new FileProgressRequestBody((File)value,
                            "multipart/form-data",listener);
                    builder.addFormDataPart(key,((File) value).getName(),progressRequestBody);
                }else{
                    builder.addFormDataPart(key,((File) value).getName(),
                            RequestBody.create(MultipartBody.FORM, (File)value));
                }
            } else {
                builder.addFormDataPart(key,value.toString());
            }
        }

        return builder.build();
    }

    protected void putBody(Object name, Object obj, Map<String, Object> extras) throws Throwable {
        if (obj != null) {
            if (obj instanceof File) {
                File file = (File) obj;
                if (file.exists()){
                    extras.put(name.toString(), file);
                }
            } else if (obj instanceof Map){
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                    putBody(entry.getKey(), entry.getValue(), extras);
                }
            } else {
                String key = name.toString();
                String value;
                if (obj instanceof Boolean) {
                    value = (Boolean) obj ? "1" : "0";
                } else {
                    value = obj.toString();
                }
                extras.put(key, value);
            }
        }
    }
}

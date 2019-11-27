package com.mao.library.abs;

import org.json.JSONObject;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
public abstract class AbsDBModel extends AbsModel {
    public String byUser;

    public AbsDBModel(){
    }

    public abstract String getId();

    public abstract void setId(String str);

    public AbsDBModel(JSONObject json) {
        super(json);
    }
}

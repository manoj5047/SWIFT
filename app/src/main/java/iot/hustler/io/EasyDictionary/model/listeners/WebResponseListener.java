package iot.hustler.io.EasyDictionary.model.listeners;

import iot.hustler.io.EasyDictionary.model.pojo.RootObject;

public interface WebResponseListener {

    public void onSuccess(RootObject object);

    public void onError(String errormessage);
}

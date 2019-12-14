package iot.hustler.io.easydictionary.model.listeners;

import iot.hustler.io.easydictionary.model.pojo.ResponseWordsDefinitionsDTO;

public interface WebResponseListener {

    public void onSuccess(ResponseWordsDefinitionsDTO object);

    public void onError(String errormessage);
}

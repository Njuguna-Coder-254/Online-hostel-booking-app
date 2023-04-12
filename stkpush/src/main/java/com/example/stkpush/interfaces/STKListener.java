package com.example.stkpush.interfaces;

import com.example.stkpush.api.response.STKPushResponse;

public interface STKListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}

package com.example.stkpush.interfaces;

import com.example.stkpush.api.response.STKPushResponse;

public interface STKQueryListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}

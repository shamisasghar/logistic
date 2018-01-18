package com.logistics.hypernym.logistic.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.logistics.hypernym.logistic.FrameActivity;
import com.logistics.hypernym.logistic.fragments.JobDetailFragment;
import com.logistics.hypernym.logistic.fragments.JobNoticationFragment;
import com.logistics.hypernym.logistic.fragments.khali;
import com.logistics.hypernym.logistic.models.PayloadNotification;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.AppUtils;
import com.logistics.hypernym.logistic.utils.Constants;
import com.logistics.hypernym.logistic.utils.GsonUtils;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Metis on 11-Jan-18.
 */

public class OneSignalReceiver extends NotificationExtenderService {
    PayloadNotification payloadNotification;

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {


        // Read properties from result.
        JSONObject additionalData = receivedResult.payload.additionalData;
        Log.e("test", additionalData.toString());
        if (additionalData != null) {
            if (receivedResult.isAppInFocus) {
                payloadNotification = new PayloadNotification();
                try {
                    payloadNotification.title = additionalData.getString("title");
                    payloadNotification.job_id = additionalData.getInt("id");
                    payloadNotification.message = additionalData.getString("message");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PAYLOAD, GsonUtils.toJson(payloadNotification));
                   // AppUtils.makeNotification(this, JobDetailActivity.class, null, bundle, payloadNotification.title, false, payloadNotification.job_id);
                    //ActivityUtils.startActivity(this, FrameActivity.class,JobDetailFragment.class.getName(), bundle);
                    AppUtils.makeNotification(this, FrameActivity.class, JobNoticationFragment.class.getName(), bundle, payloadNotification.title, false, payloadNotification.job_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                {
                try
                {
                    payloadNotification = new PayloadNotification();
                    payloadNotification.title = additionalData.getString("title");
                    payloadNotification.job_id = Integer.parseInt(additionalData.getString("id"));
                    payloadNotification.message = additionalData.getString("message");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PAYLOAD, GsonUtils.toJson(payloadNotification));
                    AppUtils.makeNotification(this, FrameActivity.class, JobNoticationFragment.class.getName(), bundle, payloadNotification.title, false, payloadNotification.job_id);

//                    if (orderStatus.status == OrderPlacedEnum.READY_FOR_PAYMENT.getValue()) {
//                        if (!AppUtils.isRunningInForeground(this)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.READY_FOR_PAYMENT, GsonUtils.toJson(orderStatus));
//                            AppUtils.makeNotification(this, FrameActivity.class, PayFragment.class.getName(), bundle, orderStatus.message, false, orderStatus.order_id);
//                        } else {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.READY_FOR_PAYMENT, GsonUtils.toJson(orderStatus));
//                            ActivityUtils.startActivity(this, FrameActivity.class, PayFragment.class.getName(), bundle);
//                        }
//                    } else if (orderStatus.status == OrderPlacedEnum.NEED_TO_BE_CHANGED.getValue()) {
//                        if (!AppUtils.isRunningInForeground(this)) {
//                            orderStatus.lines_deleted = additionalData.getInt("lines_deleted");
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.NEED_TO_BE_CHANGED, GsonUtils.toJson(orderStatus));
//                            AppUtils.makeNotification(this, FrameActivity.class, MyCartFragment.class.getName(), bundle, orderStatus.message, true, orderStatus.order_id);
//                        } else {
//                            orderStatus.lines_deleted = additionalData.getInt("lines_deleted");
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.NEED_TO_BE_CHANGED, GsonUtils.toJson(orderStatus));
//                            ActivityUtils.startActivity(this, FrameActivity.class, MyCartFragment.class.getName(), bundle, true);
//                        }
//                    } else if (orderStatus.status == OrderPlacedEnum.PAYMENT_DONE.getValue()) {
//                        if (!AppUtils.isRunningInForeground(this)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.PAYMENT_DONE, GsonUtils.toJson(orderStatus));
//                        } else {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.PAYMENT_DONE, GsonUtils.toJson(orderStatus));
//                            ActivityUtils.startActivity(this, FrameActivity.class, PaymentDoneFragment.class.getName(), bundle, true);
//                        }
//                    } else if (orderStatus.status == OrderPlacedEnum.CANCELLED.getValue()) {
//                        if (!AppUtils.isRunningInForeground(this)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.CANCELED, GsonUtils.toJson(orderStatus));
//                            AppUtils.makeNotification(this, FrameActivity.class, MyCartFragment.class.getName(), bundle, orderStatus.message, true, orderStatus.order_id);
//                        } else {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.CANCELED, GsonUtils.toJson(orderStatus));
//                            ActivityUtils.startActivity(this, FrameActivity.class, MyCartFragment.class.getName(), bundle, true);
//                        }
//                    } else if (orderStatus.status == OrderPlacedEnum.PAYMENT_UNSUCCESSFUL.getValue()) {
//                        if (!AppUtils.isRunningInForeground(this)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.PAYMENT_UNSUCCESSFUL, GsonUtils.toJson(orderStatus));
//                            AppUtils.makeNotification(this, FrameActivity.class, PaymentDoneFragment.class.getName(), bundle, orderStatus.message, false, orderStatus.order_id);
//                        } else {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(Constants.PAYMENT_UNSUCCESSFUL, GsonUtils.toJson(orderStatus));
//                            ActivityUtils.startActivity(this, FrameActivity.class, PaymentDoneFragment.class.getName(), bundle, true);
//                        }
                    // } else
//                    if (orderStatus.status == OrderPlacedEnum.READY_TO_BE_SERVED.getValue() ||
//                            orderStatus.status == OrderPlacedEnum.SERVED.getValue()) {
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(Constants.ORDER_ID, orderStatus.order_id);
//                        AppUtils.makeNotification(MyApplication.getAppContext(), FrameActivity.class, OrderTrackingFragment.class.getName(), bundle, orderStatus.message, true, orderStatus.order_id);
////                        AppUtils.makeNotification(this, HomeActivity.class, CategoriesFragment.class.getName(), null, orderStatus.message, true, new Random().nextInt(1000 - 1 + 1) + 1);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // Return true to stop the notification from displaying.
        return true;
    }
}

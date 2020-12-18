package com.ruanjie.donkey.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.CouponsDialogAdapter;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.toolsdk.ui.dialog.SureDialog;
import com.softgarden.baselibrary.dialog.EditDialog;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.List;


/**
 * Created by Administrator on 2017/4/1.
 */
public class DiaLogUtils {
//    方法	说明
//    newDialog(Context context)	创建dialog
//    setContentHolder(Holder holder)	设置holder,必要
//    setContentWidth(int width)	宽：ViewGroup.LayoutParams.WRAP_CONTENT等
//    setContentHeight(int height)	高
//    setHeader(int resourceId)	头的布局或View
//    setFooter(int resourceId)	尾的布局或View
//    setGravity(int gravity)	dialog的位置
//    setExpanded(boolean expanded)	是否可扩展，默认是false,仅适用于ListView和GridView
//    setCancelable(boolean isCancelable)	点击外部区域是否可以取消dialog
//    setAdapter(BaseAdapter adapter)	ListView或GridView的adapter,ViewHolder不需要
//    setOnItemClickListener(OnItemClickListener listener)	ListView或GridView的item的点击事件
//    setOnClickListener(OnClickListener listener)	点击事件
//    setOnDismissListener(OnDismissListener listener)	dismiss的监听
//    setOnCancelListener(OnCancelListener listener)	取消的监听
//    getHolderView()	获取视图View
//    getHeaderView()	获取头布局
//    getFooterView()	获取尾布局
//    setMargin(left, top, right, bottom)	Add margins to your dialog. They are set to 0 except when gravity is center. In that case basic margins are applied
//    setPadding(left, top, right, bottom)	Set padding to the holder
//    setInAnimation(R.anim.abc_fade_in)	进入动画
//    setOutAnimation(R.anim.abc_fade_out)	移除动画
//    setContentBackgroundResource(resource)	dialog的背景色
//    setOverlayBackgroundResource(resource)	dialog以外的背景色

    /**
     * 要想setContentBackgroundResource生效，则item不能设置background属性
     */

    public static PromptDialog showTipDialog(Context context, String title, String content
            , String leftStr, String rightStr, PromptDialog.OnButtonClickListener listener) {

        PromptDialog promptDia = new PromptDialog(context);
        promptDia.setTitle(title);
        promptDia.setContent(content);
        promptDia.setNegativeButton(leftStr, R.color.titleColor);
        promptDia.setPositiveButton(rightStr, R.color.text_yellow);
        promptDia.setCanceledOnTouchOutside(false);
        promptDia.setOnButtonClickListener(listener);
        promptDia.show();
        return promptDia;
    }
    public static SureDialog showSureDialog(Context context, String title, String content,
                                            String sure, SureDialog.OnSureClickListener listener) {
        final SureDialog sureDialog = new SureDialog(context);
        sureDialog.setTitle(title);
        sureDialog.setContent(content);
        sureDialog.setSure(sure);
        sureDialog.setCanceledOnTouchOutside(false);
        sureDialog.setOnSureListener(listener);
        sureDialog.show();
        return sureDialog;
    }

    public static DialogPlus showCouponsDialog(Activity act, List<CouponBean> data, OnDialogCouponSelectListener listener) {
        final DialogPlus dialog = DialogPlus.newDialog(act)
                //要想setContentBackgroundResource生效，则item不能设置background属性
                .setContentBackgroundResource(R.drawable.shape_coupon)
//                .setOverlayBackgroundResource(ContextUtil.getColor(R.color.transparent))//外部背景色
                .setContentHolder(new ViewHolder(R.layout.dialog_coupons))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        dialogPlus.dismiss();
                    }
                })
                .create();
        View view = dialog.getHolderView();
        RecyclerView mRecyclerView = view.findViewById(R.id.mRecyclerView);
        TextView tvOk = view.findViewById(R.id.tvOk);

        CouponsDialogAdapter mAdapter = new CouponsDialogAdapter(act);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int i = mAdapter.getmIsSelectPosi();
                if (i == position) {
                    mAdapter.setmIsSelectPosi(-1);
                } else {
                    mAdapter.setmIsSelectPosi(position);
                }
                mAdapter.notifyDataSetChanged();

            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int i = mAdapter.getmIsSelectPosi();
                    if (i == -1) {
                        listener.onOkClick("0.00", 0);
                    } else {
                        listener.onOkClick(mAdapter.getData().get(i).getMoney(), mAdapter.getData().get(i).getCoupon_id());
                    }

                }
            }
        });
        mAdapter.setNewData(data);

        return dialog;
    }

    public interface OnDialogCouponSelectListener {
        void onOkClick(String couponMoney, int couponId);
    }

    public static DialogPlus showShareDialog(Activity act, OnDialogShareListener listener) {
        final DialogPlus dialog = DialogPlus.newDialog(act)
                //要想setContentBackgroundResource生效，则item不能设置background属性
//                .setContentBackgroundResource(R.drawable.shape_coupon)
//                .setOverlayBackgroundResource(ContextUtil.getColor(R.color.transparent))//外部背景色
                .setContentHolder(new ViewHolder(R.layout.dialog_share))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        dialogPlus.dismiss();
                    }
                })
                .create();
        View view = dialog.getHolderView();
        LinearLayout llWechat = view.findViewById(R.id.llWechat);
        LinearLayout llFriend = view.findViewById(R.id.llFriend);
        LinearLayout llWeibo = view.findViewById(R.id.llWeibo);
        LinearLayout llQq = view.findViewById(R.id.llQq);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        llWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShare(1);
            }
        });llFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShare(2);
            }
        });llWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShare(3);
            }
        });llQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShare(4);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
    public interface OnDialogShareListener {
        void onShare(int  which);
    }


    public static EditDialog showEditDialog(Context context, String title, String hint
            , String leftStr, String rightStr, EditDialog.OnButtonClickListener listener) {

        EditDialog editDialog = new EditDialog(context);
        editDialog.setTitle(title);
        editDialog.setHint(hint);
        editDialog.setNegativeButton(leftStr, R.color.titleColor);
        editDialog.setPositiveButton(rightStr, R.color.titleColor);
        editDialog.setCanceledOnTouchOutside(false);
        editDialog.setOnButtonClickListener(listener);
        editDialog.show();
        return editDialog;
    }

    public static DialogPlus showQrCodeDialog(Activity act, Bitmap image, String qrCode) {
        final DialogPlus dialog = DialogPlus.newDialog(act)
                //要想setContentBackgroundResource生效，则item不能设置background属性
                .setContentBackgroundResource(R.drawable.shape_code_image)
//                .setOverlayBackgroundResource(ContextUtil.getColor(R.color.transparent))//外部背景色
                .setContentHolder(new ViewHolder(R.layout.dialog_qr_code))
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        dialogPlus.dismiss();
                    }
                })
                .create();
        View view = dialog.getHolderView();
        ImageView ivImage = view.findViewById(R.id.ivImage);
        TextView tvQrCode = view.findViewById(R.id.tvQrCode);
        ivImage.setImageBitmap(image);
//        ImageUtil.loadImage(ivImage, image);

        tvQrCode.setText(qrCode);
        dialog.show();
        return dialog;
    }


}

package com.limelight.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limelight.R;
import androidx.core.content.ContextCompat;

/** Minimal, controller-friendly desktop controls that stay out of the stream when collapsed. */
public final class QuickControlsOverlay {
    public interface Callbacks {
        void toggleFullKeyboard();
        void sendAltTab();
        void toggleSystemKeyboard();
        void sendTaskManager();
        void showMore();
        void restoreStreamFocus();
    }

    private final Activity activity;
    private final Callbacks callbacks;
    private final FrameLayout layer;
    private final View scrim;
    private final LinearLayout panel;
    private final ImageButton handle;
    private boolean expanded;
    private boolean expandedBeforePip;

    public QuickControlsOverlay(Activity activity, ViewGroup root, Callbacks callbacks) {
        this.activity = activity;
        this.callbacks = callbacks;

        layer = new FrameLayout(activity);
        layer.setClipChildren(false);
        layer.setClipToPadding(false);
        layer.setClickable(false);
        layer.setFocusable(false);
        root.addView(layer, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        scrim = new View(activity);
        scrim.setBackgroundColor(color(R.color.drift_scrim));
        scrim.setClickable(true);
        scrim.setFocusable(false);
        scrim.setOnClickListener(v -> collapse());
        layer.addView(scrim, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        panel = new LinearLayout(activity);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(18), dp(18), dp(18), dp(18));
        panel.setBackgroundResource(R.drawable.drift_quick_panel);
        panel.setElevation(dp(18));
        panel.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                collapse();
                return true;
            }
            return false;
        });
        FrameLayout.LayoutParams panelParams = new FrameLayout.LayoutParams(
                dp(332), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END);
        panelParams.setMargins(0, dp(12), dp(12), dp(12));
        layer.addView(panel, panelParams);

        TextView title = new TextView(activity);
        title.setText(R.string.quick_controls_title);
        title.setTextColor(color(R.color.drift_text));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(dp(14), 0, dp(14), dp(12));
        panel.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(56)));

        addAction(R.string.quick_full_keys, R.drawable.ic_quick_full_keys,
                callbacks::toggleFullKeyboard);
        addAction(R.string.quick_alt_tab, R.drawable.ic_quick_switch,
                callbacks::sendAltTab);
        addAction(R.string.quick_android_keyboard, R.drawable.ic_quick_keyboard,
                callbacks::toggleSystemKeyboard);
        addAction(R.string.quick_task_manager, R.drawable.ic_quick_tasks,
                callbacks::sendTaskManager);
        addAction(R.string.quick_more, R.drawable.ic_quick_more,
                callbacks::showMore);

        handle = new ImageButton(activity);
        handle.setBackgroundResource(R.drawable.drift_quick_handle);
        handle.setImageResource(R.drawable.ic_quick_controls);
        handle.setColorFilter(color(R.color.drift_text));
        handle.setContentDescription(activity.getString(R.string.quick_controls_open));
        handle.setPadding(dp(14), dp(18), dp(10), dp(18));
        handle.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        handle.setFocusable(true);
        handle.setClickable(true);
        handle.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            expand();
        });
        FrameLayout.LayoutParams handleParams = new FrameLayout.LayoutParams(
                dp(56), dp(88), Gravity.END | Gravity.CENTER_VERTICAL);
        layer.addView(handle, handleParams);

        collapse(false);
    }

    private void addAction(int labelRes, int iconRes, Runnable action) {
        TextView view = new TextView(activity);
        view.setText(labelRes);
        view.setTextColor(color(R.color.drift_text));
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setBackgroundResource(R.drawable.drift_quick_action);
        view.setCompoundDrawablePadding(dp(14));
        view.setPadding(dp(16), 0, dp(14), 0);
        view.setMinHeight(dp(64));
        view.setFocusable(true);
        view.setClickable(true);
        Drawable icon = activity.getDrawable(iconRes);
        if (icon != null) {
            icon.setTint(color(R.color.drift_text));
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
        }
        view.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            collapse();
            layer.postDelayed(action, 80);
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(64));
        params.topMargin = dp(8);
        panel.addView(view, params);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expand() {
        if (layer.getVisibility() != View.VISIBLE) {
            return;
        }
        expanded = true;
        scrim.setVisibility(View.VISIBLE);
        panel.setVisibility(View.VISIBLE);
        handle.setVisibility(View.GONE);
        if (panel.getChildCount() > 1) {
            panel.getChildAt(1).requestFocus();
        }
    }

    public void collapse() {
        collapse(true);
    }

    private void collapse(boolean restoreFocus) {
        expanded = false;
        scrim.setVisibility(View.GONE);
        panel.setVisibility(View.GONE);
        handle.setVisibility(View.VISIBLE);
        if (restoreFocus) {
            callbacks.restoreStreamFocus();
        }
    }

    public void hideForPip() {
        expandedBeforePip = expanded;
        layer.setVisibility(View.GONE);
    }

    public void restoreAfterPip() {
        layer.setVisibility(View.VISIBLE);
        if (expandedBeforePip) {
            expand();
        }
        else {
            collapse(false);
        }
    }

    public void hide() {
        layer.setVisibility(View.GONE);
    }

    private int color(int resource) {
        return ContextCompat.getColor(activity, resource);
    }

    private int dp(int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }
}

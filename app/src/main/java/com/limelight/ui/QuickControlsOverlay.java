package com.limelight.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewConfiguration;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.limelight.R;
import androidx.core.content.ContextCompat;

/** Minimal, controller-friendly desktop controls that stay out of the stream when collapsed. */
public final class QuickControlsOverlay {
    private static final long HANDLE_IDLE_TIMEOUT_MS = 3_000;
    private static final long HANDLE_MOTION_DURATION_MS = 180;
    private static final long PANEL_ENTER_DURATION_MS = 340;
    private static final long PANEL_EXIT_DURATION_MS = 280;
    private static final PathInterpolator MOTION_EASING =
            new PathInterpolator(0.2f, 0f, 0f, 1f);
    public interface Callbacks {
        void toggleFullKeyboard();
        void sendAltTab();
        void toggleSystemKeyboard();
        void sendTaskManager();
        void showMore();
        void showSessionActions();
        void setOverlayInputActive(boolean active);
        void restoreStreamFocus();
    }

    private final Activity activity;
    private final Callbacks callbacks;
    private final FrameLayout layer;
    private final View scrim;
    private final LinearLayout panel;
    private final LinearLayout actionList;
    private final ImageButton handle;
    private final boolean onLeft;
    private final int touchSlop;
    private final Runnable minimizeHandleRunnable = this::minimizeHandle;
    private boolean handleMinimized;
    private boolean expanded;
    private boolean expandedBeforePip;
    private float handleTouchDownX;
    private float handleTouchDownY;
    private float handleTouchDownCenterY;
    private float handleCenterFraction = 0.5f;
    private boolean handleDragging;

    public QuickControlsOverlay(Activity activity, ViewGroup root, boolean onLeft, Callbacks callbacks) {
        this.activity = activity;
        this.onLeft = onLeft;
        this.touchSlop = ViewConfiguration.get(activity).getScaledTouchSlop();
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
        panel.setBackgroundResource(onLeft ?
                R.drawable.drift_quick_panel_left : R.drawable.drift_quick_panel);
        panel.setElevation(dp(18));
        panel.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                collapse();
                return true;
            }
            return false;
        });
        FrameLayout.LayoutParams panelParams = new FrameLayout.LayoutParams(
                dp(332), ViewGroup.LayoutParams.MATCH_PARENT, edgeGravity());
        layer.addView(panel, panelParams);

        LinearLayout header = new LinearLayout(activity);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView title = new TextView(activity);
        title.setText(R.string.quick_controls_title);
        title.setTextColor(color(R.color.drift_text));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(dp(14), 0, dp(8), 0);
        header.addView(title, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        ImageButton sessionButton = new ImageButton(activity);
        sessionButton.setImageResource(R.drawable.ic_quick_session);
        sessionButton.setColorFilter(color(R.color.drift_text));
        sessionButton.setContentDescription(activity.getString(R.string.quick_session_actions));
        sessionButton.setBackgroundResource(R.drawable.drift_quick_action);
        sessionButton.setPadding(dp(16), dp(16), dp(16), dp(16));
        sessionButton.setFocusable(true);
        sessionButton.setFocusableInTouchMode(true);
        sessionButton.setClickable(true);
        sessionButton.setOnClickListener(v -> {
            if (!expanded) {
                return;
            }
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            collapse();
            layer.postDelayed(callbacks::showSessionActions, PANEL_EXIT_DURATION_MS + 20);
        });
        header.addView(sessionButton, new LinearLayout.LayoutParams(dp(56), dp(56)));
        panel.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(64)));

        ScrollView actionScroll = new ScrollView(activity);
        actionScroll.setFillViewport(true);
        actionScroll.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        actionScroll.setVerticalScrollBarEnabled(false);
        actionList = new LinearLayout(activity);
        actionList.setOrientation(LinearLayout.VERTICAL);
        actionScroll.addView(actionList, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        panel.addView(actionScroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        addAction(R.string.quick_full_keys, R.drawable.ic_quick_full_keys,
                callbacks::toggleFullKeyboard);
        addAction(R.string.quick_android_keyboard, R.drawable.ic_quick_keyboard,
                callbacks::toggleSystemKeyboard);
        addAction(R.string.quick_alt_tab, R.drawable.ic_quick_switch,
                callbacks::sendAltTab);
        addAction(R.string.quick_task_manager, R.drawable.ic_quick_tasks,
                callbacks::sendTaskManager);
        addAction(R.string.quick_more, R.drawable.ic_quick_more,
                callbacks::showMore);

        handle = new ImageButton(activity);
        handle.setBackgroundResource(handleBackground());
        handle.setImageResource(R.drawable.ic_quick_controls);
        handle.setColorFilter(color(R.color.drift_text));
        handle.setContentDescription(activity.getString(R.string.quick_controls_open));
        handle.setPadding(dp(14), dp(18), dp(14), dp(18));
        handle.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        handle.setFocusable(true);
        handle.setClickable(true);
        handle.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                handle.removeCallbacks(minimizeHandleRunnable);
                if (handleMinimized) {
                    showFullHandle();
                }
            }
            else if (!expanded) {
                scheduleHandleMinimize();
            }
        });
        handle.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            if (handleMinimized) {
                revealHandle();
            }
            else {
                expand();
            }
        });
        handle.setOnTouchListener((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    handleTouchDownX = event.getRawX();
                    handleTouchDownY = event.getRawY();
                    handleTouchDownCenterY = handle.getY() + handle.getHeight() / 2f;
                    handleDragging = false;
                    handle.removeCallbacks(minimizeHandleRunnable);
                    return false;
                case MotionEvent.ACTION_MOVE:
                    if (!handleMinimized && !expanded &&
                            Math.abs(event.getRawY() - handleTouchDownY) > touchSlop) {
                        handleDragging = true;
                        v.setPressed(false);
                    }
                    if (handleDragging) {
                        moveHandleCenter(handleTouchDownCenterY +
                                event.getRawY() - handleTouchDownY);
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_UP:
                    if (handleDragging) {
                        scheduleHandleMinimize();
                        return true;
                    }
                    float inwardDistance = onLeft ?
                            event.getRawX() - handleTouchDownX :
                            handleTouchDownX - event.getRawX();
                    if (handleMinimized && inwardDistance >= dp(24)) {
                        v.setPressed(false);
                        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        revealHandle();
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_CANCEL:
                    if (!expanded) {
                        scheduleHandleMinimize();
                    }
                    return handleDragging;
                default:
                    return false;
            }
        });
        layer.addView(handle, new FrameLayout.LayoutParams(
                dp(56), dp(88), edgeGravity() | Gravity.CENTER_VERTICAL));
        layer.post(() -> applyHandleLayout(56, 88));

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
        view.setFocusableInTouchMode(true);
        view.setClickable(true);
        Drawable icon = activity.getDrawable(iconRes);
        if (icon != null) {
            icon.setTint(color(R.color.drift_text));
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
        }
        view.setOnClickListener(v -> {
            if (!expanded) {
                return;
            }
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            collapse();
            layer.postDelayed(action, PANEL_EXIT_DURATION_MS + 20);
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(64));
        params.topMargin = dp(8);
        actionList.addView(view, params);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void bringHandleToFront() {
        layer.bringToFront();
        handle.bringToFront();
    }

    public void expand() {
        if (layer.getVisibility() != View.VISIBLE) {
            return;
        }
        callbacks.setOverlayInputActive(true);
        handle.removeCallbacks(minimizeHandleRunnable);
        panel.animate().cancel();
        scrim.animate().cancel();
        layer.bringToFront();
        expanded = true;
        handle.setVisibility(View.GONE);
        scrim.setAlpha(0f);
        scrim.setVisibility(View.VISIBLE);
        scrim.animate()
                .alpha(1f)
                .setDuration(PANEL_ENTER_DURATION_MS)
                .setInterpolator(MOTION_EASING)
                .start();
        panel.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        panel.setTranslationX(panelHiddenTranslation());
        panel.setVisibility(View.VISIBLE);
        panel.animate()
                .translationX(0f)
                .setDuration(PANEL_ENTER_DURATION_MS)
                .setInterpolator(MOTION_EASING)
                .withEndAction(() -> panel.setLayerType(View.LAYER_TYPE_NONE, null))
                .start();
        if (actionList.getChildCount() > 0) {
            actionList.getChildAt(0).requestFocus();
        }
    }

    public void collapse() {
        collapse(true);
    }

    private void collapse(boolean restoreFocus) {
        boolean animate = expanded && panel.getVisibility() == View.VISIBLE;
        expanded = false;
        layer.setVisibility(View.VISIBLE);
        bringHandleToFront();
        panel.animate().cancel();
        scrim.animate().cancel();
        if (animate) {
            scrim.animate()
                    .alpha(0f)
                    .setDuration(PANEL_EXIT_DURATION_MS)
                    .setInterpolator(MOTION_EASING)
                    .withEndAction(() -> {
                        if (!expanded) {
                            scrim.setVisibility(View.GONE);
                        }
                    })
                    .start();
            panel.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            panel.animate()
                    .translationX(panelHiddenTranslation())
                    .setDuration(PANEL_EXIT_DURATION_MS)
                    .setInterpolator(MOTION_EASING)
                    .withEndAction(() -> {
                        panel.setLayerType(View.LAYER_TYPE_NONE, null);
                        if (!expanded) {
                            panel.setVisibility(View.GONE);
                            panel.setTranslationX(0f);
                            showFullHandle();
                            scheduleHandleMinimize();
                            if (restoreFocus) {
                                callbacks.restoreStreamFocus();
                            }
                        }
                    })
                    .start();
        }
        else {
            panel.setLayerType(View.LAYER_TYPE_NONE, null);
            scrim.setVisibility(View.GONE);
            scrim.setAlpha(1f);
            panel.setVisibility(View.GONE);
            panel.setTranslationX(0f);
            showFullHandle();
            scheduleHandleMinimize();
            if (restoreFocus) {
                callbacks.restoreStreamFocus();
            }
        }
    }

    public void hideForPip() {
        expandedBeforePip = expanded;
        handle.removeCallbacks(minimizeHandleRunnable);
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
        handle.removeCallbacks(minimizeHandleRunnable);
        layer.setVisibility(View.GONE);
    }

    private void scheduleHandleMinimize() {
        handle.removeCallbacks(minimizeHandleRunnable);
        handle.postDelayed(minimizeHandleRunnable, HANDLE_IDLE_TIMEOUT_MS);
    }

    private void minimizeHandle() {
        if (expanded || layer.getVisibility() != View.VISIBLE) {
            return;
        }
        handle.animate().cancel();
        handleMinimized = true;
        callbacks.setOverlayInputActive(false);
        handle.setContentDescription(activity.getString(R.string.quick_controls_reveal));
        handle.setImageDrawable(null);
        handle.setBackgroundResource(minimizedHandleBackground());
        handle.setPadding(0, 0, 0, 0);
        applyHandleLayout(48, 72);
        handle.setAlpha(0f);
        handle.setTranslationX(0f);
        handle.setScaleX(1f);
        handle.animate()
                .alpha(0.62f)
                .setDuration(HANDLE_MOTION_DURATION_MS)
                .setInterpolator(MOTION_EASING)
                .start();
    }

    private void revealHandle() {
        callbacks.setOverlayInputActive(true);
        showFullHandle();
        scheduleHandleMinimize();
    }

    private void showFullHandle() {
        handle.animate().cancel();
        handleMinimized = false;
        handle.setContentDescription(activity.getString(R.string.quick_controls_open));
        handle.setVisibility(View.VISIBLE);
        handle.setImageResource(R.drawable.ic_quick_controls);
        handle.setColorFilter(color(R.color.drift_text));
        handle.setBackgroundResource(handleBackground());
        handle.setPadding(dp(14), dp(18), dp(14), dp(18));
        applyHandleLayout(56, 88);
        handle.setAlpha(0f);
        handle.setTranslationX(0f);
        handle.setScaleX(1f);
        handle.animate()
                .alpha(1f)
                .setDuration(HANDLE_MOTION_DURATION_MS)
                .setInterpolator(MOTION_EASING)
                .start();
    }

    private int edgeGravity() {
        return onLeft ? Gravity.LEFT : Gravity.RIGHT;
    }

    private int panelHiddenTranslation() {
        return onLeft ? -dp(332) : dp(332);
    }

    private int handleBackground() {
        return onLeft ? R.drawable.drift_quick_handle_left : R.drawable.drift_quick_handle;
    }

    private int minimizedHandleBackground() {
        return onLeft ? R.drawable.drift_quick_handle_minimized_left :
                R.drawable.drift_quick_handle_minimized;
    }

    private void applyHandleLayout(int widthDp, int heightDp) {
        int width = dp(widthDp);
        int height = dp(heightDp);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                width, height, edgeGravity() | Gravity.TOP);
        int layerHeight = layer.getHeight();
        if (layerHeight == 0) {
            params.gravity = edgeGravity() | Gravity.CENTER_VERTICAL;
        }
        else {
            int center = Math.round(handleCenterFraction * layerHeight);
            params.topMargin = Math.max(0, Math.min(layerHeight - height, center - height / 2));
        }
        handle.setLayoutParams(params);
    }

    private void moveHandleCenter(float center) {
        int layerHeight = layer.getHeight();
        if (layerHeight == 0) {
            return;
        }
        int halfHeight = handle.getHeight() / 2;
        float clampedCenter = Math.max(halfHeight,
                Math.min(layerHeight - halfHeight, center));
        handleCenterFraction = clampedCenter / layerHeight;
        applyHandleLayout(handleMinimized ? 48 : 56, handleMinimized ? 72 : 88);
    }

    private int color(int resource) {
        return ContextCompat.getColor(activity, resource);
    }

    private int dp(int value) {
        return Math.round(value * activity.getResources().getDisplayMetrics().density);
    }
}

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.tv.settings.dialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.android.tv.settings.dialog.SettingsLayoutFragment;
import com.android.tv.settings.dialog.Layout;
import com.android.tv.settings.dialog.Layout.Action;
import com.android.tv.settings.dialog.Layout.LayoutRow;

import com.android.tv.settings.R;

import java.util.ArrayList;

/**
 * Activity to present settings menus and options.
 */
public abstract class SettingsLayoutActivity extends Activity implements
        SettingsLayoutFragment.Listener, SettingsLayoutAdapter.OnFocusListener {

    private SettingsLayoutFragment mSettingsLayoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Layout layout = createLayout();
        layout.navigateToRoot();
        final FragmentManager fm = getFragmentManager();
        mSettingsLayoutFragment = (SettingsLayoutFragment) fm.findFragmentByTag(
                SettingsLayoutFragment.TAG_LEAN_BACK_DIALOG_FRAGMENT);
        if (mSettingsLayoutFragment == null) {
            mSettingsLayoutFragment = new SettingsLayoutFragment.Builder()
                    .title(layout.getTitle())
                    .breadcrumb(layout.getBreadcrumb())
                    .icon(layout.getIcon())
                    .iconBackgroundColor(getResources().getColor(R.color.icon_background))
                    .build();
            SettingsLayoutFragment.add(fm, mSettingsLayoutFragment);
        }
        mSettingsLayoutFragment.setLayout(layout);
    }

    @Override
    public void onBackPressed() {
        if (! mSettingsLayoutFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public abstract Layout createLayout();

    @Override
    public void onActionFocused(Layout.LayoutRow item) {
    }

    @Override
    public void onActionClicked(Action action) {
    }

    protected void goBackToTitle (String title) {
        mSettingsLayoutFragment.goBackToTitle (title);
    }

    /**
     * Return true if the display view is rendered right to left.
     */
    protected boolean isLayoutRtl() {
        return mSettingsLayoutFragment.getView().isLayoutRtl();
    }

    protected void setIcon(int resId) {
        mSettingsLayoutFragment.setIcon(resId);
    }
}
